package com.hospital.uciRegistration.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PATIENTS")
public class PatientEntity {

    @Id
    @Column(length = 20)
    private String id;

    private String firstName;
    private String lastName;

    @Embedded
    private AddressEmbeddable address;

}
