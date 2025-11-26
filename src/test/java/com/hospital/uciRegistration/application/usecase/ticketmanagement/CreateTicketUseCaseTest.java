package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.application.dto.TicketCreateRequest;
import com.hospital.uciRegistration.application.dto.TicketDTO;
import com.hospital.uciRegistration.application.services.ColorNounService;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ColorNounService colorNounService;

    @InjectMocks
    private CreateTicketUseCase createTicketUseCase;

    @Test
    void should_createTicketSuccessfully() {
        Patient patient = new Patient("123", "John", "Doe", null);
        TicketCreateRequest request = new TicketCreateRequest(true, false, true, false, false);

        ColorCode fakeColorCode = new ColorCode("Red", "Tiger");
        when(colorNounService.generateUniqueColorCode()).thenReturn(fakeColorCode);

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        when(ticketRepository.save(ticketCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        TicketDTO result = createTicketUseCase.execute(patient, request);

        verify(colorNounService, times(1)).generateUniqueColorCode();
        verify(ticketRepository, times(1)).save(any(Ticket.class));

        assertEquals("123", result.getPatientId());
        assertEquals("Red-Tiger", result.getColorCode());
    }
}