package com.hospital.uciRegistration.application.usecase.queue;

import com.hospital.uciRegistration.application.dto.DisplayTicket;
import com.hospital.uciRegistration.application.services.TicketDisplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTicketQueueUseCase {

    private final Logger logger = LoggerFactory.getLogger(GetTicketQueueUseCase.class);

    private final TicketDisplayService ticketDisplayService;

    public GetTicketQueueUseCase(TicketDisplayService ticketDisplayService) {
        this.ticketDisplayService = ticketDisplayService;
    }

    public List<DisplayTicket> execute() {
        logger.info("Ticket list has been requested");
        return ticketDisplayService.getQueueForScreen();
    }
}
