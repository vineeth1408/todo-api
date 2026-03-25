package com.learning.todoapi.controller;

import com.learning.todoapi.dto.ApiResponse;
import com.learning.todoapi.dto.TodoRequestDto;
import com.learning.todoapi.dto.TodoResponseDto;
import com.learning.todoapi.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTodos() {
        List<TodoResponseDto> todos = todoService.findAll();
        return ResponseEntity.ok(new ApiResponse(true, "Todos retrieved successfully", todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTodoById(@PathVariable Long id) {
        TodoResponseDto todo = todoService.findById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Todo retrieved successfully", todo));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTodo(@Valid @RequestBody TodoRequestDto request) {
        TodoResponseDto created = todoService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Todo created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto request) {
        TodoResponseDto updated = todoService.update(id, request);
        return ResponseEntity.ok(new ApiResponse(true, "Todo updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Todo deleted successfully", null));
    }
}