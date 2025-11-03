package com.hospital.uciRegistration.domain.repository;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import java.util.List;

public interface PatientRepository {
    Patient save(Patient patient);
    boolean existsById(String id);
    Patient findById(String id);
    List<Patient> findAll();
}