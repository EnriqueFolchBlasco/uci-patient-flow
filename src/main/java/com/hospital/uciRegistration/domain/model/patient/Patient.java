package com.hospital.uciRegistration.domain.model.patient;

import com.hospital.uciRegistration.application.dto.PatientRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private String id;
    private String firstName;
    private String lastName;
    private Address address;

    public static Patient register(PatientRegisterRequest request) {
        Assert.notNull(request, "Patient must not be null");

        if (request.getId() == null || request.getId().isBlank()) {
            throw new IllegalArgumentException("Patient must have at least one id");
        }

        if (request.getFirstName() == null || request.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Patient must have at least one first name");
        }

        return new Patient(
                request.getId(),
                request.getFirstName(),
                request.getLastName(),
                new Address(
                        request.getStreet(),
                        request.getCity(),
                        request.getZip(),
                        request.getState()
                )
        );
    }

}
