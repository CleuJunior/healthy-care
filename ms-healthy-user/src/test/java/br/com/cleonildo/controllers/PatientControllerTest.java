package br.com.cleonildo.controllers;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.services.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static factory.PatientEntitiesFactory.buildPatientRequest;
import static factory.PatientEntitiesFactory.buildPatientResponse;
import static java.util.Collections.singletonList;
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
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatientService service;
    private PatientResponse response;

    @BeforeEach
    void setup() {
        this.response = buildPatientResponse();
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
                .andExpect(jsonPath("$[0].age").value("25"))
                .andExpect(content().json("[{\"address\":{\"postal_code\":\"24804399\",\"street\":\"Rua do Magnolia\",\"city\":\"Sao Paulo\",\"state\":\"Sao Paulo\"}}]"))
                .andExpect(jsonPath("$[0].phone").value("(16) 92633-7053"))
                .andExpect(jsonPath("$[0].symptoms").value("Náusea e formigamento nos pés"))
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
                .andExpect(jsonPath("$.age").value("25"))
                .andExpect(content().json("{\"address\":{\"postal_code\":\"24804399\",\"street\":\"Rua do Magnolia\",\"city\":\"Sao Paulo\",\"state\":\"Sao Paulo\"}}"))
                .andExpect(jsonPath("$.phone").value("(16) 92633-7053"))
                .andExpect(jsonPath("$.symptoms").value("Náusea e formigamento nos pés"))
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
        this.mockMvc.perform(get(URL)
                        .param("firstName", firstName)
                        .param("lastName", lastName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.timestamp").value("1604625859"))
                .andExpect(jsonPath("$.id.date").value("2020-11-06T01:24:19.000+00:00"))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Vitoria"))
                .andExpect(jsonPath("$.age").value("25"))
                .andExpect(content().json("{\"address\":{\"postal_code\":\"24804399\",\"street\":\"Rua do Magnolia\",\"city\":\"Sao Paulo\",\"state\":\"Sao Paulo\"}}"))
                .andExpect(jsonPath("$.phone").value("(16) 92633-7053"))
                .andExpect(jsonPath("$.symptoms").value("Náusea e formigamento nos pés"))
                .andDo(print());

        verify(service).findByFirstNameAndLastName(firstName, lastName);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should return patient when calling savePatient with valid request")
    void shouldReturnPatient_WhenCallingSavePatientWithValidRequest() throws Exception {
        // Arrange
        var request = buildPatientRequest();

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
        var request = buildPatientRequest();

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
                .andExpect(jsonPath("$.age").value("25"))
                .andExpect(content().json("{\"address\":{\"postal_code\":\"24804399\",\"street\":\"Rua do Magnolia\",\"city\":\"Sao Paulo\",\"state\":\"Sao Paulo\"}}"))
                .andExpect(jsonPath("$.phone").value("(16) 92633-7053"))
                .andExpect(jsonPath("$.symptoms").value("Náusea e formigamento nos pés"))
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