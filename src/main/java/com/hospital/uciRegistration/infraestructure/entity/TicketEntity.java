package com.hospital.uciRegistration.infraestructure.entity;

import com.hospital.uciRegistration.domain.model.ticket.PriorityType;
import com.hospital.uciRegistration.domain.model.ticket.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TICKETS")
public class TicketEntity {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    private String color;
    private String noun;
    private String colorCode;

    private boolean riskOfFall;
    private boolean breathingDifficulty;
    private boolean severePain;
    private boolean bleeding;
    private boolean unconscious;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
