package com.learning.todoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.todoapi.dto.TodoRequestDto;
import com.learning.todoapi.dto.TodoResponseDto;
import com.learning.todoapi.exception.ResourceNotFoundException;
import com.learning.todoapi.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTodos_Success() throws Exception {
        TodoResponseDto todo1 = new TodoResponseDto(1L, "Test Todo 1", "Description 1", false, LocalDateTime.now(), LocalDateTime.now());
        TodoResponseDto todo2 = new TodoResponseDto(2L, "Test Todo 2", "Description 2", true, LocalDateTime.now(), LocalDateTime.now());
        List<TodoResponseDto> todos = Arrays.asList(todo1, todo2);

        when(todoService.findAll()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Todos retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    public void testGetTodoById_Success() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "Test Todo", "Description", false, LocalDateTime.now(), LocalDateTime.now());

        when(todoService.findById(1L)).thenReturn(todo);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Todo retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    public void testGetTodoById_NotFound() throws Exception {
        when(todoService.findById(1L)).thenThrow(new ResourceNotFoundException("Todo not found"));

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    public void testCreateTodo_Success() throws Exception {
        TodoRequestDto request = new TodoRequestDto("New Todo", "New Description", null);
        TodoResponseDto created = new TodoResponseDto(1L, "New Todo", "New Description", false, LocalDateTime.now(), LocalDateTime.now());

        when(todoService.save(any(TodoRequestDto.class))).thenReturn(created);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Todo created successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    public void testCreateTodo_ValidationError() throws Exception {
        TodoRequestDto request = new TodoRequestDto("", "", null); // Invalid: title blank

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    public void testUpdateTodo_Success() throws Exception {
        TodoRequestDto request = new TodoRequestDto("Updated Todo", "Updated Description", true);
        TodoResponseDto updated = new TodoResponseDto(1L, "Updated Todo", "Updated Description", true, LocalDateTime.now(), LocalDateTime.now());

        when(todoService.update(eq(1L), any(TodoRequestDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Todo updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    public void testUpdateTodo_NotFound() throws Exception {
        TodoRequestDto request = new TodoRequestDto("Updated Todo", "Updated Description", true);

        when(todoService.update(eq(1L), any(TodoRequestDto.class))).thenThrow(new ResourceNotFoundException("Todo not found"));

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    public void testUpdateTodo_ValidationError() throws Exception {
        TodoRequestDto request = new TodoRequestDto("", "", null); // Invalid

        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    public void testDeleteTodo_Success() throws Exception {
        doNothing().when(todoService).delete(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTodo_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Todo not found")).when(todoService).delete(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}