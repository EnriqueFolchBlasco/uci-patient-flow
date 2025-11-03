package com.hospital.uciRegistration.infraestructure.repository;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import com.hospital.uciRegistration.domain.repository.TicketRepository;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import com.hospital.uciRegistration.infraestructure.entity.TicketEntity;
import com.hospital.uciRegistration.infraestructure.mappers.PatientMapper;
import com.hospital.uciRegistration.infraestructure.mappers.TicketMapper;
import com.hospital.uciRegistration.infraestructure.repository.jpa.TicketJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TicketEntityRepository implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;
    private final TicketMapper ticketMapper;
    private final PatientMapper patientMapper;

    public TicketEntityRepository(
            TicketJpaRepository ticketJpaRepository,
            TicketMapper ticketMapper,
            PatientMapper patientMapper
    ) {
        this.ticketJpaRepository = ticketJpaRepository;
        this.ticketMapper = ticketMapper;
        this.patientMapper = patientMapper;
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity entity = ticketMapper.toEntity(ticket);
        TicketEntity saved = ticketJpaRepository.save(entity);
        return ticketMapper.fromEntity(saved);
    }

    @Override
    public Ticket findById(UUID id) {
        Optional<TicketEntity> optional = ticketJpaRepository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Ticket not found");
        }

        return ticketMapper.fromEntity(optional.get());
    }

    @Override
    public List<Ticket> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return ticketJpaRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(ticketMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Ticket findByPatient(Patient patient) {
        PatientEntity patientEntity = patientMapper.toPatientEntity(patient);

        TicketEntity entity = ticketJpaRepository.findByPatient(patientEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No ticket found for patient: " + patient.getId()));

        return ticketMapper.fromEntity(entity);
    }

    @Override
    public List<Ticket> findWaitingTicketsOrdered() {
        return ticketJpaRepository.findWaitingOrderedByPriorityAndTime(TicketStatus.WAITING)
                .stream()
                .map(ticketMapper::fromEntity)
                .toList();
    }

    @Override
    public Optional<Ticket> findFirstByStatus(TicketStatus status) {
        return ticketJpaRepository.findAllByStatusOrderByPriorityDescCreatedAtAsc(status)
                .stream()
                .findFirst()
                .map(ticketMapper::fromEntity);
    }

    @Override
    public List<Ticket> findLastCalledTickets(int limit) {
        List<TicketStatus> calledStatuses = List.of(TicketStatus.IN_PROGRESS);


        List<TicketEntity> entities = ticketJpaRepository.findTop5ByStatusInOrderByCreatedAtDesc(calledStatuses);

        return entities.stream()
                .map(ticketMapper::fromEntity)
                .toList();
    }
}