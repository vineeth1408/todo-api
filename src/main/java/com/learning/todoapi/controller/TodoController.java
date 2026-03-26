package com.learning.todoapi.controller;

import com.learning.todoapi.dto.ApiResponse;
import com.learning.todoapi.dto.TodoRequestDto;
import com.learning.todoapi.dto.TodoResponseDto;
import com.learning.todoapi.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Tag(name = "Todo", description = "CRUD operations for todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Operation(summary = "Get all todos", description = "Retrieve all todo items")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todos retrieved successfully")
    })
    public ResponseEntity<ApiResponse> getAllTodos() {
        List<TodoResponseDto> todos = todoService.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Todos retrieved successfully", todos));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo by ID", description = "Retrieve a specific todo item by its ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Todo not found",
                content = @Content(schema = @Schema(implementation = com.learning.todoapi.dto.ErrorResponse.class))
            )
    })
    public ResponseEntity<ApiResponse> getTodoById(@PathVariable Long id) {
        TodoResponseDto todo = todoService.findById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Todo retrieved successfully", todo));
    }

    @PostMapping
    @Operation(summary = "Create todo", description = "Create a new todo item")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Todo created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Invalid request payload",
                content = @Content(schema = @Schema(implementation = com.learning.todoapi.dto.ErrorResponse.class))
            )
    })
    public ResponseEntity<ApiResponse> createTodo(@Valid @RequestBody TodoRequestDto request) {
        TodoResponseDto created = todoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Todo created successfully", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update todo", description = "Update an existing todo item by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Invalid request payload",
                content = @Content(schema = @Schema(implementation = com.learning.todoapi.dto.ErrorResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Todo not found",
                content = @Content(schema = @Schema(implementation = com.learning.todoapi.dto.ErrorResponse.class))
            )
    })
    public ResponseEntity<ApiResponse> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto request) {
        TodoResponseDto updated = todoService.update(id, request);
        return ResponseEntity.ok(new ApiResponse(true, "Todo updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete todo", description = "Delete a todo item by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "Todo not found",
                content = @Content(schema = @Schema(implementation = com.learning.todoapi.dto.ErrorResponse.class))
            )
    })
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Todo deleted successfully", null));
    }
}