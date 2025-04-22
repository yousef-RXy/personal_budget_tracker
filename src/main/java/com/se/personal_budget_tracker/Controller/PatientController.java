package com.se.personal_budget_tracker.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.personal_budget_tracker.Service.PatientService;
import com.se.personal_budget_tracker.model.PatientModel;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientModel> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public void addPatient(@RequestBody PatientModel patient) {
        patientService.addPatient(patient);
    }

    @DeleteMapping("/{id}")
    public boolean deletePatient(@PathVariable Long id) {
        return patientService.deletePatient(id);
    }

    @PutMapping("/{id}")
    public boolean updatePatient(@PathVariable Long id, @RequestBody PatientModel updatedPatient) {
        return patientService.updatePatient(id, updatedPatient);
    }
}
