package com.hospital.uciRegistration.application.usecase.patient;

import com.hospital.uciRegistration.application.dto.PatientRegisterRequest;
import com.hospital.uciRegistration.common.exception.PatientAlreadyExistsException;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RegisterPatientUseCaseTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RegisterPatientUseCase registerPatientUseCase;

    PatientRegisterRequest patientRequest = new PatientRegisterRequest(
            "1234567E",
            "Jose",
            "Alore",
            "NA",
            "NA",
            "NA",
            "NA"
    );

    @Test
    void should_request_when_patientIsValid() {
        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);

        registerPatientUseCase.execute(patientRequest);
        verify(patientRepository, times(1)).save(patientCaptor.capture());

        Patient capturedPatient = patientCaptor.getValue();
        assertEquals("1234567E", capturedPatient.getId());
        assertEquals("Jose", capturedPatient.getFirstName());
    }

    @Test
    void should_throwException_when_userAlreadyExists () {
        when(patientRepository.existsById(patientRequest.getId())).thenReturn(true);
        assertThrows(PatientAlreadyExistsException.class, () -> registerPatientUseCase.execute(patientRequest) );
    }

    @ParameterizedTest
    @MethodSource("invalidRequests")
    void should_throwException_forInvalidRequest(PatientRegisterRequest invalid) {
        assertThrows(IllegalArgumentException.class, () -> registerPatientUseCase.execute(invalid));
    }

    static Stream<PatientRegisterRequest> invalidRequests() {
        PatientRegisterRequest base = new PatientRegisterRequest("1234567E","Jose","Alore","NA","NA","NA","NA");
        return Stream.of(
                new PatientRegisterRequest(base.getId(), null, base.getLastName(), base.getStreet(), base.getCity(), base.getZip(), base.getState()),
                new PatientRegisterRequest(base.getId(), "", base.getLastName(), base.getStreet(), base.getCity(), base.getZip(), base.getState()),
                new PatientRegisterRequest(base.getId(), base.getFirstName(), null, base.getStreet(), base.getCity(), base.getZip(), base.getState()),
                new PatientRegisterRequest(base.getId(), base.getFirstName(), "", base.getStreet(), base.getCity(), base.getZip(), base.getState())
        );
    }
}