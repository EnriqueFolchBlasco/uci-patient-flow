package com.hospital.uciRegistration.domain.model.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.ValueObject;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ValueObject
public class Address {

    private String street;
    private String city;
    private String zip;
    private String state;

}
