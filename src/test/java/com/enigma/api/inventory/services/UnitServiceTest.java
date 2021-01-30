package com.enigma.api.inventory.services;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.repositories.UnitRepository;
import com.enigma.api.inventory.services.impl.UnitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @InjectMocks
    UnitServiceImpl service;

    @Mock
    UnitRepository repository;

    @Test
    void saveShouldReturnEntity() {
        Unit input = new Unit();

        Unit output = new Unit();
        output.setId(1);
        output.prePersist();
        output.preUpdate();

        when(repository.save(any())).thenReturn(output);

        Unit result = service.save(input);

        assertEquals(output, result);
        assertEquals(output.getCreatedDate(), result.getCreatedDate());
        assertEquals(output.getModifiedDate(), result.getModifiedDate());
    }

    @Test
    void removeByIdShouldReturnEntity() {
        Unit output = new Unit();
        output.setId(1);
        Optional<Unit> optionalUnit = Optional.of(output);

        when(repository.findById(anyInt())).thenReturn(optionalUnit);

        Unit result = service.removeById(1);

        assertEquals(output, result);
    }

    @Test
    void removeByNonExistIdShouldReturnNull() {
        Optional<Unit> optionalUnit = Optional.empty();

        when(repository.findById(anyInt())).thenReturn(optionalUnit);

        Unit result = service.removeById(1);

        assertNull(result);
    }

    @Test
    void findByIdShouldReturnEntity() {
        Unit output = new Unit();
        output.setId(1);
        Optional<Unit> optionalUnit = Optional.of(output);

        when(repository.findById(anyInt())).thenReturn(optionalUnit);

        Unit result = service.findById(1);

        assertEquals(output, result);
    }

    @Test
    void findAllShouldReturnListEntity() {
        List<Unit> units = new ArrayList<>();

        when(repository.findAll(any(Sort.class))).thenReturn(units);

        List<Unit> results = service.findAll();

        assertEquals(units, results);
    }

    @Test
    void findAllPageShouldReturnPage() {
        Unit unit = new Unit();
        List<Unit> units = new ArrayList<>();
        units.add(unit);

        Page<Unit> output = new PageImpl<>(units);

        when(repository.findAll(any(), any(PageRequest.class))).thenReturn(output);

        Page<Unit> results = service.findAll(unit, 1, 1, Sort.Direction.ASC);

        assertEquals(output, results);
    }

}
