package com.hospital.uciRegistration.application.services;

import com.hospital.uciRegistration.common.exception.NoTicketsWaitingException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.*;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketQueueServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    private TicketQueueService ticketQueueService;

    @BeforeEach
    void setUp() {
        ticketQueueService = new TicketQueueService(ticketRepository);
    }

    @Test
    void should_markNextTicketInProgress(){

        List<Ticket> list = new ArrayList<>();

        Patient patient = new Patient();
        patient.setId("1");

        Ticket ticket = new Ticket(
                UUID.randomUUID(),
                patient,
                PriorityType.URGENT,
                new ColorCode("RED", "WOLF"),
                new TriageInfo(true, true, true, true, true),
                LocalDateTime.now(),
                TicketStatus.WAITING
        );

        list.add(ticket);

        when(ticketRepository.findWaitingTicketsOrdered()).thenReturn(list);

        ticketQueueService.attendNextTicket();

        assertEquals(TicketStatus.IN_PROGRESS, list.get(0).getStatus());
    }

    @Test
    void should_throwException_noTicketsWaiting(){

        List<Ticket> list = new ArrayList<>();

        when(ticketRepository.findWaitingTicketsOrdered()).thenReturn(list);

        assertThrows(NoTicketsWaitingException.class, () -> {
            ticketQueueService.attendNextTicket();
        });

    }
}