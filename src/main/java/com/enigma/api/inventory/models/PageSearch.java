package com.enigma.api.inventory.models;

import lombok.*;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;

@Getter
@Setter
@NoArgsConstructor
public class PageSearch {

    private Integer page = 0;

    @Max(100)
    private Integer size = 10;

    private Sort.Direction sort = Sort.Direction.ASC;

}
