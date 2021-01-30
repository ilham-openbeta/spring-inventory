package com.enigma.api.inventory.models.item;

import com.enigma.api.inventory.models.PageSearch;
import lombok.*;

@Getter
@Setter
public class ItemSearch extends PageSearch {

    private String name;

}
