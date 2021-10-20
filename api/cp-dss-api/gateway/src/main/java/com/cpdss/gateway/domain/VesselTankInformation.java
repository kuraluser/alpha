package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class VesselTankInformation {
	
	  private Long id;

	  private Long categoryId;

	  private String categoryName;

	  private String name;

	  private String frameNumberFrom;

	  private String frameNumberTo;

	  private String shortName;
	  
	  private Double fullCapacityCubm;
	  private Double density;

	  private Integer group;

	  private Integer order;
	  
	  private boolean slopTank;

}
