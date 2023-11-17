package br.com.cleonildo.controllers;

import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.services.PatientService;
import databuilder.PatientResponseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    private static final String URL = "/api/v1/patients";
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
    void testGetAllPatients() throws Exception {
        // Arrange
        List<PatientResponse> mockPatientList = singletonList(response);

        given(service.findAllPatient()).willReturn(mockPatientList);

        // Act and Assert
        this.mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(mockPatientList.size()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("gina"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("gina"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value("gina"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("{\"postal_code\":\"24804399\",\"street\":\"Rua do Magnolia\",\"city\":\"Sao Paulo\",\"state\":\"Sao Paulo\"}"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Doe"));
    }

}