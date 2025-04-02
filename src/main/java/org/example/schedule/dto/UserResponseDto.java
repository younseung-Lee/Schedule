package org.example.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.schedule.entity.UserEntity;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserResponseDto toDto(UserEntity userEntity){
        return new UserResponseDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getCreatedAt(),
                userEntity.getModifiedAt());
    }

}
