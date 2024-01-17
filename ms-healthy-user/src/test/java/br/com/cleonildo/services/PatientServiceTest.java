package br.com.cleonildo.services;

import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Patient;
import br.com.cleonildo.repositories.PatientRepository;
import factory.PatientEntitiesFactory;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
    @Mock
    private PatientRepository repository;
    @InjectMocks
    private PatientService service;
    private final ObjectId objectId = new ObjectId("5fa4a5c3a2e74c2b6c8f50a1");
    private Patient patient;
    private PatientResponse excpetedResponse;

    @BeforeEach
    void setup() {
        this.patient = PatientEntitiesFactory.buildPatient();
        this.excpetedResponse = PatientEntitiesFactory.buildPatientResponse();
    }

    @Test
    @DisplayName("Return the list of all patient")
    void shouldReturnListPatient_WhenCallingFindAllPatient() {
        given(this.repository.findAll())
                .willReturn(singletonList(this.patient));

        var listPatientResponse = this.service.findAllPatient();

        assertThat(listPatientResponse.size(), is(1));
        assertThat(listPatientResponse.get(0), is(this.excpetedResponse));

        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Return patient by Id")
    void shouldReturnPatient_WhenCallingFindPatientById_WithValidId() {
        given(this.repository.findById(any(ObjectId.class)))
                .willReturn(Optional.of(this.patient));

        var actualResponse = this.service.findById(this.objectId);

        assertThat(actualResponse, is(this.excpetedResponse));

        verify(this.repository).findById(any(ObjectId.class));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Return patient by First and Last name")
    void shouldReturnPatient_WhenCallingFindByFirstNameAndLastName() {
        given(this.repository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(anyString(), anyString()))
                .willReturn(Optional.of(patient));

        var actualResponse = this.service.findByFirstNameAndLastName("Anna", "Vitoria");

        assertThat(actualResponse, is(this.excpetedResponse));

        verify(this.repository).findByFirstNameIgnoreCaseAndLastNameIgnoreCase(anyString(), anyString());
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Return patient when saved in database")
    void shouldReturnPatient_WhenCallingSavePatient() {
        given(this.repository.save(any(Patient.class)))
                .willReturn(this.patient);

        var request = PatientEntitiesFactory.buildPatientRequest();
        var actualResponse = this.service.savePatient(request);

        assertThat(actualResponse, is(this.excpetedResponse));

        verify(this.repository).save(any(Patient.class));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Return patient with Update filds")
    void shouldReturnPatient_WhenCallingUpdatePatient() {
        given(this.repository.findById(any(ObjectId.class)))
                .willReturn(Optional.of(this.patient));

        given(this.repository.save(any(Patient.class)))
                .willReturn(PatientEntitiesFactory.buildPatientUpdate());


        var excpetedPatientUpdate = PatientEntitiesFactory.buildPatientResponseUpdate();
        var request = PatientEntitiesFactory.buildPatientRequestUpdate();

        var actualResponseUpdate = this.service.updatePatient(objectId, request);

        assertThat(actualResponseUpdate, is(excpetedPatientUpdate));

        verify(this.repository).findById(any(ObjectId.class));
        verify(this.repository).save(any(Patient.class));
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Delete patient by Id")
    void shouldDeletePatient_WhenCallingDeletePatientById() {
        given(this.repository.findById(any(ObjectId.class)))
                .willReturn(Optional.of(this.patient));

        doNothing().when(this.repository).delete(this.patient);

        this.service.deletePatientById(this.objectId);

        verify(this.repository).findById(any(ObjectId.class));
        verify(this.repository).delete(this.patient);
        verifyNoMoreInteractions(this.repository);
    }

}