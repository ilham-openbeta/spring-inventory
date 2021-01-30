package com.enigma.api.inventory.models.unit;

import com.enigma.api.inventory.models.PageSearch;
import com.enigma.api.inventory.models.validations.Alphabetic;
import lombok.*;

@Getter
@Setter
public class UnitSearch extends PageSearch {

    @Alphabetic
    private String code;
    private String description;
}
