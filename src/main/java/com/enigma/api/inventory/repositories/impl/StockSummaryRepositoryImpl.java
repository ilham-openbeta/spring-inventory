package com.enigma.api.inventory.repositories.impl;

import com.enigma.api.inventory.entities.Stock;
import com.enigma.api.inventory.entities.StockSummary;
import com.enigma.api.inventory.repositories.StockSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class StockSummaryRepositoryImpl implements StockSummaryRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<StockSummary> findAllSummaries() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockSummary> criteria = builder.createQuery(StockSummary.class);
        Root<Stock> root = criteria.from(Stock.class);

        criteria.multiselect(root.get("item"), builder.sum(root.get("quantity")))
                .groupBy(root.get("item"));

        return entityManager.createQuery(criteria).getResultList();
    }
}
