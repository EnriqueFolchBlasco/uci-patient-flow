package com.hospital.uciRegistration.domain.model.ticket;

public class PriorityCalculator {

    public static PriorityType calculatePriority(TriageInfo triageInfo) {
        if (triageInfo.isUnconscious() || triageInfo.isBreathingDifficulty()) {
            return PriorityType.EMERGENCY;

        } else if (triageInfo.isSeverePain() || triageInfo.isBleeding()) {
            return PriorityType.URGENT;

        } else if (triageInfo.isRiskOfFall()) {
            return PriorityType.LESS_URGENT;

        } else {
            return PriorityType.NON_URGENT;
        }
    }
}
