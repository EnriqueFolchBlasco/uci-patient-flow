package com.hospital.uciRegistration.application.services;

import com.hospital.uciRegistration.common.exception.NoTicketsWaitingException;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketQueueService {

    private final TicketRepository ticketRepository;

    public TicketQueueService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket attendNextTicket() {
        List<Ticket> queue = ticketRepository.findWaitingTicketsOrdered();
        if (queue.isEmpty()) {
            throw new NoTicketsWaitingException("No tickets waiting");
        }

        Ticket next = queue.get(0);
        next.markInProgress();
        return ticketRepository.save(next);
    }
}
