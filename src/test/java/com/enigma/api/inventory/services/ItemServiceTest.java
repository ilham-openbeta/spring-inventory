package com.enigma.api.inventory.services;

import com.enigma.api.inventory.entities.Item;
import com.enigma.api.inventory.repositories.ItemRepository;
import com.enigma.api.inventory.services.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    ItemServiceImpl service;

    @Mock
    ItemRepository repository;

    @Test
    void saveShouldReturnEntity() {
        Item input = new Item();

        Item output = new Item();
        output.setId(1);

        when(repository.save(any())).thenReturn(output);

        Item result = service.save(input);

        assertEquals(output, result);
    }

}
