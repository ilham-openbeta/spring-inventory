package com.enigma.api.inventory.models.stock;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class StockRequest {

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer itemId;
}
