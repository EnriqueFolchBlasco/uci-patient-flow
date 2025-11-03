package com.hospital.uciRegistration.application.services;

import com.hospital.uciRegistration.application.dto.DisplayTicket;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketDisplayService {

    private final TicketRepository ticketRepository;
    private static final int MAX_DISPLAY = 5;

    public TicketDisplayService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<DisplayTicket> getQueueForScreen() {
        List<Ticket> lastCalled = ticketRepository.findLastCalledTickets(MAX_DISPLAY);

        List<DisplayTicket> display = new ArrayList<>();
        for (Ticket ticket : lastCalled) {
            long minutesAgo = java.time.Duration.between(ticket.getCreatedAt(), java.time.LocalDateTime.now()).toMinutes();
            String timeText = minutesAgo == 0 ? "Just appeared" : "Appeared " + minutesAgo + "m ago";

            display.add(new DisplayTicket(
                    ticket.getColorCode().getColor_code(),
                    timeText
            ));
        }

        return display;
    }
}