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

    public PatientResponse findById(ObjectId id) {
        var patientOptional = this.repository.findById(id).get();

        LOGGER.info("Patient found");

        return new PatientResponse(patientOptional);

    }

    public PatientResponse findByFirstNameAndLastName(String firsName, String lastName) {
        var patientOptional = this.repository.findByFirstNameIgnoreCaseAndLastNameIgnoreCase(firsName, lastName).get();

        LOGGER.info("Patient found");

        return new PatientResponse(patientOptional);
    }

    @Transactional
    public PatientResponse savePatient(PatientRequest request) {
        var patient = Patient
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthdate(request.birthdate())
                .address(request.address())
                .phones(request.phones())
                .symptoms(request.symptoms())
                .build();

        var patientResponse =  this.repository.save(patient);

        return new PatientResponse(patientResponse);
    }

    @Transactional
    public PatientResponse updatePatient(ObjectId objectId, PatientRequest request) {
        var patient = this.repository.findById(objectId).get();

        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setBirthdate(request.birthdate());
        patient.setAddress(request.address());
        patient.setPhones(request.phones());
        patient.setSymptoms(request.symptoms());

        this.repository.save(patient);

        return new PatientResponse(patient);
    }

    @Transactional
    public void deletePatientById(ObjectId objectId) {
        var patient = this.repository.findById(objectId).get();

        this.repository.delete(patient);
    }

}
