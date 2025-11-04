package com.hospital.uciRegistration.infraestructure.mappers;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface PatientMapper {

    PatientEntity toPatientEntity(Patient patient);

    Patient fromPatientEntity(PatientEntity entity);
}
