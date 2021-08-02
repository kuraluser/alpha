/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_EMPTY)
public class VoyageDto {
  private Long captainId;

  private Long chiefOfficerId;

  private String voyageNo;

  private Long id;

  private String startDate;

  private String endDate;

  private String status;

  private String charterer;

  private LocalDate plannedStartDate;

  private LocalDate plannedEndDate;

  private LocalDate actualStartDate;

  private LocalDate actualEndDate;

  private Long startTimezoneId;

  private Long endTimezoneId;

  private Long noOfDays;
}
