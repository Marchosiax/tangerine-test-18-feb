package com.marchosiax.tangerine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDTO(
        Long id,
        @JsonProperty("prdname")
        String name
) {
}
