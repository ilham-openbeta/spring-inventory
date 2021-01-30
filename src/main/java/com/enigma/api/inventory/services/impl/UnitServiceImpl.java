package com.enigma.api.inventory.services.impl;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.repositories.UnitRepository;
import com.enigma.api.inventory.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends CommonServiceImpl<Unit, Integer> implements UnitService {

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        super(unitRepository);
    }
}
