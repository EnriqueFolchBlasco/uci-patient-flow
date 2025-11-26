package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.common.exception.PatientNotFoundException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import com.hospital.uciRegistration.domain.model.ticket.TriageInfo;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChangeTicketStatusUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private ChangeTicketStatusUseCase changeTicketStatusUseCase;

    private Patient patient;
    private Ticket ticket;
    private String patientId;

    @BeforeEach
    void setUp() {
        patientId = "123";

        patient = new Patient();
        patient.setId(patientId);
        patient.setFirstName("John");

        ticket = Ticket.createTicket(
                patient,
                new TriageInfo(true,true,true,true,true),
                new ColorCode("RED", "WOLF")
        );
    }

    private void mockPatientAndTicket() {
        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.findById(patientId)).thenReturn(patient);
        when(ticketRepository.findByPatient(patient)).thenReturn(ticket);
    }

    @Test
    void should_changeStatus_toCompleted(){
        ticket.markInProgress();
        mockPatientAndTicket();

        changeTicketStatusUseCase.execute(patientId, "COMPLETED");
        assertEquals(TicketStatus.COMPLETED, ticket.getStatus());
    }

    @Test
    void should_changeStatus_toInProgress(){
        mockPatientAndTicket();

        changeTicketStatusUseCase.execute(patientId, "IN_PROGRESS");
        assertEquals(TicketStatus.IN_PROGRESS, ticket.getStatus());
    }

    @Test
    void should_changeStatus_toCancelled(){
        mockPatientAndTicket();

        changeTicketStatusUseCase.execute(patientId, "CANCELLED");
        assertEquals(TicketStatus.CANCELLED, ticket.getStatus());
    }

    @Test
    void should_not_changeStatus_whenTicketAlreadyCancelled() {
        ticket.markAsCancelled();

        mockPatientAndTicket();

        assertThrows(IllegalStateException.class,
                () -> changeTicketStatusUseCase.execute(patientId, "COMPLETED")
        );
    }


    @Test
    void should_not_changeStatus_orderInvalid(){
        //FROM WAITING TO COMPLETED => WRONG, HAS TO PASS THROUGH IN_PROGRESS
        mockPatientAndTicket();

        assertThrows(IllegalStateException.class,
                () -> changeTicketStatusUseCase.execute(patientId, "COMPLETED")
        );

    }

    @Test
    void should_throwException_patientNotFound(){
        when(patientRepository.existsById(patientId)).thenReturn(false);

        PatientNotFoundException ex = assertThrows(PatientNotFoundException.class,
                () -> changeTicketStatusUseCase.execute(patientId, "COMPLETED")
        );
        assertEquals("Theres no ticket with the patient " + patient.getId(), ex.getMessage());
    }

    @Test
    void should_throwException_statusDoesntExist(){
        when(patientRepository.existsById(patientId)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> changeTicketStatusUseCase.execute(patientId, "WRONG")
        );
        assertEquals("Invalid ticket status: WRONG", ex.getMessage());

    }




}
