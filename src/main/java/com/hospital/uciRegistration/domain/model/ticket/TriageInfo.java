package com.hospital.uciRegistration.domain.model.ticket;

import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@Getter
public class TriageInfo {

    private final boolean riskOfFall;
    private final boolean breathingDifficulty;
    private final boolean severePain;
    private final boolean bleeding;
    private final boolean unconscious;

    public TriageInfo(boolean riskOfFall, boolean breathingDifficulty, boolean severePain,
                      boolean bleeding, boolean unconscious) {
        this.riskOfFall = riskOfFall;
        this.breathingDifficulty = breathingDifficulty;
        this.severePain = severePain;
        this.bleeding = bleeding;
        this.unconscious = unconscious;
    }


}
