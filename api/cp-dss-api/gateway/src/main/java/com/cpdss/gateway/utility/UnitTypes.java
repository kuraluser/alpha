package com.cpdss.gateway.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sanalkumar.k
 *
 */
@AllArgsConstructor
@Getter
public enum UnitTypes {
	  MT("MT"),
	  KL("KL"),
	  OBSKL("OBSKL"),
	  OBSBBLS("OBSBBLS"),
	  BBLS("BBLS"),
	  LT("LT");

	  private String type;
}
