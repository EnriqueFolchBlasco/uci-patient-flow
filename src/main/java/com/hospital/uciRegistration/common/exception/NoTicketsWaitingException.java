package com.hospital.uciRegistration.common.exception;

public class NoTicketsWaitingException extends RuntimeException {
    public NoTicketsWaitingException(String message) {
        super(message);
    }
}
