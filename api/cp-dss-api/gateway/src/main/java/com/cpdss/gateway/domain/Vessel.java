/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/**
 * Vessel dto class
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class Vessel {

  private Long id;

  private String name;

  private String imoNumber;

  private String flagPath;

  private Long chiefOfficerId;

  private String chiefOfficerName;

  private Long captainId;

  private String captainName;

  private String charterer;

  private List<LoadLine> loadlines;

  private String code;

  private String portOfRegistry;

  private String builder;

  private String officialNumber;

  private String signalLetter;

  private Long navigationAreaId;

  private String typeOfShip;

  private String registerLength;

  private String lengthOverall;

  private String lengthBetweenPerpendiculars;

  private String breadthMolded;

  private String depthMolded;

  private String designedLoaddraft;

  private String draftFullLoadSummer;

  private String thicknessOfUpperDeckStringerPlate;

  private String thicknessOfKeelplate;

  private String deadweight;

  private String lightweight;

  private String lcg;

  private String keelToMastHeight;

  private String deadweightConstant;

  private String provisionalConstant;

  private String deadweightConstantLcg;

  private String provisionalConstantLcg;

  private String grossTonnage;

  private String netTonnage;

  private String deadweightConstantTcg;

  private String frameSpace3l;

  private String frameSpace7l;

  private Boolean hasLoadicator;

  private String bmSfModelType;

  private String maxLoadRate;

  private String mastRiser;

  private String heightOfManifoldAboveDeck;
}
