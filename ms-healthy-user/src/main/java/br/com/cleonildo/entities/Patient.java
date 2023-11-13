package br.com.cleonildo.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document
@NoArgsConstructor
@Getter @Setter
public class Patient {

    @Id
    private ObjectId id;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private Address address;
    @Setter(AccessLevel.NONE)
    private List<String> phones;
    private List<String> symptoms;

    public Patient(String firstName, String lastName, Address address, List<String> phones, List<String> symptoms) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phones = phones;
        this.symptoms = symptoms;
    }
}
