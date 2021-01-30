package com.enigma.api.inventory.services.impl;

import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.repositories.StockRepository;
import com.enigma.api.inventory.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl extends CommonServiceImpl<Stock, Integer> implements StockService {

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        super(stockRepository);
    }

    @Override
    public List<StockSummary> findAllSummaries() {
        return ((StockRepository) repository).findAllSummaries();
    }
}
