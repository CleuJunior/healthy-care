package br.com.cleonildo.dto;

import br.com.cleonildo.entities.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

public record PatientRequest(
        @Field("first_name") String firstName,
        @Field("last_name") String lastName,
        @JsonFormat(pattern = "dd-MM-yyyy") LocalDate birthdate,
        Address address,
        List<String> phones,
        List<String> symptoms
) {}
