package com.hospital.uciRegistration.domain.model.ticket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityCalculatorTest {

    @Test
    void should_returnEmergency_when_unconsciousOrBreathingDifficulty() {
        TriageInfo t1 = new TriageInfo(false, true, false, false, false);
        TriageInfo t2 = new TriageInfo(false, false, false, false, true);

        assertEquals(PriorityType.EMERGENCY, PriorityCalculator.calculatePriority(t1));
        assertEquals(PriorityType.EMERGENCY, PriorityCalculator.calculatePriority(t2));
    }

    @Test
    void should_returnUrgent_when_severePainOrBleeding() {
        TriageInfo t1 = new TriageInfo(false, false, true, false, false);
        TriageInfo t2 = new TriageInfo(false, false, false, true, false);

        assertEquals(PriorityType.URGENT, PriorityCalculator.calculatePriority(t1));
        assertEquals(PriorityType.URGENT, PriorityCalculator.calculatePriority(t2));
    }

    @Test
    void should_returnLessUrgent_when_riskOfFallOnly() {
        TriageInfo t = new TriageInfo(true, false, false, false, false);
        assertEquals(PriorityType.LESS_URGENT, PriorityCalculator.calculatePriority(t));
    }

    @Test
    void should_returnNonUrgent_when_noSymptoms() {
        TriageInfo t = new TriageInfo(false, false, false, false, false);
        assertEquals(PriorityType.NON_URGENT, PriorityCalculator.calculatePriority(t));
    }
}
