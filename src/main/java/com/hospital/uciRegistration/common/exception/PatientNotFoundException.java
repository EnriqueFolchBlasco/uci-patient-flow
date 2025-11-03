package com.hospital.uciRegistration.common.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String id) {
        super("Theres no ticket with the patient " + id);
    }
}
