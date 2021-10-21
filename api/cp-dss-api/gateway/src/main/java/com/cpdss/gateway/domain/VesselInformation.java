/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import lombok.Data;

@Data
public class VesselInformation {

  private Long vesselId;

  private String name;

  private String typeOfShip;

  private String builder;

  private String dateOfLaunching;

  private String signalLetter;

  private String officialNumber;
}
