package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schedule.entity.ScheduleEntity;
import org.example.schedule.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static ScheduleResponseDto toDto(ScheduleEntity scheduleEntity){
        return new ScheduleResponseDto(
                scheduleEntity.getId(),
                scheduleEntity.getTitle(),
                scheduleEntity.getContent(),
                scheduleEntity.getUser().getId(),
                scheduleEntity.getCreatedAt(),
                scheduleEntity.getModifiedAt());
    }

}
