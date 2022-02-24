/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/** @Author jerin.g */
@Data
@JsonInclude(Include.NON_EMPTY)
public class Voyage {
  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long captainId;

  @NotNull(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private Long chiefOfficerId;

  @NotBlank(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String voyageNo;

  private Long id;

  private String startDate;

  private String endDate;

  private String status;

  private Long confirmedLoadableStudyId;

  private Long confirmedDischargeStudyId;

  private Boolean isDischargeStarted;

  private List<PortRotation> loadingPorts;

  private List<PortRotation> dischargingPorts;

  private List<Cargo> cargos;

  private String charterer;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate plannedStartDate;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate plannedEndDate;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private LocalDate actualStartDate;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private LocalDate actualEndDate;

  private Long statusId;

  private Long startTimezoneId;

  private Long endTimezoneId;

  private Long noOfDays;
}
