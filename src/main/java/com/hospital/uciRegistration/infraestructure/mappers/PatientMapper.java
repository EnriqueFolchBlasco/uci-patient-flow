package com.hospital.uciRegistration.infraestructure.mappers;

import com.hospital.uciRegistration.domain.model.patient.Address;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.infraestructure.entity.AddressEmbeddable;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    private final AddressMapper addressMapper;

    public PatientMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public PatientEntity toPatientEntity(Patient patient) {
        if (patient == null) {
            return null;
        }

        AddressEmbeddable addressEmbeddable = addressMapper.toAddressEmbeddable(patient.getAddress());

        return new PatientEntity(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                addressEmbeddable
        );
    }

    public Patient fromPatientEntity(PatientEntity entity) {
        if (entity == null) {
            return null;
        }

        Address address = addressMapper.fromAddressEmbeddable(entity.getAddress());

        Patient patient = new Patient();
        patient.setId(entity.getId());
        patient.setFirstName(entity.getFirstName());
        patient.setLastName(entity.getLastName());
        patient.setAddress(address);
        return patient;
    }
}
