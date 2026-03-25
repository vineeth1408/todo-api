package com.learning.todoapi.service;

import com.learning.todoapi.dto.TodoRequestDto;
import com.learning.todoapi.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    List<TodoResponseDto> findAll();

    TodoResponseDto findById(Long id);

    TodoResponseDto save(TodoRequestDto request);

    TodoResponseDto update(Long id, TodoRequestDto request);

    void delete(Long id);
}