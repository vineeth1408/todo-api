package com.learning.todoapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {

    @Schema(description = "Unique identifier of the todo", example = "1")
    private Long id;

    @Schema(description = "Title of the todo item", example = "Buy groceries")
    private String title;

    @Schema(description = "Description of the todo item", example = "Milk, bread, eggs")
    private String description;

    @Schema(description = "Completion status of the todo item", example = "false")
    private Boolean completed;

    @Schema(description = "Timestamp when todo was created", example = "2023-10-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when todo was last updated", example = "2023-10-01T11:00:00")
    private LocalDateTime updatedAt;
}