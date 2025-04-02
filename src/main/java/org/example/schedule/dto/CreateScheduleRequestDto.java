package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleRequestDto {
    private String title;
    private String content;
    private Long userId;
    private String password;
}
