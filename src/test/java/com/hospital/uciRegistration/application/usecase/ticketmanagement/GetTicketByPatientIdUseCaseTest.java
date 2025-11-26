package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.common.exception.PatientNotFoundException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TriageInfo;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetTicketByPatientIdUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private GetTicketByPatientIdUseCase getTicketByPatientIdUseCase;


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

    @Test
    void should_obtainTicket_byPatientId(){

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(patientRepository.findById(patientId)).thenReturn(patient);
        when(ticketRepository.findByPatient(patient)).thenReturn(ticket);

        Ticket result = getTicketByPatientIdUseCase.execute(patientId);
        assertNotNull(result);
        assertEquals(patient, result.getPatient());
    }

    @Test
    void should_throwExceptionPatientNotFound_when_patientExistsByIdFalse(){
        when(patientRepository.existsById(patientId)).thenReturn(false);

        assertThrows(PatientNotFoundException.class, () ->
                getTicketByPatientIdUseCase.execute(patientId)
        );
    }
}