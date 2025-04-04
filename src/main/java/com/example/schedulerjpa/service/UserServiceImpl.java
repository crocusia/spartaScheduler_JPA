package com.example.schedulerjpa.service;

import com.example.schedulerjpa.config.PasswordEncoder;
import com.example.schedulerjpa.dto.request.*;
import com.example.schedulerjpa.dto.response.UserResponseDto;
import com.example.schedulerjpa.dto.response.UserSessionDto;
import com.example.schedulerjpa.dto.response.UserSignUpResponseDto;
import com.example.schedulerjpa.entity.User;
import com.example.schedulerjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //회원가입
    @Override
    public UserSignUpResponseDto signUp(UserSignUpRequestDto signUpDto){
        //이미 존재하는 이메일인지 확인
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다.");
        }
        //새로운 이메일이라면 저장
        //비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        log.info("암호화된 비밀번호: {}", encodedPassword);
        User user = new User(signUpDto.getName(), signUpDto.getEmail(), encodedPassword);
        userRepository.save(user);

        return new UserSignUpResponseDto(user.getName(), user.getEmail());
    }
    //로그인
    @Override
    public UserSessionDto login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByEmailOrElseThrow(loginRequestDto.getEmail());

        if(!user.checkPassword(loginRequestDto.getPassword(), passwordEncoder)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다.");
        }
        //세션을 위한 DTO 생성 및 반환
        return new UserSessionDto(user.getId(), user.getName());
    }

    //조회 - 유저 아이디에 따른 유저 프로필 조회
    @Override
    public UserResponseDto findUserById(Long userId){
        User user = userRepository.findByIdOrElseThrow(userId);
        return new UserResponseDto(user.getName(), user.getEmail(), user.getUpdatedAt());
    }

    //수정 - 유저 이름 수정
    @Override
    public UserResponseDto updateUserName(Long userId, UserNameUpdateRequestDto updateDto){
        User user = userRepository.findByIdOrElseThrow(userId);
        user.updateName(updateDto.getName());
        userRepository.save(user);
        return new UserResponseDto(user.getName(), user.getEmail(), user.getUpdatedAt());
    }

    @Override
    public void updateUserPassword(Long userId, UserPasswordUpdateRequestDto updateDto){
        User user = userRepository.findByIdOrElseThrow(userId);

        //입력된 비밀번호가 현재 비밀번호와 일치하는지 검사
        if(!user.checkPassword(updateDto.getCurrentPassword(), passwordEncoder)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.updatePassword(updateDto.getNewPassword());
    }

    //삭제 - 유저 삭제
    @Override
    public void deleteUser(Long userId, UserDeleteRequestDto deleteDto){
        User user = userRepository.findByIdOrElseThrow(userId);
        //입력된 비밀번호가 현재 비밀번호와 일치하는지 검사
        if(!user.checkPassword(deleteDto.getPassword(), passwordEncoder)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

}
