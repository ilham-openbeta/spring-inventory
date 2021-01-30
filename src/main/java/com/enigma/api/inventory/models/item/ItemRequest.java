package com.enigma.api.inventory.models.item;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {

    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer unitId;

}
