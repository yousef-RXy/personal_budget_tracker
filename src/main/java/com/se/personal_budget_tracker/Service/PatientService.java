package com.se.personal_budget_tracker.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.se.personal_budget_tracker.Repository.PatientRepository;
import com.se.personal_budget_tracker.model.PatientModel;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientModel> getAllPatients() {
        return patientRepository.findAll();
    }

    public void addPatient(PatientModel patient) {
        patientRepository.save(patient);
    }

    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updatePatient(Long id, PatientModel updatedPatient) {
        Optional<PatientModel> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            PatientModel patient = existingPatient.get();
            patient.setName(updatedPatient.getName());
            patient.setAge(updatedPatient.getAge());
            patient.setDiagnosis(updatedPatient.getDiagnosis());
            patient.setGender(updatedPatient.getGender());

            patientRepository.save(patient);
            return true;
        }
        return false;
    }

}