package br.com.cleonildo.dto;

import br.com.cleonildo.entities.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

public record PatientRequest(
        @Field("first_name")
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 3, max = 80, message = "{bean.validation.name.min.max.char}")
        String firstName,
        @Field("last_name")
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 3, max = 80, message = "{bean.validation.name.min.max.char}")
        String lastName,
        @JsonFormat(pattern = "dd-MM-yyyy")
        @NotNull(message = "{bean.validation.field.null}")
        @PastOrPresent(message = "{bean.validation.birthdate}")
        LocalDate birthdate,
        @NotNull(message = "{bean.validation.field.null}")
        Address address,
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 12, max = 15, message = "{bean.validation.phone}")
        String phone,
        @NotNull(message = "{bean.validation.field.null}")
        @NotBlank(message = "{bean.validation.field.blank}")
        @Size(min = 5, message = "{bean.validation.symptoms}")
        String symptoms

) { }
