package com.cpdss.gateway.domain.loadingplan;

import lombok.Data;

@Data
public class PortLoadablePlanBallastDetails {
    private Long cargoId;
    private String colorCode;
    private String correctedUllage;
    private String correctionFactor ;
    private String fillingPercentage;
    private Long id;
    private boolean isActive;
    private Long loadablePatternId;
    private String rdgUllage ;
    private Long tankId;
    private String tankname;
    private String temperature;
    private String quantity;
    private String actualPlanned;
    private String arrivalDeparture;
    private String sounding;
}
