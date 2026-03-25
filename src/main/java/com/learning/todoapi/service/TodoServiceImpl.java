package com.learning.todoapi.service;

import com.learning.todoapi.dto.TodoRequestDto;
import com.learning.todoapi.dto.TodoResponseDto;
import com.learning.todoapi.entity.Todo;
import com.learning.todoapi.exception.ResourceNotFoundException;
import com.learning.todoapi.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoResponseDto> findAll() {
        return todoRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponseDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        return mapToResponseDto(todo);
    }

    @Override
    public TodoResponseDto save(TodoRequestDto request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);
        Todo saved = todoRepository.save(todo);
        return mapToResponseDto(saved);
    }

    @Override
    public TodoResponseDto update(Long id, TodoRequestDto request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }
        Todo updated = todoRepository.save(todo);
        return mapToResponseDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    private TodoResponseDto mapToResponseDto(Todo todo) {
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCompleted(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}