package com.hospital.uciRegistration.domain.repository;

import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import org.jmolecules.ddd.annotation.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository {

    Ticket save(Ticket ticket);

    Ticket findById(UUID id);

    List<Ticket> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Ticket findByPatient(Patient patient);

    List<Ticket> findWaitingTicketsOrdered();

    Optional<Ticket> findFirstByStatus(TicketStatus status);

    List<Ticket> findLastCalledTickets(int limit);

}