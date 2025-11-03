package com.hospital.uciRegistration.interfaces.config;

import com.hospital.uciRegistration.common.exception.NoAvailableColorCodeException;
import com.hospital.uciRegistration.common.exception.NoTicketsWaitingException;
import com.hospital.uciRegistration.common.exception.PatientAlreadyExistsException;
import com.hospital.uciRegistration.common.exception.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.hospital.uciRegistration.interfaces.rest")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<Object> handleException(PatientAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoAvailableColorCodeException.class)
    public ResponseEntity<Object> handleException(NoAvailableColorCodeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(NoTicketsWaitingException.class)
    public ResponseEntity<Object> handleException(NoTicketsWaitingException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Object> handlePatientNotFound(PatientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
