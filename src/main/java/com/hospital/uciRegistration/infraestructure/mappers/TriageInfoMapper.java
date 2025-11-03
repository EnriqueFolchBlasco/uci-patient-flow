package com.hospital.uciRegistration.infraestructure.mappers;

import com.hospital.uciRegistration.domain.model.ticket.TriageInfo;
import org.springframework.stereotype.Component;

@Component
public class TriageInfoMapper {

    public TriageInfo fromEntityFields(
            boolean riskOfFall,
            boolean breathingDifficulty,
            boolean severePain,
            boolean bleeding,
            boolean unconscious
    ) {
        return new TriageInfo(riskOfFall, breathingDifficulty, severePain, bleeding, unconscious);
    }

    public boolean[] toEntityFields(TriageInfo triageInfo) {

        if (triageInfo == null) {
          return new boolean[5];
        }

        return new boolean[]{
                triageInfo.isRiskOfFall(),
                triageInfo.isBreathingDifficulty(),
                triageInfo.isSeverePain(),
                triageInfo.isBleeding(),
                triageInfo.isUnconscious()
        };
    }
}
