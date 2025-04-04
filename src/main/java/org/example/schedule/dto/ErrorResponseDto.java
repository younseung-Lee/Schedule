package org.example.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 JSON에서 제외
public class ErrorResponseDto {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private String path;
    private List<FieldErrorDto> fieldErrors;

    @Getter
    @AllArgsConstructor
    public static class FieldErrorDto {
        private String field;
        private String message;
    }
}