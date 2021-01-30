package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.exceptions.PathNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DefaultErrorController.class)
class ErrorControllerTest {

    @Autowired
    MockMvc mvc;

    // handle path not found exception
    @Test
    void nonExistPathShouldReturnError() throws Exception {
        RequestBuilder request = get("/error");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PathNotFoundException))
                .andExpect(result -> assertEquals("error.404.path", result.getResolvedException().getMessage()));
    }

    // handle unknown exception
    @Test
    void unknownErrorShouldReturnError() throws Exception {
        RequestBuilder request = get("/errortest");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));
    }

    // handle exception internal
    @Test
    void unsupportedMethodShouldReturnMethodNotAllowed() throws Exception {
        RequestBuilder request = post("/error");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.METHOD_NOT_ALLOWED.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException));
    }
}
