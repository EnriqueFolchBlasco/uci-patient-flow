package com.hospital.uciRegistration.application.usecase.ticketmanagement;

import com.hospital.uciRegistration.application.usecase.patient.RegisterPatientUseCase;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GetTicketByPatientIdUseCaseTest {

    private TicketRepository ticketRepository;
    private PatientRepository patientRepository;

    @InjectMocks
    private RegisterPatientUseCase registerPatientUseCase;

    @Test
    void should_obtainTicket_byPatientId(){



    }

}