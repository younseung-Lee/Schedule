package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateScheduleRequestDto {
    private final String title;
    private final String content;
    private final Long userId;
    private final String password;
}
