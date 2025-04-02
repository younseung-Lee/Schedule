package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UpdateUserRequestDto {
    private final String username;
    private final String email;
    private final String password;  // 기존 비밀번호 추가

}
