package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.exceptions.EntityNotFoundException;
import com.enigma.api.inventory.models.*;
import com.enigma.api.inventory.models.stock.StockElement;
import com.enigma.api.inventory.models.stock.StockRequest;
import com.enigma.api.inventory.models.stock.StockResponse;
import com.enigma.api.inventory.models.stock.StockSearch;
import com.enigma.api.inventory.services.ItemService;
import com.enigma.api.inventory.services.StockService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/stocks")
@RestController
public class StockController {

    @Autowired
    private StockService service;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Add stock", description = "Add stock to item", tags = {"stock"})
    @PostMapping(produces = "application/json")
    public ResponseMessage<StockResponse> add(@RequestBody @Valid StockRequest model) {
        Stock entity = modelMapper.map(model, Stock.class);

        Item item = itemService.findById(model.getItemId());
        entity.setItem(item);

        entity = service.save(entity);
        StockResponse data = modelMapper.map(entity, StockResponse.class);
        return ResponseMessage.success(data);
    }

    @Operation(summary = "Edit stock", description = "Edit stock", tags = {"stock"})
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<StockResponse> edit(@PathVariable Integer id, @RequestBody @Valid StockRequest model) {
        Stock entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        Item item = itemService.findById(model.getItemId());
        entity.setItem(item);

        modelMapper.map(model, entity);
        service.save(entity);

        StockResponse data = modelMapper.map(entity, StockResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Delete stock", description = "Delete stock", tags = {"stock"})
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<StockResponse> removeById(@PathVariable Integer id) {
        Stock entity = service.removeById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        StockResponse data = modelMapper.map(entity, StockResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Find stock", description = "Find stock data", tags = {"stock"})
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseMessage<StockResponse> findById(@PathVariable Integer id) {
        Stock entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        StockResponse data = modelMapper.map(entity, StockResponse.class);

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all stock", description = "Get all stock data", tags = {"stock"})
    @GetMapping(produces = "application/json")
    public ResponseMessage<PagedList<StockElement>> findAll(
            @Valid StockSearch model
    ) {
        Stock search = modelMapper.map(model, Stock.class);

        Page<Stock> entityPage = service.findAll(search, model.getPage(), model.getSize(), model.getSort());

        List<Stock> entities = entityPage.toList();

        List<StockElement> models = entities.stream()
                .map(e -> modelMapper.map(e, StockElement.class))
                .collect(Collectors.toList());

        PagedList<StockElement> data = new PagedList<>(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }

    @Operation(summary = "Get all stock summary", description = "Get all stock summary data", tags = {"stock"})
    @GetMapping(value = "/summaries", produces = "application/json")
    public ResponseMessage<List<StockSummary>> findAllSummaries() {
        List<StockSummary> entityPage = service.findAllSummaries();
        return ResponseMessage.success(entityPage);
    }

}
