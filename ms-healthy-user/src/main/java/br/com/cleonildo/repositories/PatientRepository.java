package br.com.cleonildo.repositories;

import br.com.cleonildo.entities.Patient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findById(ObjectId id);

    // Custom query to find patients by firstName and lastName
//    @Query("{'firstName' : ?0, 'lastName' : ?1}")
    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
