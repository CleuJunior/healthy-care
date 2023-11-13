package br.com.cleonildo.services;

import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import br.com.cleonildo.repositories.PatientRepository;
import databuilder.PatientBuilder;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository repository;
    @InjectMocks
    private PatientService service;
    private final Address address = new Address("Rua do Magnolia", "Sao Paulo", "Sao Paulo", "24804399");
    private final ObjectId objectId = new ObjectId();
    private static final String FIRST_NAME = "Anna";
    private static final String LAST_NAME = "Vitoria";
    private static final List<String> PHONES = Collections.singletonList("23 1288293091");
    private static final List<String> SYMPTOMS = Collections.singletonList("Náusea");
    private Patient patient;

    @BeforeEach
    void setup() {
        this.patient = PatientBuilder
                .create()
                .withId(objectId)
                .withFirstName(FIRST_NAME)
                .withLastName(LAST_NAME)
                .withAddress(address)
                .withPhone(PHONES)
                .withSymptom(SYMPTOMS)
                .build();
    }

    @Test
    @DisplayName("Return the list of all patient")
    void shouldReturnListPatient_WhenCallingFindAllPatient() {
        given(repository.findAll()).willReturn(Collections.singletonList(patient));

        var listPatientResponse = this.service.findAllPatient();

        assertThat(listPatientResponse.isEmpty(), is(false));
        assertThat(listPatientResponse.size(), is(1));
        this.commonAssertions(listPatientResponse.get(0));
    }

    private void commonAssertions(PatientResponse response) {
        assertThat(response.id(), is(objectId));
        assertThat(response.firstName(), is(FIRST_NAME));
        assertThat(response.lastName(), is(LAST_NAME));
    }

}