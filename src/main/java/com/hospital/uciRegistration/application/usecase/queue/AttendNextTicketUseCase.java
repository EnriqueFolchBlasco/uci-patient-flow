package com.hospital.uciRegistration.application.usecase.queue;

import com.hospital.uciRegistration.common.exception.NoTicketsWaitingException;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendNextTicketUseCase {

    private final Logger logger = LoggerFactory.getLogger(AttendNextTicketUseCase.class);
    private final TicketRepository ticketRepository;

    public AttendNextTicketUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket execute() {
        List<Ticket> queue = ticketRepository.findWaitingTicketsOrdered();

        if (queue.isEmpty()) {
            throw new NoTicketsWaitingException("No tickets waiting");
        }

        Ticket next = queue.get(0);
        next.markInProgress();

        logger.info("Next ticked in line has been put in_progress");
        return ticketRepository.save(next);
    }
}