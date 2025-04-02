package org.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteScheduleRequestDto {
    private Long userId;
    private String password;

    public DeleteScheduleRequestDto(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
