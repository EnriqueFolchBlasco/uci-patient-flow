package com.hospital.uciRegistration.domain.model.ticket;

import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Getter
public class ColorCode {

    private final String color;
    private final String noun;
    private final String color_code;

    public ColorCode(String color, String noun) {
        this.color = color;
        this.noun = noun;
        this.color_code = color + "-" + noun;
    }

}
