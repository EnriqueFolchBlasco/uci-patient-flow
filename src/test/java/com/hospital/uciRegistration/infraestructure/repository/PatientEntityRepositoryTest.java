package com.hospital.uciRegistration.infraestructure.repository;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import com.hospital.uciRegistration.infraestructure.mappers.PatientMapper;
import com.hospital.uciRegistration.infraestructure.repository.jpa.PatientJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientEntityRepositoryTest {

    @Mock
    private PatientJpaRepository patientJpaRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientEntityRepository repo;

    @Test
    void should_savePatient_andReturnMappedDomain() {
        Patient patient = new Patient();
        PatientEntity entity = new PatientEntity();
        PatientEntity savedEntity = new PatientEntity();
        Patient mappedPatient = new Patient();

        when(patientMapper.toPatientEntity(patient)).thenReturn(entity);
        when(patientJpaRepository.save(entity)).thenReturn(savedEntity);
        when(patientMapper.fromPatientEntity(savedEntity)).thenReturn(mappedPatient);

        Patient result = repo.save(patient);

        assertEquals(mappedPatient, result);
        verify(patientMapper).toPatientEntity(patient);
        verify(patientJpaRepository).save(entity);
        verify(patientMapper).fromPatientEntity(savedEntity);
    }

    @Test
    void should_returnTrue_when_existsById_isPresent() {
        when(patientJpaRepository.findIdById("123")).thenReturn(Optional.of("123"));

        boolean exists = repo.existsById("123");

        assertTrue(exists);
    }

    @Test
    void should_returnFalse_when_existsById_isEmpty() {
        when(patientJpaRepository.findIdById("404")).thenReturn(Optional.empty());

        boolean exists = repo.existsById("404");

        assertFalse(exists);
    }

    @Test
    void should_findById_andReturnPatient() {
        PatientEntity entity = new PatientEntity();
        Patient patient = new Patient();

        when(patientJpaRepository.findById("1")).thenReturn(Optional.of(entity));
        when(patientMapper.fromPatientEntity(entity)).thenReturn(patient);

        Patient result = repo.findById("1");

        assertEquals(patient, result);
    }

    @Test
    void should_throw_when_findById_notFound() {
        when(patientJpaRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> repo.findById("999"));
    }

    @Test
    void should_findAll_andMapToDomainList() {
        PatientEntity e1 = new PatientEntity();
        e1.setId("1");
        PatientEntity e2 = new PatientEntity();
        e2.setId("2");
        Patient p1 = new Patient();
        p1.setId("1");
        Patient p2 = new Patient();
        p2.setId("2");

        when(patientJpaRepository.findAll()).thenReturn(List.of(e1, e2));
        when(patientMapper.fromPatientEntity(e1)).thenReturn(p1);
        when(patientMapper.fromPatientEntity(e2)).thenReturn(p2);

        List<Patient> result = repo.findAll();

        assertTrue(result.stream().anyMatch(p -> p.getId().equals("1")));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals("2")));

        verify(patientJpaRepository).findAll();
        verify(patientMapper).fromPatientEntity(e1);
        verify(patientMapper).fromPatientEntity(e2);
    }
}