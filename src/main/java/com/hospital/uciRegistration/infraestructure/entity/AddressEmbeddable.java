package com.hospital.uciRegistration.infraestructure.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressEmbeddable {

    private String street;
    private String city;
    private String zip;
    private String state;

}
