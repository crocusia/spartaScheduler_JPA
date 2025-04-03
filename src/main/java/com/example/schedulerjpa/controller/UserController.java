package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.*;
import com.example.schedulerjpa.dto.response.UserResponseDto;
import com.example.schedulerjpa.dto.response.UserSessionDto;
import com.example.schedulerjpa.dto.response.UserSignUpResponseDto;
import com.example.schedulerjpa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<UserSignUpResponseDto> signUp(@Valid @RequestBody UserSignUpRequestDto signUpDto){
        UserSignUpResponseDto userSignUpResponseDto = userService.signUp(signUpDto);
        return new ResponseEntity<>(userSignUpResponseDto, HttpStatus.CREATED);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDto loginRequest,
            HttpServletRequest request
    ){
        HttpSession existingSession  = request.getSession(false);

        //세션이 존재하고 loginUser가 null이 아니라면
        if(existingSession!=null && existingSession.getAttribute("loginUser") != null){
            return ResponseEntity.badRequest().body("이미 로그인된 상태입니다.");
        }

        UserSessionDto loginUser = userService.login(loginRequest);
        //로그인 성공 시, 새로운 세션 생성
        HttpSession session = request.getSession(true);
        //세션에 사용자 정보 저장
        session.setAttribute("loginUser", loginUser);
        //세션 유효시간 30분
        session.setMaxInactiveInterval(30 * 60);
        return ResponseEntity.ok("로그인 성공");
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> lohout( HttpServletRequest request ){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return ResponseEntity.ok("로그아웃 성공");
    }

    //마이페이지
    @PostMapping("/me")
    public ResponseEntity<UserResponseDto> myPage(@ModelAttribute("loginUser") UserSessionDto loginUser){
        UserResponseDto userResponseDto = userService.findUserById(loginUser.getUserId());
        return ResponseEntity.ok((userResponseDto));
    }

    //유저 프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findUserById(id);
        return ResponseEntity.ok(responseDto);
    }

    //유저 이름 변경
    @PatchMapping("/me/name")
    public ResponseEntity<UserResponseDto> updateUserName(
            @Valid @RequestBody UserNameUpdateRequestDto updateDto,
            @ModelAttribute("loginUser") UserSessionDto loginUser
    ) {
        UserResponseDto userResponseDto = userService.updateUserName(loginUser.getUserId(), updateDto);
        return ResponseEntity.ok(userResponseDto);
    }

    //유저 비밀번호 변경
    @PatchMapping("/me/password")
    public ResponseEntity<String> updateUserPassword(
            @Valid @RequestBody UserPasswordUpdateRequestDto updateDto,
            @ModelAttribute("loginUser") UserSessionDto loginUser
    ) {
        userService.updateUserPassword(loginUser.getUserId(), updateDto);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    //유저 삭제
    @PatchMapping("/me")
    public ResponseEntity<String> deleteUser(
            @Valid @RequestBody UserDeleteRequestDto deleteDto,
            @ModelAttribute("loginUser") UserSessionDto loginUser
    ) {
        userService.deleteUser(loginUser.getUserId(), deleteDto);
        return ResponseEntity.ok("유저 삭제 성공");
    }

}
