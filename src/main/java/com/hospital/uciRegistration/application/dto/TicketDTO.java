package com.hospital.uciRegistration.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private String id;
    private String patientFullName;
    private String patientId;
    private String colorCode;
    private String status;
}
