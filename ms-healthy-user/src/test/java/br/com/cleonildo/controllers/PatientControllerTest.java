package br.com.cleonildo.controllers;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Address;
import br.com.cleonildo.services.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import databuilder.PatientRequestBuilder;
import databuilder.PatientResponseBuilder;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    private static final String URL = "/api/v1/patients";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final String ADDRESS_JSON = "{\"address\":{\"postal_code\":\"24804399\",\"street\":\"Rua do Magnolia\",\"city\":\"Sao Paulo\",\"state\":\"Sao Paulo\"}}";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService service;
    private PatientResponse response;

    @BeforeEach
    void setup() {
        this.response = PatientResponseBuilder
                .create()
                .withId()
                .withFirstName()
                .withLastName()
                .withAge()
                .withAddress()
                .withPhone()
                .withSymptom()
                .build();
    }

    @Test
    @DisplayName("Should return a list of patients when calling getAllPatients")
    void shouldReturnListPatient_WhenCallingGetAllPatients() throws Exception {
        // Arrange
        given(service.findAllPatient()).willReturn(singletonList(response));

        // Act and Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.timestamp").value("1604625859"))
                .andExpect(jsonPath("$[0].id.date").value("2020-11-06T01:24:19.000+00:00"))
                .andExpect(jsonPath("$[0].firstName").value("Anna"))
                .andExpect(jsonPath("$[0].lastName").value("Vitoria"))
                .andExpect(jsonPath("$[0].age").value("24"))
                .andExpect(content().json("[" + ADDRESS_JSON + "]"))
                .andExpect(jsonPath("$[0].phones", containsInAnyOrder("(16) 92633-7053", "(54) 93858-3963")))
                .andExpect(jsonPath("$[0].symptoms", containsInAnyOrder("Náusea", "Formigamento nos pés")))
                .andDo(print());

        verify(service).findAllPatient();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should return a patient when calling getPatientById with a valid ID")
    void shouldReturnPatient_WhenCallingGetPatientByIdWithValidId() throws Exception {
        // Arrange

        given(service.findById(any(ObjectId.class))).willReturn(response);

        // Act and Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL + "/5fa4a5c3a2e74c2b6c8f50a1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.timestamp").value("1604625859"))
                .andExpect(jsonPath("$.id.date").value("2020-11-06T01:24:19.000+00:00"))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Vitoria"))
                .andExpect(jsonPath("$.age").value("24"))
                .andExpect(content().json(ADDRESS_JSON))
                .andExpect(jsonPath("$.phones", containsInAnyOrder("(16) 92633-7053", "(54) 93858-3963")))
                .andExpect(jsonPath("$.symptoms", containsInAnyOrder("Náusea", "Formigamento nos pés")))
                .andDo(print());

        verify(service).findById(any(ObjectId.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should return patient when calling getPatientByFirstAndLastName with valid parameters")
    void shouldReturnPatient_WhenCallingGetPatientByFirstAndLastNameWithValidParameters() throws Exception {
        // Arrange
        var firstName = "Anna";
        var lastName = "Vitoria";

        given(service.findByFirstNameAndLastName(firstName, lastName)).willReturn(response);

        // Act and Assert
        this.mockMvc.perform(get(URL + "/search")
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.timestamp").value("1604625859"))
                .andExpect(jsonPath("$.id.date").value("2020-11-06T01:24:19.000+00:00"))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Vitoria"))
                .andExpect(jsonPath("$.age").value("24"))
                .andExpect(content().json(ADDRESS_JSON))
                .andExpect(jsonPath("$.phones", containsInAnyOrder("(16) 92633-7053", "(54) 93858-3963")))
                .andExpect(jsonPath("$.symptoms", containsInAnyOrder("Náusea", "Formigamento nos pés")))
                .andDo(print());

        verify(service).findByFirstNameAndLastName(firstName, lastName);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should return patient when calling savePatient with valid request")
    void shouldReturnPatient_WhenCallingSavePatientWithValidRequest() throws Exception {
        // Arrange
        var request = PatientRequestBuilder
                .create()
                .withFirstName("Anna")
                .withLastName("Vitoria")
                .withBirthDate(LocalDate.of(1998, 4, 28))
                .withAddress(new Address("Rua do Magnolia", "Sao Paulo", "Sao Paulo", "24804399"))
                .withPhone(List.of("(16) 92633-7053", "(54) 93858-3963"))
                .withSymptom(List.of("Náusea", "Formigamento nos pés"))
                .build();

        // Mocking the service method
        given(service.savePatient(request)).willReturn(response);

        // Act and Assert
        this.mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andDo(print());

        verify(service).savePatient(request);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should return updated patient when calling updatePatient with valid data")
    void shouldReturnUpdatedPatient_WhenCallingUpdatePatientWithValidData() throws Exception {
        // Arrange
        var id = "5fa4a5c3a2e74c2b6c8f50a1";

        var request = PatientRequestBuilder
                .create()
                .withFirstName("Anna")
                .withLastName("Vitoria")
                .withBirthDate(LocalDate.of(1998, 4, 28))
                .withAddress(new Address("Rua do Magnolia", "Sao Paulo", "Sao Paulo", "24804399"))
                .withPhone(List.of("(16) 92633-7053", "(54) 93858-3963"))
                .withSymptom(List.of("Náusea", "Formigamento nos pés"))
                .build();

        given(service.updatePatient(any(ObjectId.class), any(PatientRequest.class))).willReturn(response);

        // Act and Assert
        this.mockMvc.perform(put(URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.timestamp").value("1604625859"))
                .andExpect(jsonPath("$.id.date").value("2020-11-06T01:24:19.000+00:00"))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Vitoria"))
                .andExpect(jsonPath("$.age").value("24"))
                .andExpect(content().json(ADDRESS_JSON))
                .andExpect(jsonPath("$.phones", containsInAnyOrder("(16) 92633-7053", "(54) 93858-3963")))
                .andExpect(jsonPath("$.symptoms", containsInAnyOrder("Náusea", "Formigamento nos pés")))
                .andDo(print());

        verify(service).updatePatient(any(ObjectId.class), any(PatientRequest.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should delete patient when calling deletePatientById with valid ID")
    void shouldDeletePatient_WhenCallingDeletePatientByIdWithValidId() throws Exception {
        // Arrange
        var id = "5fa4a5c3a2e74c2b6c8f50a1";

        // Act and Assert
        this.mockMvc.perform(delete(URL + "/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(service).deletePatientById(any(ObjectId.class));
        verifyNoMoreInteractions(service);
    }
}