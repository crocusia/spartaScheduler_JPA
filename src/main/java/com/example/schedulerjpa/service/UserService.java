package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.request.*;
import com.example.schedulerjpa.dto.response.UserResponseDto;
import com.example.schedulerjpa.dto.response.UserSessionDto;
import com.example.schedulerjpa.dto.response.UserSignUpResponseDto;

public interface UserService {
    //회원가입
    UserSignUpResponseDto signUp(UserSignUpRequestDto signUpDto);

    //로그인
    UserSessionDto login(LoginRequestDto loginRequestDto);

    //조회 - 유저 아이디에 따른 유저 프로필 조회
    UserResponseDto findUserById(Long userId);

    //수정 - 유저 이름 수정
    UserResponseDto updateUserName(Long userId, UserNameUpdateRequestDto updateDto);

    //수정 - 유저 비밀번호 수정
    void updateUserPassword(Long userId, UserPasswordUpdateRequestDto updateDto);

    //삭제 - 유저 삭제
    void deleteUser(Long userId, UserDeleteRequestDto deleteDto);
}
