/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class SynopticalTableResponse {

  private CommonSuccessResponse responseStatus;

  private List<SynopticalRecord> synopticalRecords;

  private Long id;

  // private List<Long> failedRecords;

  private Map<Long, String> failedRecords;
}
