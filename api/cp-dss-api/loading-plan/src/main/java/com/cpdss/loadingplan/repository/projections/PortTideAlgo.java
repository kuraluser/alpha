/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

/** For Alog processing of Loading Information */
public interface PortTideAlgo {

  Long getId();

  Long getPortXid();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  Date getTideDate();

  BigDecimal getTideHeight();

  @JsonSerialize(using = LocalTimeSerializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  LocalTime getTideTime();
}
