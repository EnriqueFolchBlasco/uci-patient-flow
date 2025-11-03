package com.hospital.uciRegistration.interfaces.rest;

import com.hospital.uciRegistration.application.dto.DisplayTicket;
import com.hospital.uciRegistration.application.usecase.queue.GetTicketQueueUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/queue")
@AllArgsConstructor
public class QueueController {

    private final GetTicketQueueUseCase getTicketQueueUseCase;

    @GetMapping
    public ResponseEntity<List<DisplayTicket>> getQueue() {
        return ResponseEntity.ok(getTicketQueueUseCase.execute());
    }

}