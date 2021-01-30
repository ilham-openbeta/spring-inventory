package com.enigma.api.inventory.repositories;

import com.enigma.api.inventory.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer>, StockSummaryRepository{
}
