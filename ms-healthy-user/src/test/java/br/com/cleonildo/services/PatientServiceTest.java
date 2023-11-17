package br.com.cleonildo.services;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Address;
import br.com.cleonildo.entities.Patient;
import br.com.cleonildo.repositories.PatientRepository;
import databuilder.PatientBuilder;
import databuilder.PatientRequestBuilder;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

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
    private static final LocalDate BIRTH_DATE = LocalDate.of(1998, 4, 28);
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
                .withBirthDate(BIRTH_DATE)
                .withAddress(address)
                .withPhone(PHONES)
                .withSymptom(SYMPTOMS)
                .build();
    }

    @Test
    @DisplayName("Return the list of all patient")
    void shouldReturnListPatient_WhenCallingFindAllPatient() {
        given(repository.findAll()).willReturn(singletonList(patient));

        var listPatientResponse = this.service.findAllPatient();

        assertThat(listPatientResponse.isEmpty(), is(false));
        assertThat(listPatientResponse.size(), is(1));
        this.commonAssertions(listPatientResponse.get(0));
    }

    @Test
    @DisplayName("Return patient by Id")
    void shouldReturnPatient_WhenCallingFindPatientById_WithValidId() {
        given(repository.findById(any(ObjectId.class))).willReturn(Optional.of(patient));

        var patientResponse = this.service.findById(objectId);

        this.commonAssertions(patientResponse);
    }

    @Test
    @DisplayName("Return patient by First and Last name")
    void shouldReturnPatient_WhenCallingFindByFirstNameAndLastName() {
        given(repository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(anyString(), anyString())).willReturn(Optional.of(patient));

        var patientResponse = this.service.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME);

        this.commonAssertions(patientResponse);
    }

    @Test
    @DisplayName("Return patient when saved in database")
    void shouldReturnPatient_WhenCallingSavePatient() {
        given(repository.save(any(Patient.class))).willReturn(patient);

        var request = new PatientRequest(FIRST_NAME, LAST_NAME, BIRTH_DATE, address, PHONES, SYMPTOMS);

        var patientResponse = this.service.savePatient(request);

        this.commonAssertions(patientResponse);
    }

    @Test
    @DisplayName("Return patient with Update filds")
    void shouldReturnPatient_WhenCallingUpdatePatient() {
        var addressUpdate = new Address("Rua Update","Update","Estado Update","00000-000");
        var updateFirstName = "UpdateFirstName";
        var updateLastName = "UpdateLastName";
        var updateBirthdate = LocalDate.of(1990, 11, 9);
        var age =  Period.between(updateBirthdate, LocalDate.now()).getYears();
        var phones = singletonList("99 99999999");
        var symptoms = singletonList("Update");


        given(repository.findById(any(ObjectId.class))).willReturn(Optional.of(patient));
        given(repository.save(any(Patient.class))).willReturn(patient);


        var request = PatientRequestBuilder
                .create()
                .withFirstName(updateFirstName)
                .withLastName(updateLastName)
                .withBirthDate(updateBirthdate)
                .withAddress(addressUpdate)
                .withPhone(phones)
                .withSymptom(symptoms)
                .build();


        var response = this.service.updatePatient(objectId, request);

        assertThat(response.id(), is(objectId));
        assertThat(response.firstName(), is(updateFirstName));
        assertThat(response.lastName(), is(updateLastName));
        assertThat(response.age(), is(age));
        assertThat(response.address(), is(addressUpdate));
        assertThat(response.phones(), is(phones));
        assertThat(response.symptoms(), is(symptoms));
    }

    @Test
    @DisplayName("Delete patient by Id")
    void shouldDeletePatient_WhenCallingDeletePatientById() {
        given(repository.findById(any(ObjectId.class))).willReturn(Optional.of(patient));
        doNothing().when(repository).delete(patient);

        this.service.deletePatientById(objectId);

        verify(repository).findById(any(ObjectId.class));
        verify(repository).delete(patient);
    }

    private void commonAssertions(PatientResponse response) {
        int age = Period.between(patient.getBirthdate(), LocalDate.now()).getYears();

        assertThat(response.id(), is(objectId));
        assertThat(response.firstName(), is(FIRST_NAME));
        assertThat(response.lastName(), is(LAST_NAME));
        assertThat(response.age(), is(age));
    }

}