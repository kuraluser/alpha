/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository.projections;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/** For Alog processing of Loading Information */
public interface PortTideAlgo {

  Long getId();

  Long getPortXid();

  Date getTideDate();

  BigDecimal getTideHeight();

  Timestamp getTideTime();
}
