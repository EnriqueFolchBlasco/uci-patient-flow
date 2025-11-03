package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.application.dto.TicketCreateRequest;
import com.hospital.uciRegistration.application.dto.TicketDTO;
import com.hospital.uciRegistration.application.services.ColorNounService;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TriageInfo;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateTicketUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateTicketUseCase.class);
    private final ColorNounService colorNounService;
    private final TicketRepository ticketRepository;

    public CreateTicketUseCase(TicketRepository ticketRepository, ColorNounService colorNounService) {
        this.ticketRepository = ticketRepository;
        this.colorNounService = colorNounService;
    }

    public TicketDTO execute(Patient patient, TicketCreateRequest ticketRequest) {
        logger.info("Creating ticket for patient: {} {}", patient.getFirstName(), patient.getLastName());

        TriageInfo triageInfo = new TriageInfo(
                ticketRequest.isRiskOfFall(),
                ticketRequest.isBreathingDifficulty(),
                ticketRequest.isSeverePain(),
                ticketRequest.isBleeding(),
                ticketRequest.isUnconscious()
        );

        ColorCode colorCode = colorNounService.generateUniqueColorCode();

        Ticket ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        Ticket savedTicket = ticketRepository.save(ticket);

        logger.info("Ticket created with ID: {}", ticket.getId());

        return new TicketDTO(
                ticket.getId().toString(),
                patient.getFirstName() + " " + patient.getLastName(),
                patient.getId(),
                ticket.getColorCode().getColor_code(),
                ticket.getStatus().name()
        );
    }
}
