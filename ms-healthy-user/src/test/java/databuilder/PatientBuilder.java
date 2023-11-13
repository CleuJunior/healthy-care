package databuilder;

import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class PatientBuilder {

    private ObjectId id;
    private String firstName;
    private String lastName;
    private Address address;
    private List<String> phones = new ArrayList<>();
    private List<String> symptoms = new ArrayList<>();

    private PatientBuilder() {
    }

    public static PatientBuilder create() {
        return new PatientBuilder();
    }

    public PatientBuilder withId(ObjectId id) {
        this.id = id;
        return this;
    }

    public PatientBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PatientBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PatientBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public PatientBuilder withPhone(List<String> phones) {
        this.phones = phones;
        return this;
    }

    public PatientBuilder withSymptom(List<String> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public Patient build() {
        return new Patient(id, firstName, lastName, address, phones, symptoms);
    }
}
