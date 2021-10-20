package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class VesselDraftDisplacementResponse {
	
    private Double depthMoulded;
    private Double thicknessOfUpperDeck;
    private Double thicknessOfKeelPlate;
    private Double totalDepth;

}
