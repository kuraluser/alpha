/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @Author jerin.g */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgoError {
  private String errorHeading;
  private List<String> errorDetails;
}
