/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
}
