package com.enigma.api.inventory.configs;

import com.enigma.api.inventory.entities.Unit;
import com.enigma.api.inventory.models.unit.UnitResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy((MatchingStrategies.STRICT));
        modelMapper.typeMap(UnitResponse.class, Unit.class).addMappings(mapper -> mapper.skip(Unit::setId));

        return modelMapper;
    }
}
