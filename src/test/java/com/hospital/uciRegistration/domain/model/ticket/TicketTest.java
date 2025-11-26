package com.hospital.uciRegistration.domain.model.ticket;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private final UUID id = UUID.randomUUID();
    private final Patient patient  = new Patient();
    private final PriorityType priority = PriorityType.EMERGENCY;
    private final ColorCode colorCode = new ColorCode("Green", "Car");
    private final TriageInfo triageInfo = new TriageInfo(true, true, true, true, true);
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final TicketStatus status = TicketStatus.WAITING;

    @Test
    void should_throwIllegalArgumentException_when_patient_is_null() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Ticket.createTicket(null,triageInfo, colorCode));
        assertEquals("Patient must not be null", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_triage_info_is_null() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Ticket.createTicket(patient,null,colorCode));
        assertEquals("Triage parameters must not be null", exception.getMessage());
    }

    @Test
    void should_throwIllegalArgumentException_when_colorCode_is_null() {
        var exception = assertThrows(IllegalArgumentException.class, () -> Ticket.createTicket(patient,triageInfo,null));
        assertEquals("Color code must not be null", exception.getMessage());
    }

    @Test
    void should_createTicket_when_patientRegisters() {
        var ticket = Ticket.createTicket(patient,triageInfo,colorCode);
        assertEquals(patient,ticket.getPatient());
        assertEquals(triageInfo,ticket.getTriageInfo());
        assertEquals(colorCode, ticket.getColorCode());
    }

    @Test
    void should_changeStatusInProgress_when_statusIsWAITING() {
        var ticket = Ticket.createTicket(patient,triageInfo,colorCode);
        ticket.markInProgress();
        assertEquals(TicketStatus.IN_PROGRESS, ticket.getStatus());
    }

    @Test
    void should_markAsCancelled() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        ticket.markAsCancelled();
        assertEquals(TicketStatus.CANCELLED, ticket.getStatus());
    }

    @Test
    void should_markAsComplete() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        ticket.markInProgress();
        ticket.markAsComplete();
        assertEquals(TicketStatus.COMPLETED, ticket.getStatus());
    }

    @Test
    void should_markAsCancelledAfterInProgress() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        ticket.markInProgress();
        ticket.markAsCancelled();
        assertEquals(TicketStatus.CANCELLED, ticket.getStatus());
    }

    @Test
    void should_notAllowStatusChange_afterCompleted() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        ticket.markInProgress();
        ticket.markAsComplete();
        assertThrows(IllegalStateException.class, ticket::markInProgress);
    }

    @Test
    void should_changeTriageInfo() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        var newTriage = new TriageInfo(false, false, false, false, false);
        ticket.changeTriageInfo(newTriage);
        assertEquals(newTriage, ticket.getTriageInfo());
    }

    @Test
    void should_changePriority_when_priorityIsValid() {
        var ticket = Ticket.createTicket(patient,triageInfo,colorCode);
        ticket.changePriority(PriorityType.LESS_URGENT);
        assertEquals(PriorityType.LESS_URGENT,ticket.getPriority());
    }

    @Test
    void should_changeTriageInfo_when_triageInfoIsValid() {
        var ticket = Ticket.createTicket(patient,triageInfo,colorCode);
        TriageInfo newTriageInfo = new TriageInfo(false, false, false, false, false);
        ticket.changeTriageInfo(newTriageInfo);
        assertEquals(newTriageInfo,ticket.getTriageInfo());
    }

    @Test
    void should_throwException_when_changingFromCompletedToInProgress() {
        var ticket = Ticket.createTicket(patient,triageInfo,colorCode);
        ticket.markInProgress();
        assertThrows(IllegalStateException.class, ticket::markInProgress);
    }

    @Test
    void should_returnTrue_when_ticketIsWaiting() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        assertTrue(ticket.isWaiting());
    }

    @Test
    void should_returnFalse_when_ticketNotWaiting() {
        var ticket = Ticket.createTicket(patient, triageInfo, colorCode);
        ticket.markInProgress();
        assertFalse(ticket.isWaiting());
    }


    @Test
    void should_initializeAllFields_when_usingFullConstructor() {
        Ticket ticket = new Ticket(id, patient, priority, colorCode, triageInfo, createdAt, status);

        assertEquals(id, ticket.getId());
        assertEquals(patient, ticket.getPatient());
        assertEquals(priority, ticket.getPriority());
        assertEquals(colorCode, ticket.getColorCode());
        assertEquals(triageInfo, ticket.getTriageInfo());
        assertEquals(createdAt, ticket.getCreatedAt());
        assertEquals(status, ticket.getStatus());
    }


}