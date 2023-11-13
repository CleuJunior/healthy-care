package br.com.cleonildo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

public record Address(
        String street,
        String city,
        String state,
        @JsonProperty("postal_code")
        @Field("postal_code")
        String postalCode
) {}
