package br.com.cleonildo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Patient {

    @Id
    private ObjectId id;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private LocalDate birthdate;
    private Address address;
    private List<String> phones;
    private List<String> symptoms;

    public Patient(String firstName, String lastName, LocalDate birthdate, Address address, List<String> phones,
                   List<String> symptoms) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.phones = phones;
        this.symptoms = symptoms;
    }

}
