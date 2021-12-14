package com.cpdss.gateway.domain.vessel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VesselCowParameters {
    private Long id;
    private Long vesselId;
    private String topCowMinDuration;
    private String topCowMaxDuration;
    private String bottomCowMinDuration;
    private String bottomCowMaxDuration;
    private String fullCowMinDuration;
    private String fullCowMaxDuration;
    private String topWashMinAngle;
    private String topWashMaxAngle;
    private String bottomWashMinAngle;
    private String bottomWashMaxAngle;
}
