package br.com.cleonildo.dto;

import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

public record PatientResponse(
        ObjectId id,
        @Field("first_name")
        String firstName,
        @Field("last_name")
        String lastName,
        Integer age,
        Address address,
        String phone,
        String symptoms
)
{
    public PatientResponse(Patient patient) {
        this(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                Period.between(patient.getBirthdate(), LocalDate.now()).getYears(),
                patient.getAddress(),
                patient.getPhone(),
                patient.getSymptoms()
        );
    }
}
