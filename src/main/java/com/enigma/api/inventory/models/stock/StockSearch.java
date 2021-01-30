package com.enigma.api.inventory.models.stock;

import com.enigma.api.inventory.models.PageSearch;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class StockSearch extends PageSearch {

    private String quantity;
}
