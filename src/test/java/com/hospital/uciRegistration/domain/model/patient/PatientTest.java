package com.hospital.uciRegistration.domain.model.patient;

import com.hospital.uciRegistration.application.dto.PatientRegisterRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PatientTest {

    PatientRegisterRequest request = new PatientRegisterRequest(null, null,null,"1L","2L","234523","Pisos picados");

    @Test
    public void should_throwException_when_patientIsNull() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Patient.register(null));
        assertEquals("Patient must not be null", exception.getMessage());
    }

    @Test
    void should_registerPatient_when_allFieldsAreValid() {
        request.setId("1234");
        request.setFirstName("Maria");
        request.setLastName("Lopez");
        var patient = Patient.register(request);
        assertEquals("Maria", patient.getFirstName());
        assertEquals("Lopez", patient.getLastName());
        assertEquals("1234", patient.getId());
    }

    @Test
    public void should_throwException_when_patientDoesNotContainAtleastAName_isNull() {
        request.setId("1234");
        var exception = assertThrows(IllegalArgumentException.class, () -> Patient.register(request));
        assertEquals("Patient must have at least one first name", exception.getMessage());
    }

    @Test
    public void should_throwException_when_patientDoesNotContainAtleastAName_isBlank() {
        request.setId("1234");
        request.setFirstName("");
        var exception = assertThrows(IllegalArgumentException.class, () -> Patient.register(request));
        assertEquals("Patient must have at least one first name", exception.getMessage());
    }

    @Test
    public void should_throwException_when_patientDoesNotContainId_isNull() {
        request.setFirstName("Lopez");
        var exception = assertThrows(IllegalArgumentException.class, () -> Patient.register(request));
        assertEquals("Patient must have at least one id", exception.getMessage());
    }

    @Test
    public void should_throwException_when_patientDoesNotContainId_isBlank() {
        request.setFirstName("Lopez");
        request.setId("");
        var exception = assertThrows(IllegalArgumentException.class, () -> Patient.register(request));
        assertEquals("Patient must have at least one id", exception.getMessage());
    }



}