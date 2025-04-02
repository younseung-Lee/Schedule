package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Getter
public class CreateUserRequestDto {
    private final String username;
    private final String email;
    private final String password;
}
