package com.enigma.api.inventory.services;

import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.repositories.StockRepository;
import com.enigma.api.inventory.services.impl.StockServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks
    StockServiceImpl service;

    @Mock
    StockRepository repository;

    @Test
    void saveShouldReturnEntity() {
        Stock input = new Stock();

        Stock output = new Stock();
        output.setId(1);

        when(repository.save(any())).thenReturn(output);

        Stock result = service.save(input);

        assertEquals(output, result);
    }

    @Test
    void findAllSummariesShouldReturnListEntity(){
        List<StockSummary> summaryList = service.findAllSummaries();
        assertNotNull(summaryList);
    }

}
