package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.item.ItemElement;
import com.enigma.api.inventory.models.stock.StockRequest;
import com.enigma.api.inventory.models.stock.StockResponse;
import com.enigma.api.inventory.services.ItemService;
import com.enigma.api.inventory.services.StockService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
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

@WebMvcTest(StockController.class)
class StockControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    StockService service;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    ItemService itemService;

    @Test
    void addShouldReturnEntity() throws Exception {
        Item item = new Item();
        item.setId(1);

        ItemElement itemElement = new ItemElement();
        itemElement.setId(item.getId());

        Stock entity = new Stock();
        entity.setId(1);

        StockResponse stockResponse = new StockResponse();
        stockResponse.setId(entity.getId());

        when(modelMapper.map(any(StockRequest.class), any(Class.class))).thenReturn(entity);
        when(itemService.findById(any(Integer.class))).thenReturn(item);
        when(service.save(any())).thenReturn(entity);
        when(modelMapper.map(any(Stock.class), any(Class.class))).thenReturn(stockResponse);

        RequestBuilder request = post("/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"itemId\": 1, \"quantity\": 100}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.quantity", is(entity.getQuantity())));
    }

    @Test
    void editShouldReturnEntity() throws Exception {
        Item item = new Item();
        item.setId(1);

        ItemElement itemElement = new ItemElement();
        itemElement.setId(item.getId());

        Stock entity = new Stock();
        entity.setId(1);

        StockResponse stockResponse = new StockResponse();
        stockResponse.setId(entity.getId());

        when(service.findById(any(Integer.class))).thenReturn(entity);
        when(itemService.findById(any(Integer.class))).thenReturn(item);
        when(modelMapper.map(any(Stock.class), any(Class.class))).thenReturn(stockResponse);

        RequestBuilder request = put("/stocks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"itemId\": 1, \"quantity\": 100}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.quantity", is(entity.getQuantity())));
    }

    @Test
    void editNonExistIdShouldReturnError() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = put("/stocks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"itemId\": 1, \"quantity\": 100}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void removeByIdShouldReturnEntity() throws Exception {
        Item item = new Item();
        item.setId(1);

        ItemElement itemElement = new ItemElement();
        itemElement.setId(item.getId());

        Stock entity = new Stock();
        entity.setId(1);

        StockResponse stockResponse = new StockResponse();
        stockResponse.setId(entity.getId());
        stockResponse.setQuantity(entity.getQuantity());
        stockResponse.setItem(itemElement);

        when(service.removeById(any(Integer.class))).thenReturn(entity);
        when(modelMapper.map(any(Stock.class), any(Class.class))).thenReturn(stockResponse);

        RequestBuilder request = delete("/stocks/1")
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.quantity", is(entity.getQuantity())));
    }

    @Test
    void removeNonExistIdShouldReturnError() throws Exception {
        when(service.removeById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = delete("/stocks/1")
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void findByIdShouldReturnEntity() throws Exception {
        Item item = new Item();
        item.setId(1);

        ItemElement itemElement = new ItemElement();
        itemElement.setId(item.getId());

        Stock entity = new Stock();
        entity.setId(1);

        StockResponse stockResponse = new StockResponse();
        stockResponse.setId(entity.getId());

        when(service.findById(any(Integer.class))).thenReturn(entity);
        when(modelMapper.map(any(Stock.class), any(Class.class))).thenReturn(stockResponse);

        RequestBuilder request = get("/stocks/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.quantity", is(entity.getQuantity())));
    }

    @Test
    void findNonExistIdShouldReturnError() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = get("/stocks/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void findAllShouldReturnEmptyList() throws Exception {
        Page page = Page.empty();
        when(service.findAll(any(), anyInt(), anyInt(), any())).thenReturn(page);

        RequestBuilder request = get("/stocks");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.list", empty()));
    }

    @Test
    void findAllSummariesShouldReturnEmptyList() throws Exception {
        StockSummary stockSummary = new StockSummary();
        stockSummary.setQuantity(1L);
        List<StockSummary> list = new ArrayList<>();
        list.add(stockSummary);

        when(service.findAllSummaries()).thenReturn(list);

        RequestBuilder request = get("/stocks/summaries");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())));
    }
}
