package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.item.ItemRequest;
import com.enigma.api.inventory.models.item.ItemResponse;
import com.enigma.api.inventory.models.unit.UnitResponse;
import com.enigma.api.inventory.services.FileService;
import com.enigma.api.inventory.services.ItemService;
import com.enigma.api.inventory.services.UnitService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemService service;

    @MockBean
    ModelMapper modelMapper;

    @MockBean
    UnitService unitService;

    @MockBean
    FileService fileService;

    @Test
    void addShouldReturnEntity() throws Exception {
        Unit unit = new Unit();
        unit.setId(1);
        unit.setCode("kg");
        unit.setDescription("Kilogram");

        UnitResponse unitResponse = new UnitResponse();
        unitResponse.setId(unit.getId());
        unitResponse.setCode(unit.getCode());
        unitResponse.setDescription(unit.getDescription());

        Item entity = new Item();
        entity.setId(1);
        entity.setName("Brambang");
        entity.setPrice(50000);
        entity.setUnit(unit);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(entity.getId());
        itemResponse.setName(entity.getName());
        itemResponse.setPrice(entity.getPrice());
        itemResponse.setUnit(unitResponse);

        when(modelMapper.map(any(ItemRequest.class), any(Class.class))).thenReturn(entity);
        when(unitService.findById(any(Integer.class))).thenReturn(unit);
        when(service.save(any())).thenReturn(entity);
        when(modelMapper.map(any(Item.class), any(Class.class))).thenReturn(itemResponse);

        RequestBuilder request = post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"unitId\": 1, \"name\": \"" + entity.getName() + "\", \"price\": " + entity.getPrice() + "}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.name", is(entity.getName())));
    }

    @Test
    void editShouldReturnEntity() throws Exception {
        Unit unit = new Unit();
        unit.setId(1);
        unit.setCode("kg");
        unit.setDescription("Kilogram");

        UnitResponse unitResponse = new UnitResponse();
        unitResponse.setId(unit.getId());
        unitResponse.setCode(unit.getCode());
        unitResponse.setDescription(unit.getDescription());

        Item entity = new Item();
        entity.setId(1);
        entity.setName("Brambang");
        entity.setPrice(50000);
        entity.setUnit(unit);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(entity.getId());
        itemResponse.setName(entity.getName());
        itemResponse.setPrice(entity.getPrice());
        itemResponse.setUnit(unitResponse);

        when(service.findById(any(Integer.class))).thenReturn(entity);
        when(unitService.findById(any(Integer.class))).thenReturn(unit);
        when(modelMapper.map(any(Item.class), any(Class.class))).thenReturn(itemResponse);

        RequestBuilder request = put("/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"unitId\": 1, \"name\": \"" + entity.getName() + "\", \"price\": " + entity.getPrice() + "}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.name", is(entity.getName())));
    }

    @Test
    void editNonExistShouldReturnError() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = put("/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"unitId\": 1, \"name\": \"aaa\", \"price\": 123}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("error.404.entity", result.getResolvedException().getMessage()));
    }

    @Test
    void removeByIdShouldReturnEntity() throws Exception {
        Unit unit = new Unit();
        unit.setId(1);
        unit.setCode("kg");
        unit.setDescription("Kilogram");

        UnitResponse unitResponse = new UnitResponse();
        unitResponse.setId(unit.getId());
        unitResponse.setCode(unit.getCode());
        unitResponse.setDescription(unit.getDescription());

        Item entity = new Item();
        entity.setId(1);
        entity.setName("Brambang");
        entity.setPrice(50000);
        entity.setUnit(unit);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(entity.getId());
        itemResponse.setName(entity.getName());
        itemResponse.setPrice(entity.getPrice());
        itemResponse.setUnit(unitResponse);

        when(service.removeById(any(Integer.class))).thenReturn(entity);
        when(modelMapper.map(any(Item.class), any(Class.class))).thenReturn(itemResponse);

        RequestBuilder request = delete("/items/1")
                .contentType(MediaType.APPLICATION_JSON);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.name", is(entity.getName())));
    }

    @Test
    void removeNonExistIdShouldReturnError() throws Exception {
        when(service.removeById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = delete("/items/1")
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
        Unit unit = new Unit();
        unit.setId(1);
        unit.setCode("kg");
        unit.setDescription("Kilogram");

        UnitResponse unitResponse = new UnitResponse();
        unitResponse.setId(unit.getId());
        unitResponse.setCode(unit.getCode());
        unitResponse.setDescription(unit.getDescription());

        Item entity = new Item();
        entity.setId(1);
        entity.setName("Brambang");
        entity.setPrice(50000);
        entity.setUnit(unit);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setId(entity.getId());
        itemResponse.setName(entity.getName());
        itemResponse.setPrice(entity.getPrice());
        itemResponse.setUnit(unitResponse);

        when(service.findById(any(Integer.class))).thenReturn(entity);
        when(modelMapper.map(any(Item.class), any(Class.class))).thenReturn(itemResponse);

        RequestBuilder request = get("/items/1");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.name", is(entity.getName())));
    }

    @Test
    void findNonExistIdShouldReturnError() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(null);

        RequestBuilder request = get("/items/1");
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

        RequestBuilder request = get("/items");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.data.list", empty()));
    }

    @Test
    void uploadImageShouldReturnEntity() throws Exception {
        byte[] data = new byte[]{(byte) 0xff, (byte) 0xd8, (byte) 0xff, (byte) 0xdb};

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "pemandangan.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                data
        );

        Item item = new Item();
        item.setId(1);
        when(service.findById(any(Integer.class))).thenReturn(item);

        mvc.perform(multipart("/items/1/image").file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())));
    }

    @Test
    void uploadNonExistIdShouldReturnError() throws Exception {
        byte[] data = new byte[]{(byte) 0xff, (byte) 0xd8, (byte) 0xff, (byte) 0xdb};

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "pemandangan.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                data
        );

        when(service.findById(any(Integer.class))).thenReturn(null);

        mvc.perform(multipart("/items/1/image").file(file))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(HttpStatus.NOT_FOUND.value())))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void downloadImageShouldReturnImage() throws Exception {
        RequestBuilder request = get("/items/image/e7e7d0af-7f49-4270-84ac-752cb5837949.jpg");
        mvc.perform(request)
                .andExpect(status().isOk());
    }
}
