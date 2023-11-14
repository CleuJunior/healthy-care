package br.com.cleonildo.dto;

import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

public record PatientResponse(
        @Id ObjectId id,
        @Field("first_name") String firstName,
        @Field("last_name") String lastName,
        Integer age,
        Address address,
        @Field("phones_numbers")
        Collection<String> phones,
        Collection<String> symptoms
)
{
    public PatientResponse(Patient patient) {
        this(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                Period.between(patient.getBirthdate(), LocalDate.now()).getYears(),
                patient.getAddress(),
                patient.getPhones(),
                patient.getSymptoms()
        );
    }
}
