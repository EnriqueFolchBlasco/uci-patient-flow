package com.hospital.uciRegistration.infraestructure.repository;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import com.hospital.uciRegistration.infraestructure.entity.TicketEntity;
import com.hospital.uciRegistration.infraestructure.mappers.PatientMapper;
import com.hospital.uciRegistration.infraestructure.mappers.TicketMapper;
import com.hospital.uciRegistration.infraestructure.repository.jpa.TicketJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketEntityRepositoryTest {

    @Mock
    private TicketJpaRepository ticketJpaRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private TicketEntityRepository repo;

    @Test
    void should_saveTicket_andReturnMappedDomain() {
        Ticket ticket = new Ticket();
        TicketEntity entity = new TicketEntity();
        TicketEntity savedEntity = new TicketEntity();
        Ticket mappedBack = new Ticket();

        when(ticketMapper.toEntity(ticket)).thenReturn(entity);
        when(ticketJpaRepository.save(entity)).thenReturn(savedEntity);
        when(ticketMapper.fromEntity(savedEntity)).thenReturn(mappedBack);

        Ticket result = repo.save(ticket);

        assertEquals(mappedBack, result);
        verify(ticketMapper).toEntity(ticket);
        verify(ticketJpaRepository).save(entity);
        verify(ticketMapper).fromEntity(savedEntity);
    }

    @Test
    void should_findById_andReturnTicket() {
        UUID id = UUID.randomUUID();
        TicketEntity entity = new TicketEntity();
        Ticket ticket = new Ticket();

        when(ticketJpaRepository.findById(id)).thenReturn(Optional.of(entity));
        when(ticketMapper.fromEntity(entity)).thenReturn(ticket);

        Ticket result = repo.findById(id);

        assertEquals(ticket, result);
    }

    @Test
    void should_throw_when_findById_notFound() {
        UUID id = UUID.randomUUID();
        when(ticketJpaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> repo.findById(id));
    }

    @Test
    void should_findByPatient_andReturnTicket() {
        Patient patient = new Patient();
        PatientEntity patientEntity = new PatientEntity();
        TicketEntity ticketEntity = new TicketEntity();
        Ticket ticket = new Ticket();

        when(patientMapper.toPatientEntity(patient)).thenReturn(patientEntity);
        when(ticketJpaRepository.findByPatient(patientEntity)).thenReturn(Optional.of(ticketEntity));
        when(ticketMapper.fromEntity(ticketEntity)).thenReturn(ticket);

        Ticket result = repo.findByPatient(patient);

        assertEquals(ticket, result);
    }

    @Test
    void should_throw_when_findByPatient_notFound() {
        Patient patient = new Patient();
        PatientEntity patientEntity = new PatientEntity();

        when(patientMapper.toPatientEntity(patient)).thenReturn(patientEntity);
        when(ticketJpaRepository.findByPatient(patientEntity)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> repo.findByPatient(patient));
    }

    @Test
    void should_findWaitingTicketsOrdered_andMapToDomainList() {
        TicketEntity e1 = new TicketEntity();
        TicketEntity e2 = new TicketEntity();

        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();

        when(ticketJpaRepository.findWaitingOrderedByPriorityAndTime(TicketStatus.WAITING)).thenReturn(List.of(e1, e2));
        when(ticketMapper.fromEntity(e1)).thenReturn(t1);
        when(ticketMapper.fromEntity(e2)).thenReturn(t2);

        List<Ticket> result = repo.findWaitingTicketsOrdered();

        assertEquals(2, result.size());
        assertEquals(t1, result.get(0));
        assertEquals(t2, result.get(1));
    }

    @Test
    void should_findByCreatedAtBetween_andReturnMappedTickets() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        TicketEntity e1 = new TicketEntity();
        TicketEntity e2 = new TicketEntity();
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();

        when(ticketJpaRepository.findByCreatedAtBetween(start, end))
                .thenReturn(List.of(e1, e2));
        when(ticketMapper.fromEntity(e1)).thenReturn(t1);
        when(ticketMapper.fromEntity(e2)).thenReturn(t2);

        List<Ticket> result = repo.findByCreatedAtBetween(start, end);

        assertEquals(2, result.size());
        assertEquals(t1, result.get(0));
        assertEquals(t2, result.get(1));
    }

    @Test
    void should_findFirstByStatus_andReturnOptionalTicket() {
        TicketEntity entity = new TicketEntity();
        Ticket ticket = new Ticket();

        when(ticketJpaRepository.findAllByStatusOrderByPriorityDescCreatedAtAsc(TicketStatus.WAITING))
                .thenReturn(List.of(entity));
        when(ticketMapper.fromEntity(entity)).thenReturn(ticket);

        Optional<Ticket> result = repo.findFirstByStatus(TicketStatus.WAITING);

        assertTrue(result.isPresent());
        assertEquals(ticket, result.get());
    }

    @Test
    void should_returnEmptyOptional_when_findFirstByStatus_noTickets() {
        when(ticketJpaRepository.findAllByStatusOrderByPriorityDescCreatedAtAsc(TicketStatus.WAITING))
                .thenReturn(List.of());

        Optional<Ticket> result = repo.findFirstByStatus(TicketStatus.WAITING);

        assertTrue(result.isEmpty());
    }

    @Test
    void should_findLastCalledTickets_andReturnMappedList() {
        TicketEntity e1 = new TicketEntity();
        TicketEntity e2 = new TicketEntity();
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();

        when(ticketJpaRepository.findTop5ByStatusInOrderByCreatedAtDesc(List.of(TicketStatus.IN_PROGRESS)))
                .thenReturn(List.of(e1, e2));
        when(ticketMapper.fromEntity(e1)).thenReturn(t1);
        when(ticketMapper.fromEntity(e2)).thenReturn(t2);

        List<Ticket> result = repo.findLastCalledTickets(5);

        assertEquals(2, result.size());
        assertEquals(t1, result.get(0));
        assertEquals(t2, result.get(1));
    }

}
