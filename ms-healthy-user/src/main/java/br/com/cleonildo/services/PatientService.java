package br.com.cleonildo.services;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.entities.Patient;
import br.com.cleonildo.repositories.PatientRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientResponse> findAllPatient() {
        logger.info("List returned successfully");

        return this.repository.findAll().stream()
                .filter(Objects::nonNull)
                .map(PatientResponse::new)
                .toList();
    }

    public PatientResponse findById(ObjectId id) {
        var patient = this.repository.findById(id).get();

        logger.info("Patient found: {}", patient);
        return new PatientResponse(patient);
    }

    public PatientResponse findByFirstNameAndLastName(String firsName, String lastName) {
        var patient = this.repository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firsName, lastName).get();

        logger.info("Patient found: {}", patient);
        return new PatientResponse(patient);
    }

    @Transactional
    public PatientResponse savePatient(PatientRequest request) {
        var patient = new Patient(request);

        var patientResponse = this.repository.save(patient);

        logger.info("Patient saved: {}", patientResponse);
        return new PatientResponse(patientResponse);
    }

    @Transactional
    public PatientResponse updatePatient(ObjectId objectId, PatientRequest request) {
        var patient = this.repository.findById(objectId).get();

        this.updatePatientFromRequest(patient, request);
        this.repository.save(patient);

        logger.info("Patient updated: {}", patient);
        return new PatientResponse(patient);
    }

    private void updatePatientFromRequest(Patient patient, PatientRequest request) {
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setBirthdate(request.birthdate());
        patient.setAddress(request.address());
        patient.setPhone(request.phone());
        patient.setSymptoms(request.symptoms());
    }

    @Transactional
    public void deletePatientById(ObjectId objectId) {
        var patient = this.repository.findById(objectId).get();

        this.repository.delete(patient);
        logger.info("Patient deleted: {}", patient);
    }
}
