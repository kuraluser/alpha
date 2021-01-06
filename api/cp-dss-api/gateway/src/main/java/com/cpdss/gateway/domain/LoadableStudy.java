/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonErrorCodes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for Loadable study
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class LoadableStudy {

  private Long id;

  @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String name;

  @Size(max = 1000, message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String detail;

  private String status;

  private Long statusId;

  private String createdDate;

  @NotEmpty(message = CommonErrorCodes.E_HTTP_BAD_REQUEST)
  private String charterer;

  private String subCharterer;

  private BigDecimal draftMark;

  private Long loadLineXId;

  private BigDecimal draftRestriction;

  private BigDecimal maxAirTemperature;

  private BigDecimal maxWaterTemperature;

  private Long vesselId;

  private Long voyageId;

  private Long companyId;

  private Long createdFromId;

  private List<Long> dischargingPortIds;

  private List<LoadableStudyAttachmentData> LoadableStudyAttachment;
}
