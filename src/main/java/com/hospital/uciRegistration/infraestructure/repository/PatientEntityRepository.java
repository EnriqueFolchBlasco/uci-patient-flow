package com.hospital.uciRegistration.infraestructure.repository;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import com.hospital.uciRegistration.infraestructure.mappers.PatientMapper;
import com.hospital.uciRegistration.infraestructure.repository.jpa.PatientJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientEntityRepository implements PatientRepository {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientMapper patientMapper;

    public PatientEntityRepository(PatientJpaRepository patientJpaRepository, PatientMapper patientMapper) {
        this.patientJpaRepository = patientJpaRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = patientMapper.toPatientEntity(patient);
        PatientEntity savedEntity = patientJpaRepository.save(entity);
        return patientMapper.fromPatientEntity(savedEntity);
    }

    @Override
    public boolean existsById(String id) {
        return patientJpaRepository.findIdById(id).isPresent();
    }

    @Override
    public Patient findById(String id) {
        return patientJpaRepository.findById(id)
                .map(patientMapper::fromPatientEntity)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    @Override
    public List<Patient> findAll() {
        return patientJpaRepository.findAll()
                .stream()
                .map(patientMapper::fromPatientEntity)
                .toList();
    }

}
