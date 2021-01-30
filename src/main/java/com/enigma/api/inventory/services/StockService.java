package com.enigma.api.inventory.services;

import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;

import java.util.List;

public interface StockService extends CommonService<Stock, Integer> {

    List<StockSummary> findAllSummaries();
}
