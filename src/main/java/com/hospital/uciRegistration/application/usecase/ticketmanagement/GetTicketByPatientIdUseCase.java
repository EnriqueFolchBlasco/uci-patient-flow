package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.common.exception.PatientNotFoundException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetTicketByPatientIdUseCase {

    private final Logger logger = LoggerFactory.getLogger(GetTicketByPatientIdUseCase.class);
    private final TicketRepository ticketRepository;
    private final PatientRepository patientRepository;

    public GetTicketByPatientIdUseCase(TicketRepository ticketRepository, PatientRepository patientRepository) {
        this.ticketRepository = ticketRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Ticket execute(String id) {

        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException(id);
        }

        Patient patient = patientRepository.findById(id);
        logger.info("Patient {} info has been requested",patient.getFirstName());
        return ticketRepository.findByPatient(patient);
    }
}