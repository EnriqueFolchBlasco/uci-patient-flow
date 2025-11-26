package com.hospital.uciRegistration.interfaces.rest;

import com.hospital.uciRegistration.application.dto.PatientTicketRequest;
import com.hospital.uciRegistration.application.dto.TicketCreateRequest;
import com.hospital.uciRegistration.application.dto.TicketDTO;
import com.hospital.uciRegistration.application.usecase.patient.RegisterPatientUseCase;
import com.hospital.uciRegistration.application.usecase.queue.AttendNextTicketUseCase;
import com.hospital.uciRegistration.application.usecase.ticketmanagement.ChangeTicketStatusUseCase;
import com.hospital.uciRegistration.application.usecase.ticketmanagement.CreateTicketUseCase;
import com.hospital.uciRegistration.application.usecase.ticketmanagement.GetTicketByPatientIdUseCase;
import com.hospital.uciRegistration.domain.model.patient.Address;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private RegisterPatientUseCase registerPatientUseCase;

    @Mock
    private CreateTicketUseCase createTicketUseCase;

    @Mock
    private GetTicketByPatientIdUseCase getTicketByPatientIdUseCase;

    @Mock
    private AttendNextTicketUseCase attendNextTicketUseCase;

    @Mock
    private ChangeTicketStatusUseCase changeTicketStatusUseCase;

    private Patient patient;
    private TriageInfo triageInfo;
    private ColorCode colorCode;

    @BeforeEach
    void setUp() {
        patient = new Patient("123", "John", "Doe", new Address());
        triageInfo = new TriageInfo(false, false, false, false, false);
        colorCode = new ColorCode("Red", "Wolf");
    }

    @Test
    void registerPatientAndCreateTicket_shouldReturnCreatedTicketDTO() {
        PatientTicketRequest request = new PatientTicketRequest(
                "123", "John", "Doe", "Street", "City", "Zip", "State",
                false, false, false, false, false
        );

        Ticket ticket = Ticket.createTicket(patient, triageInfo, colorCode);

        TicketDTO ticketDTO = new TicketDTO(
                ticket.getId().toString(),
                ticket.getPatient().getFirstName() + " " + ticket.getPatient().getLastName(),
                ticket.getPatient().getId(),
                ticket.getColorCode().getColor(),
                ticket.getStatus().name()
        );

        when(registerPatientUseCase.execute(any())).thenReturn(patient);
        when(createTicketUseCase.execute(any(Patient.class), any(TicketCreateRequest.class))).thenReturn(ticketDTO);


        ResponseEntity<TicketDTO> response = ticketController.registerPatientAndCreateTicket(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ticketDTO, response.getBody());

        verify(registerPatientUseCase, times(1)).execute(any());
        verify(createTicketUseCase, times(1)).execute(eq(patient), any());

    }


    @Test
    void getPatientTicket_shouldReturnTicket() {
        Ticket ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        when(getTicketByPatientIdUseCase.execute("123")).thenReturn(ticket);

        Ticket response = ticketController.getPatientTicket("123");

        assertEquals(ticket, response);
        verify(getTicketByPatientIdUseCase, times(1)).execute("123");
    }

    @Test
    void callNextTicket_shouldReturnNextTicket() {
        Ticket ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        when(attendNextTicketUseCase.execute()).thenReturn(ticket);

        Ticket response = ticketController.callNextTicket();

        assertEquals(ticket, response);
        verify(attendNextTicketUseCase, times(1)).execute();
    }

    @Test
    void changeStatus_shouldCallChangeTicketStatusUseCase() {
        String ticketId = UUID.randomUUID().toString();
        String status = "IN_PROGRESS";

        ticketController.changeStatus(ticketId, status);

        verify(changeTicketStatusUseCase, times(1)).execute(ticketId, status);
    }
}
