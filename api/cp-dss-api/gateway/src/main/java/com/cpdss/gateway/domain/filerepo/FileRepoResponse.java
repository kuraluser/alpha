/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.filerepo;

import lombok.Data;

@Data
public class FileRepoResponse {
  private Long id;
  private Long version;
  private String createdBy;
  private String createdDate;
  private String lastModifiedBy;
  private String lastModifiedDate;
  private String voyageNumber;
  private String fileName;
  private String fileType;
  private String section;
  private String category;
  private Boolean isActive;
  private Boolean isTransferred;
}
