package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class VesselInformation {
	
	  private Long vesselId;

	  private String vesselName;

	  private String vesselType;

	  private String builder;

	  private String dateOfLaunch;

	  private String signalLetter;

	  private String officialNumber;


}
