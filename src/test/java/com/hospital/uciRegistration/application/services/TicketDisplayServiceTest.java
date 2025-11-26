package com.hospital.uciRegistration.application.services;

import com.hospital.uciRegistration.application.dto.DisplayTicket;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.*;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketDisplayServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    private TicketDisplayService ticketDisplayService;

    @BeforeEach
    void setUp() {
        ticketDisplayService = new TicketDisplayService(ticketRepository);
    }

    private Ticket buildTicketWithCreatedAt(LocalDateTime createdAt) {
        Patient patient = new Patient();
        patient.setId("1");

        return new Ticket(
                UUID.randomUUID(),
                patient,
                PriorityType.URGENT,
                new ColorCode("RED", "WOLF"),
                new TriageInfo(true, true, true, true, true),
                createdAt,
                TicketStatus.WAITING
        );
    }

    @Test
    void should_display_justAppeared() {
        Ticket ticket = buildTicketWithCreatedAt(LocalDateTime.now());

        when(ticketRepository.findLastCalledTickets(5))
                .thenReturn(List.of(ticket));

        List<DisplayTicket> result = ticketDisplayService.getQueueForScreen();

        assertEquals(1, result.size());
        assertEquals("RED-WOLF", result.get(0).colorCode());
        assertEquals("Just appeared", result.get(0).timeAgo());
    }

    @Test
    void should_display_twoMinutesAgo() {
        Ticket ticket = buildTicketWithCreatedAt(LocalDateTime.now().minusMinutes(2));

        when(ticketRepository.findLastCalledTickets(5))
                .thenReturn(List.of(ticket));

        List<DisplayTicket> result = ticketDisplayService.getQueueForScreen();

        assertEquals(1, result.size());
        assertEquals("RED-WOLF", result.get(0).colorCode());
        assertEquals("Appeared 2m ago", result.get(0).timeAgo());
    }
}