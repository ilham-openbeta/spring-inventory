package com.enigma.api.inventory.models.stock;

import com.enigma.api.inventory.models.item.ItemElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class StockResponse {

    private Integer id;
    private ItemElement item;
    private Integer quantity;
}
