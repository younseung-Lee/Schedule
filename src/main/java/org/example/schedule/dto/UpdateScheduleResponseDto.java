package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
