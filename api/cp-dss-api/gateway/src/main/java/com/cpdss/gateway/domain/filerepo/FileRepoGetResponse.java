/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.filerepo;

import com.cpdss.common.rest.CommonSuccessResponse;
import java.util.List;
import lombok.Data;

@Data
public class FileRepoGetResponse {
  private List<FileRepoResponse> fileRepos;
  private Long totalElements;
  private CommonSuccessResponse responseStatus;
}
