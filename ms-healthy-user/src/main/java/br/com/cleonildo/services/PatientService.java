package br.com.cleonildo.services;

import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Patient;
import br.com.cleonildo.repositories.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponse> findAllPatient() {
        LOGGER.info("List returned successfully");
        return this.repository
                .findAll()
                .stream()
                .map(PatientResponse::new)
                .toList();
    }

    public Patient savePatient(Patient patient) {
        return this.repository.save(patient);
    }
}
