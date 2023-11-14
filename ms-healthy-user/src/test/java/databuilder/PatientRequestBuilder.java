package databuilder;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientRequestBuilder {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Address address;
    private List<String> phones = new ArrayList<>();
    private List<String> symptoms = new ArrayList<>();

    private PatientRequestBuilder() {
    }

    public static PatientRequestBuilder create() {
        return new PatientRequestBuilder();
    }

    public PatientRequestBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PatientRequestBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public PatientRequestBuilder withBirthDate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public PatientRequestBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public PatientRequestBuilder withPhone(List<String> phones) {
        this.phones = phones;
        return this;
    }

    public PatientRequestBuilder withSymptom(List<String> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public PatientRequest build() {
        return new PatientRequest(firstName, lastName, birthdate, address, phones, symptoms);
    }
}
