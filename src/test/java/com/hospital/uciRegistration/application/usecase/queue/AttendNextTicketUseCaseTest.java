package com.hospital.uciRegistration.application.usecase.queue;

import com.hospital.uciRegistration.common.exception.NoTicketsWaitingException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import com.hospital.uciRegistration.domain.model.ticket.TriageInfo;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttendNextTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private AttendNextTicketUseCase attendNextTicketUseCase;

    private List<Ticket> ticketList;

    @BeforeEach
    void setUp(){

        Patient patient = new Patient();
        patient.setId("123");
        patient.setFirstName("John");

        Ticket ticket = Ticket.createTicket(
                patient,
                new TriageInfo(true, true, true, true, true),
                new ColorCode("RED", "WOLF")
        );

        ticketList = new ArrayList<>();
        ticketList.add(ticket);
    }

    @Test
    void should_attendNextTicket(){

        when(ticketRepository.findWaitingTicketsOrdered()).thenReturn(ticketList);
        List<Ticket> list = ticketRepository.findWaitingTicketsOrdered();
        Ticket firstTicket =  list.get(0);

        attendNextTicketUseCase.execute();
        assertEquals(TicketStatus.IN_PROGRESS, firstTicket.getStatus());

    }

    @Test
    void should_throwException_noTicketsInLine(){

        NoTicketsWaitingException ex = assertThrows(NoTicketsWaitingException.class, () -> {
            attendNextTicketUseCase.execute();
        });

        assertEquals("No tickets waiting", ex.getMessage());

    }

}