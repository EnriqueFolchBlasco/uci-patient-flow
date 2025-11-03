package com.hospital.uciRegistration.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientTicketRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String state;

    private boolean riskOfFall;
    private boolean breathingDifficulty;
    private boolean severePain;
    private boolean bleeding;
    private boolean unconscious;
}
