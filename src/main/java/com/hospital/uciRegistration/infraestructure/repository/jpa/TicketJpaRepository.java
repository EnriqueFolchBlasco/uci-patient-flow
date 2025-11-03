package com.hospital.uciRegistration.infraestructure.repository.jpa;

import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import com.hospital.uciRegistration.infraestructure.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketJpaRepository extends JpaRepository<TicketEntity, UUID> {
    List<TicketEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    Optional<TicketEntity> findByPatient(PatientEntity patientEntity);
    Optional<TicketEntity> findByColorAndNoun(String color, String noun);

    List<TicketEntity> findAllByStatusOrderByPriorityDescCreatedAtAsc(TicketStatus status);

    List<TicketEntity> findTop5ByStatusInOrderByCreatedAtDesc(List<TicketStatus> statuses);

    @Query("""
        SELECT t FROM TicketEntity t
        WHERE t.status = :status
        ORDER BY
          CASE t.priority
            WHEN 'EMERGENCY' THEN 4
            WHEN 'URGENT' THEN 3
            WHEN 'LESS_URGENT' THEN 2
            WHEN 'NON_URGENT' THEN 1
            ELSE 0
          END DESC,
          t.createdAt ASC
        """)
    List<TicketEntity> findWaitingOrderedByPriorityAndTime(@Param("status") TicketStatus status);

}
