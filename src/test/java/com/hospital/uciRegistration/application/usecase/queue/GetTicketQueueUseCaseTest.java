package com.hospital.uciRegistration.application.usecase.queue;

import com.hospital.uciRegistration.application.dto.DisplayTicket;
import com.hospital.uciRegistration.application.services.TicketDisplayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTicketQueueUseCaseTest {

    @Mock
    private TicketDisplayService ticketDisplayService;

    private GetTicketQueueUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetTicketQueueUseCase(ticketDisplayService);
    }

    @Test
    void should_returnDisplayTickets_fromService() {

        List<DisplayTicket> mockedList = List.of(
                new DisplayTicket("RED", "Just appeared")
        );

        when(ticketDisplayService.getQueueForScreen()).thenReturn(mockedList);
        List<DisplayTicket> result = useCase.execute();

        assertEquals(mockedList, result);
        verify(ticketDisplayService, times(1)).getQueueForScreen();
    }
}
