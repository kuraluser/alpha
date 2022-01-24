/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import com.cpdss.common.constants.FileRepoConstants.FileRepoSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for file repo add request */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRepoAddRequest {

  private byte[] file;
  private String voyageNo;
  private String fileName;
  private String fileType;
  private FileRepoSection section;
  private String category;
  private Long vesselId;
}
