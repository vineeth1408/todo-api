package com.learning.todoapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "Timestamp of the error response", example = "2023-10-01T10:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "HTTP error reason", example = "Bad Request")
    private String error;

    @Schema(description = "Detailed error message", example = "Validation failed")
    private String message;

    @Schema(description = "Request path that caused the error", example = "/api/todos")
    private String path;
}