package com.hospital.uciRegistration.interfaces.rest;

import com.hospital.uciRegistration.application.dto.PatientRegisterRequest;
import com.hospital.uciRegistration.application.dto.TicketCreateRequest;
import com.hospital.uciRegistration.application.dto.TicketDTO;
import com.hospital.uciRegistration.application.usecase.patient.RegisterPatientUseCase;
import com.hospital.uciRegistration.application.usecase.queue.AttendNextTicketUseCase;
import com.hospital.uciRegistration.application.usecase.ticketmanagement.ChangeTicketStatusUseCase;
import com.hospital.uciRegistration.application.usecase.ticketmanagement.CreateTicketUseCase;
import com.hospital.uciRegistration.application.usecase.ticketmanagement.GetTicketByPatientIdUseCase;
import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.Ticket;
import com.hospital.uciRegistration.application.dto.PatientTicketRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {

    private final RegisterPatientUseCase registerPatientUseCase;
    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByPatientIdUseCase getTicketByPatientIdUseCase;
    private final AttendNextTicketUseCase attendNextTicketUseCase;
    private final ChangeTicketStatusUseCase changeTicketStatusUseCase;

    @PostMapping("/register")
    public ResponseEntity<TicketDTO> registerPatientAndCreateTicket(
            @RequestBody PatientTicketRequest request) {

        PatientRegisterRequest patientRequest = new PatientRegisterRequest(
                request.getId(),
                request.getFirstName(),
                request.getLastName(),
                request.getStreet(),
                request.getCity(),
                request.getZip(),
                request.getState()
        );

        TicketCreateRequest ticketRequest = new TicketCreateRequest(
                request.isRiskOfFall(),
                request.isBreathingDifficulty(),
                request.isSeverePain(),
                request.isBleeding(),
                request.isUnconscious()
        );

        Patient patient = registerPatientUseCase.execute(patientRequest);
        TicketDTO ticketDTO = createTicketUseCase.execute(patient, ticketRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketDTO);
    }

    @GetMapping("/{id}")
    public Ticket getPatientTicket(@PathVariable String id) {
        return getTicketByPatientIdUseCase.execute(id);
    }

    @PostMapping("/next")
    public Ticket callNextTicket() {
        return attendNextTicketUseCase.execute();
    }

    @PutMapping("/{id}/change-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@PathVariable String id, @RequestBody String status) {
        changeTicketStatusUseCase.execute(id, status);
    }
}
