package com.hospital.uciRegistration.application.usecase.patient;

import com.hospital.uciRegistration.application.dto.PatientRegisterRequest;
import com.hospital.uciRegistration.common.exception.PatientAlreadyExistsException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RegisterPatientUseCase {

    private final Logger logger = LoggerFactory.getLogger(RegisterPatientUseCase.class);

    private final PatientRepository patientRepository;

    public RegisterPatientUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient execute(PatientRegisterRequest request) {

        if (request.getFirstName() == null || request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }

        if (request.getLastName() == null || request.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }

        String patientName = request.getFirstName() + " " + request.getLastName();
        Patient patient = Patient.register(request);

        if (patientRepository.existsById(patient.getId())) {
            throw new PatientAlreadyExistsException("Patient with ID " + patient.getId() + " already exists");
        }

        patientRepository.save(patient);

        logger.info("Patient: {} has been registered", patientName);
        return patient;
    }
}