package br.com.cleonildo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Field;

public record Address(
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 10, message = "{bean.validation.street.name}")
        String street,
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 4, message = "{bean.validation.city.name}")
        String city,

        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 5, message = "{bean.validation.state.name}")
        String state,
        @JsonProperty("zip_code")
        @Field("zip_code")
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 9, max = 9, message = "{bean.validation.zip.code}")
        String zipCode
) {}
