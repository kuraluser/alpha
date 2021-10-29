/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PyUserDto {
  private String id;
  private String logFile;
  private String message;
  private String status;
  private String timeStamp;
}
