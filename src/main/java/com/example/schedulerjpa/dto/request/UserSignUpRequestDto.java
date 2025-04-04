package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "알파벳과 숫자의 조합만 가능합니다.")
    private String password;
}
