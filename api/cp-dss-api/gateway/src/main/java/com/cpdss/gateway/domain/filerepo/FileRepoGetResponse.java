/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.filerepo;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.entity.FileRepo;
import java.util.List;
import lombok.Data;

@Data
public class FileRepoGetResponse {
  private List<FileRepo> fileRepos;
  private Long totalElements;
  private CommonSuccessResponse responseStatus;
}
