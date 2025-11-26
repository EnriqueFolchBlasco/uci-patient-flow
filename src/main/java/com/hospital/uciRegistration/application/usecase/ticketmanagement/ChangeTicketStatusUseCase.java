package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.common.exception.PatientNotFoundException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChangeTicketStatusUseCase {

    private final Logger logger = LoggerFactory.getLogger(ChangeTicketStatusUseCase.class);
    private final TicketRepository ticketRepository;
    private final PatientRepository patientRepository;

    public ChangeTicketStatusUseCase(TicketRepository ticketRepository, PatientRepository patientRepository) {
        this.ticketRepository = ticketRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public void execute(String id, String newTicketStatusString) {

        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException(id);
        }

        TicketStatus newTicketStatus;

        try {
            newTicketStatus = TicketStatus.valueOf(newTicketStatusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ticket status: " + newTicketStatusString);
        }

        Patient patient = patientRepository.findById(id);
        Ticket newTicket = ticketRepository.findByPatient(patient);

        switch (newTicketStatus) {
            case IN_PROGRESS -> newTicket.markInProgress();
            case COMPLETED -> newTicket.markAsComplete();
            case CANCELLED -> newTicket.markAsCancelled();
        }

        logger.info("Changed Ticket Status to {}", newTicketStatus);
        ticketRepository.save(newTicket);
    }
}