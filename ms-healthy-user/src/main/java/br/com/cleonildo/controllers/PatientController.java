package br.com.cleonildo.controllers;

import br.com.cleonildo.dto.PatientRequest;
import br.com.cleonildo.dto.PatientResponse;
import br.com.cleonildo.services.PatientService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patients")
public class PatientController {
    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        return ResponseEntity.ok().body(this.service.findAllPatient());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) {
        return ResponseEntity.ok().body(this.service.findById(new ObjectId(id)));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<PatientResponse> getPatientByFirstAndLastName(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        return ResponseEntity.ok().body(this.service.findByFirstNameAndLastName(firstName, lastName));
    }

    @PostMapping
    public ResponseEntity<Void> savePatient(@RequestBody PatientRequest request) {
        PatientResponse response = this.service.savePatient(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable String id, @RequestBody PatientRequest request) {
        var response = this.service.updatePatient(new ObjectId(id), request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        this.service.deletePatientById(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
}
