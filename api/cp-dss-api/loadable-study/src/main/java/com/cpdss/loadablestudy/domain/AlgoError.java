/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @Author vinothkumar */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlgoError {
  private String errorHeading;
  private List<String> errorDetails;
}
