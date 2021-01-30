package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.unit.UnitResponse;
import com.enigma.api.inventory.models.unit.UnitSearch;
import com.enigma.api.inventory.services.UnitService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnitController.class)
class UnitControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UnitService service;

    @MockBean
    ModelMapper modelMapper;

    @Test
    void addShouldReturnEntity() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);

        when(service.save(any())).thenReturn(entity);

        UnitResponse model = new UnitResponse();
        model.setId(entity.getId());

        when(modelMapper.map(any(Unit.class), any(Class.class))).thenReturn(model);

        RequestBuilder request = post("/units")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"abc\", \"description\": \"def\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.code", is(entity.getCode())));
    }

    @Test
    void editShouldReturnEntity() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);

        when(service.findById(any(Integer.class))).thenReturn(entity);
        when(service.save(any())).thenReturn(entity);

        UnitResponse model = new UnitResponse();
        model.setId(entity.getId());

        when(modelMapper.map(any(Unit.class), any(Class.class))).thenReturn(model);

        RequestBuilder request = put("/units/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"abc\", \"description\": \"def\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.code", is(entity.getCode())));
    }

    @Test
    void editNonExistIdShouldReturnError() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = put("/units/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"abc\", \"description\": \"def\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void removeByIdShouldReturnEntity() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);

        when(service.removeById(any(Integer.class))).thenReturn(entity);

        UnitResponse model = new UnitResponse();
        model.setId(entity.getId());

        when(modelMapper.map(any(Unit.class), any(Class.class))).thenReturn(model);

        RequestBuilder request = delete("/units/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.code", is(entity.getCode())));
    }

    @Test
    void removeNonExistIdShouldReturnError() throws Exception {
        when(service.removeById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = delete("/units/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void findByIdShouldReturnEntity() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);

        when(service.findById(any(Integer.class))).thenReturn(entity);

        UnitResponse model = new UnitResponse();
        model.setId(entity.getId());

        when(modelMapper.map(any(Unit.class), any(Class.class))).thenReturn(model);

        RequestBuilder request = get("/units/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.code", is(entity.getCode())));
    }

    @Test
    void findNonExistIdShouldReturnError() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = get("/units/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void findAllShouldReturnList() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);

        List<Unit> listUnit = new ArrayList<>();
        listUnit.add(entity);

        Page<Unit> page = new PageImpl<>(listUnit);

        when(modelMapper.map(any(UnitSearch.class), any(Class.class))).thenReturn(entity);
        when(service.findAll(any(), anyInt(), anyInt(), any())).thenReturn(page);

        RequestBuilder request = get("/units");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())));
    }

    @Test
    void findAllShouldReturnEmptyList() throws Exception {
        Page page = Page.empty();
        when(service.findAll(any(), anyInt(), anyInt(), any())).thenReturn(page);

        RequestBuilder request = get("/units");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.list", empty()));
    }

    // Handle Bind Exception
    @Test
    void nonAlphabetCodeRequestParameterShouldReturnError() throws Exception {
        Page page = Page.empty();
        when(service.findAll(any(), anyInt(), anyInt(), any())).thenReturn(page);

        RequestBuilder request = get("/units?code=123");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));
    }

    // Handle Method Argument Not Valid
    @Test
    void blankCodeRequestParameterShouldReturnError() throws Exception {
        Unit entity = new Unit();
        entity.setId(1);

        when(service.save(any())).thenReturn(entity);

        UnitResponse model = new UnitResponse();
        model.setId(entity.getId());

        when(modelMapper.map(any(Unit.class), any(Class.class))).thenReturn(model);

        RequestBuilder request = post("/units")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"\", \"description\": \"\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));
    }
}
