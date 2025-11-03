package com.hospital.uciRegistration.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegisterRequest {

    private String id;

    private String firstName;
    private String lastName;

    private String street;
    private String city;
    private String zip;
    private String state;
}
