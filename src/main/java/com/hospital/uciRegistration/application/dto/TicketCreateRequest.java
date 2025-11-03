package com.hospital.uciRegistration.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateRequest {

    private boolean riskOfFall;
    private boolean breathingDifficulty;
    private boolean severePain;
    private boolean bleeding;
    private boolean unconscious;
}
