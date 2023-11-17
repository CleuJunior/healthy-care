package br.com.cleonildo.repositories;

import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findById(ObjectId id);
    Optional<Patient> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
