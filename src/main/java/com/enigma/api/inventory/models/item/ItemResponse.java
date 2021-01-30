package com.enigma.api.inventory.models.item;

import com.enigma.api.inventory.models.unit.UnitResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponse {

    private Integer id;
    private String name;
    private Integer price;
    private UnitResponse unit;
    private String imageUrl;
}
