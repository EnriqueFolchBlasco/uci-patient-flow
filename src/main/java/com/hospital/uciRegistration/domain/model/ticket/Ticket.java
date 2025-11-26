package com.hospital.uciRegistration.domain.model.ticket;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.springframework.util.Assert;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(force = true)
@AggregateRoot
@Getter
@Setter
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

    @ConstructorProperties({"id", "patient", "priority", "colorCode", "triageInfo", "createdAt", "status"})
    public Ticket(UUID id,
                  Patient patient,
                  PriorityType priority,
                  ColorCode colorCode,
                  TriageInfo triageInfo,
                  LocalDateTime createdAt,
                  TicketStatus status) {
        this.id = id;
        this.patient = patient;
        this.priority = priority;
        this.colorCode = colorCode;
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

        if (this.status == TicketStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status of a cancelled ticket");
        }

        switch (this.status) {

            case WAITING:
                if (newStatus == TicketStatus.IN_PROGRESS || newStatus == TicketStatus.CANCELLED) {
                    this.status = newStatus;
                } else {
                    throw new IllegalStateException("Waiting tickets can only move to IN_PROGRESS or CANCELLED");
                }
                break;

            case IN_PROGRESS:
                if (newStatus == TicketStatus.COMPLETED || newStatus == TicketStatus.CANCELLED) {
                    this.status = newStatus;
                } else {
                    throw new IllegalStateException("In-progress tickets can only move to COMPLETED or CANCELLED");
                }
                break;

            default:
                throw new IllegalStateException("Unsupported ticket status transition");
        }
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