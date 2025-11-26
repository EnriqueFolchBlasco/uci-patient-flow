package com.hospital.uciRegistration.application.services;

import com.hospital.uciRegistration.common.exception.NoAvailableColorCodeException;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.infraestructure.entity.ColorEntity;
import com.hospital.uciRegistration.infraestructure.entity.NounEntity;
import com.hospital.uciRegistration.infraestructure.entity.TicketEntity;
import com.hospital.uciRegistration.infraestructure.repository.jpa.ColorJpaRepository;
import com.hospital.uciRegistration.infraestructure.repository.jpa.NounJpaRepository;
import com.hospital.uciRegistration.infraestructure.repository.jpa.TicketJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColorNounServiceTest {

    @Mock
    private ColorJpaRepository colorRepository;

    @Mock
    private NounJpaRepository nounRepository;

    @Mock
    private TicketJpaRepository ticketRepository;

    private ColorNounService service;

    @BeforeEach
    void setUp() {
        service = new ColorNounService(colorRepository, nounRepository, ticketRepository);
    }

    @Test
    void should_generateUniqueColorCode_firstTry() {

        ColorEntity color = new ColorEntity();
        NounEntity noun = new NounEntity();

        color.setName("RED");
        noun.setName("WOLF");

        when(colorRepository.findRandom()).thenReturn(color);
        when(nounRepository.findRandom()).thenReturn(noun);
        when(ticketRepository.findByColorAndNoun("RED", "WOLF"))
                .thenReturn(Optional.empty());

        ColorCode result = service.generateUniqueColorCode();

        assertEquals("RED", result.getColor());
        assertEquals("WOLF", result.getNoun());
        verify(ticketRepository).findByColorAndNoun("RED", "WOLF");
    }

    @Test
    void should_retryAndReturnSecondColorCode_whenFirstExists() {

        ColorEntity firstColor = new ColorEntity();
        NounEntity firstNoun = new NounEntity();
        ColorEntity secondColor = new ColorEntity();
        NounEntity secondNoun = new NounEntity();

        firstColor.setName("RED");
        secondColor.setName("BLUE");
        firstNoun.setName("WOLF");
        secondNoun.setName("FOX");

        when(colorRepository.findRandom())
                .thenReturn(firstColor)
                .thenReturn(secondColor);

        when(nounRepository.findRandom())
                .thenReturn(firstNoun)
                .thenReturn(secondNoun);

        when(ticketRepository.findByColorAndNoun("RED", "WOLF"))
                .thenReturn(Optional.of(new TicketEntity()));

        when(ticketRepository.findByColorAndNoun("BLUE", "FOX"))
                .thenReturn(Optional.empty());


        ColorCode result = service.generateUniqueColorCode();

        assertEquals("BLUE", result.getColor());
        assertEquals("FOX", result.getNoun());
        verify(ticketRepository, times(1)).findByColorAndNoun("RED", "WOLF");
        verify(ticketRepository, times(1)).findByColorAndNoun("BLUE", "FOX");
    }

    @Test
    void shouldThrowException_whenMaxAttemptsReached() {
        ColorEntity color = new ColorEntity();
        NounEntity noun = new NounEntity();
        color.setName("RED");
        noun.setName("WOLF");

        when(colorRepository.findRandom()).thenReturn(color);
        when(nounRepository.findRandom()).thenReturn(noun);

        when(ticketRepository.findByColorAndNoun("RED", "WOLF"))
                .thenReturn(Optional.of(new TicketEntity()));

        assertThrows(NoAvailableColorCodeException.class, () -> {
            service.generateUniqueColorCode();
        });

        verify(ticketRepository, times(100)).findByColorAndNoun("RED", "WOLF");
    }
}
