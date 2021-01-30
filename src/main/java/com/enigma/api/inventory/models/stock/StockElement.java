package com.enigma.api.inventory.models.stock;

import com.enigma.api.inventory.models.item.ItemElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockElement {

    private Integer id;
    private ItemElement item;
    private Integer quantity;
}
