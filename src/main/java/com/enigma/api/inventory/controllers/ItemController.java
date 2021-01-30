package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.ImageUploadRequest;
import com.enigma.api.inventory.models.PagedList;
import com.enigma.api.inventory.models.ResponseMessage;
import com.enigma.api.inventory.models.item.ItemElement;
import com.enigma.api.inventory.models.item.ItemRequest;
import com.enigma.api.inventory.models.item.ItemResponse;
import com.enigma.api.inventory.models.item.ItemSearch;
import com.enigma.api.inventory.services.FileService;
import com.enigma.api.inventory.services.ItemService;
import com.enigma.api.inventory.services.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/items")
@RestController
public class ItemController {

    @Autowired
    private ItemService service;

    @Autowired
    private UnitService unitService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Add item", description = "Add an item without image", tags = {"item"})
    @PostMapping(produces = "application/json")
    public ResponseMessage<ItemResponse> add(@RequestBody @Valid ItemRequest model) {
        Item entity = modelMapper.map(model, Item.class);

        Unit unit = unitService.findById(model.getUnitId());
        entity.setUnit(unit);

        entity = service.save(entity);
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Edit item", description = "Edit an item without image", tags = {"item"})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<ItemResponse> edit(@PathVariable Integer id, @RequestBody @Valid ItemRequest model) {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        Unit unit = unitService.findById(model.getUnitId());
        entity.setUnit(unit);

        modelMapper.map(model, entity);
        service.save(entity);

        ItemResponse data = modelMapper.map(entity, ItemResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Delete item", description = "Delete an item", tags = {"item"})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<ItemResponse> removeById(@PathVariable Integer id) {
        Item entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Find item", description = "Find an Item", tags = {"item"})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<ItemResponse> findById(@PathVariable Integer id) {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ItemResponse data = modelMapper.map(entity, ItemResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all item", description = "Get all item", tags = {"item"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ResponseMessage.class))
            )})
    @GetMapping(produces = "application/json")
    public ResponseMessage<PagedList<ItemElement>> findAll(
            @Valid ItemSearch model
    ) {
        Item search = modelMapper.map(model, Item.class);

        Page<Item> entityPage = service.findAll(search, model.getPage(), model.getSize(), model.getSort());

        List<Item> entities = entityPage.toList();

        List<ItemElement> models = entities.stream()
                .map(e -> modelMapper.map(e, ItemElement.class))
                .collect(Collectors.toList());

        PagedList<ItemElement> data = new PagedList<>(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Add image", description = "Add an image to item", tags = {"item"})
    @PostMapping(value = "/{id}/image", consumes = "multipart/form-data", produces = "application/json")
    public ResponseMessage<ItemResponse> upload(@PathVariable Integer id,
                                  @Valid ImageUploadRequest model) throws Exception {
        Item entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(model.getFile().getInputStream());
        String filename = fileService.upload("item", bufferedInputStream);
        entity.setImageUrl("/items/image/" + filename);
        entity.setOriginalFilename(model.getFile().getOriginalFilename());
        service.save(entity);

        ItemResponse itemResponse = modelMapper.map(entity, ItemResponse.class);

        return ResponseMessage.success(itemResponse);
    }

    @Operation(summary = "View image", description = "View image", tags = {"item"})
    @GetMapping(value = "/image/{filename}", produces = "image/jpeg")
    public void download(
            @PathVariable String filename, HttpServletResponse response
    ) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + filename + "\"");
        fileService.download("item", filename, response.getOutputStream());
    }
}
