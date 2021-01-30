package com.enigma.api.inventory.repositories;

import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.repositories.impl.StockSummaryRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class StockSummaryRepositoryTest {

    @Autowired
    StockSummaryRepositoryImpl repository;

    @Test
    void findAllSummariesShouldReturnListStockSummary(){
        List<StockSummary> lists = repository.findAllSummaries();
        assertNotNull(lists);
    }
}
