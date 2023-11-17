package databuilder;

import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatientResponseBuilder {
    private static final String FIRST_NAME = "Anna";
    private static final String LAST_NAME = "Vitoria";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1998, 4, 28);
    private static final List<String> PHONES = Collections.singletonList("23 1288293091");
    private static final List<String> SYMPTOMS = Collections.singletonList("Náusea");
    private ObjectId id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Address address;
    private List<String> phones = new ArrayList<>();
    private List<String> symptoms = new ArrayList<>();

    public static PatientResponseBuilder create() {
        return new PatientResponseBuilder();
    }

    public PatientResponseBuilder withId() {
        this.id = new ObjectId();
        return this;
    }

    public PatientResponseBuilder withFirstName() {
        this.firstName = FIRST_NAME;
        return this;
    }

    public PatientResponseBuilder withLastName() {
        this.lastName = LAST_NAME;
        return this;
    }


    public PatientResponseBuilder withAge() {
        this.age = Period.between(BIRTH_DATE, LocalDate.now()).getYears();
        return this;
    }

    public PatientResponseBuilder withAddress() {
        this.address = new Address("Rua do Magnolia", "Sao Paulo", "Sao Paulo", "24804399");
        return this;
    }

    public PatientResponseBuilder withPhone() {
        this.phones = PHONES;
        return this;
    }

    public PatientResponseBuilder withSymptom() {
        this.symptoms = SYMPTOMS;
        return this;
    }

    public PatientResponse build() {
        return new PatientResponse(id, firstName, lastName, age, address, phones, symptoms);
    }
}
