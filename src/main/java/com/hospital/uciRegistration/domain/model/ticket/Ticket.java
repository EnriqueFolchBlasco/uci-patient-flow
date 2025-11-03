package com.hospital.uciRegistration.domain.model.ticket;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

@AggregateRoot
@Getter
public class Ticket {

    private final UUID id;
    private final Patient patient;
    private PriorityType priority;
    private final ColorCode colorCode;
    private TriageInfo triageInfo;
    private final LocalDateTime createdAt;
    private TicketStatus status;

    public Ticket(Patient patient, TriageInfo triageInfo, ColorCode colorCode) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.priority = PriorityCalculator.calculatePriority(triageInfo);
        this.triageInfo = triageInfo;
        this.colorCode = colorCode;
        this.createdAt = LocalDateTime.now();
        this.status = TicketStatus.WAITING;
    }

    public Ticket(UUID id,
                  Patient patient,
                  PriorityType priority,
                  ColorCode code,
                  TriageInfo triageInfo,
                  LocalDateTime createdAt,
                  TicketStatus status) {
        this.id = id;
        this.patient = patient;
        this.priority = priority;
        this.colorCode = code;
        this.triageInfo = triageInfo;
        this.createdAt = createdAt;
        this.status = status;
    }

    public void changePriority(PriorityType newPriority) { this.priority = newPriority; }

    public void changeTriageInfo(TriageInfo newTriageInfo) { this.triageInfo = newTriageInfo; }

    private void changeStatus(TicketStatus newStatus) {
        if (this.status == TicketStatus.COMPLETED) {
            throw new IllegalStateException("Cannot change status of a completed ticket");
        }
        if (newStatus == TicketStatus.IN_PROGRESS && this.status != TicketStatus.WAITING) {
            throw new IllegalStateException("Ticket must be waiting to start progress");
        }
        this.status = newStatus;
    }

    public boolean isWaiting() {
        return status == TicketStatus.WAITING;
    }

    public void markInProgress() { changeStatus(TicketStatus.IN_PROGRESS); }
    public void markAsComplete() { changeStatus(TicketStatus.COMPLETED); }
    public void markAsCancelled() { changeStatus(TicketStatus.CANCELLED); }

    public static Ticket createTicket(Patient patient, TriageInfo triageInfo, ColorCode colorCode) {

        Assert.notNull(patient, "Patient must not be null");
        Assert.notNull(triageInfo, "Triage parameters must not be null");
        Assert.notNull(colorCode, "Color code must not be null");

        return new Ticket(
                patient,
                triageInfo,
                colorCode
        );
    }
}