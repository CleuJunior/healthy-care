package br.com.cleonildo.entities;

import br.com.cleonildo.dto.PatientRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    private ObjectId id;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private LocalDate birthdate;
    private Address address;
    private String phone;
    private String symptoms;

    public Patient(String firstName, String lastName, LocalDate birthdate, Address address, String phone, String symptoms) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.symptoms = symptoms;
    }

    public Patient(PatientRequest request) {
        this(
                request.firstName(),
                request.lastName(),
                request.birthdate(),
                request.address(),
                request.phone(),
                request.symptoms()
        );
    }

}
