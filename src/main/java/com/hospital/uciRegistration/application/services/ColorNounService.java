package com.hospital.uciRegistration.application.services;

import com.hospital.uciRegistration.common.exception.NoAvailableColorCodeException;
import com.hospital.uciRegistration.domain.model.ticket.ColorCode;
import com.hospital.uciRegistration.infraestructure.entity.ColorEntity;
import com.hospital.uciRegistration.infraestructure.entity.NounEntity;
import com.hospital.uciRegistration.infraestructure.repository.jpa.ColorJpaRepository;
import com.hospital.uciRegistration.infraestructure.repository.jpa.NounJpaRepository;
import com.hospital.uciRegistration.infraestructure.repository.jpa.TicketJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;


@Slf4j
@Service
public class ColorNounService {

    private final ColorJpaRepository colorRepo;
    private final NounJpaRepository nounRepo;
    private final TicketJpaRepository ticketRepo;
    private static final int MAX_ATTEMPTS = 100;


    public ColorNounService(ColorJpaRepository colorRepo, NounJpaRepository nounRepo, TicketJpaRepository ticketRepo) {
        this.colorRepo = colorRepo;
        this.nounRepo = nounRepo;
        this.ticketRepo = ticketRepo;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ColorCode generateUniqueColorCode() {

        ColorCode code;
        int attempts = 0;

        do {
            ColorEntity color = colorRepo.findRandom();
            NounEntity noun = nounRepo.findRandom();

            code = new ColorCode(color.getName(), noun.getName());
            attempts++;

            if (attempts > MAX_ATTEMPTS) {
                throw new NoAvailableColorCodeException("Unable to generate unique color-noun code");
            }

        } while (ticketRepo.findByColorAndNoun(code.getColor(), code.getNoun()).isPresent());

        log.debug("Generated color-noun pair attempt {}: {}-{}", attempts, code.getColor(), code.getNoun());

        return code;
    }
}
