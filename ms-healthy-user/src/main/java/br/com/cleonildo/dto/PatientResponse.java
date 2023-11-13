package br.com.cleonildo.dto;

import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public record PatientResponse(
        @Id ObjectId id,
        @Field("first_name") String firstName,
        @Field("last_name") String lastName,
        Address address,
        List<String> phones,
        List<String> symptoms
)
{
    public PatientResponse(Patient patient) {
        this(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAddress(),
                patient.getPhones(),
                patient.getSymptoms()
        );
    }
}
