/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Class for CommingleDetails - Loadable Plan Report
 *
 * @author sreemanikandan.k
 * @since 03/01/2022
 * @version 29/01/2022
 */
@Builder
@Data
@AllArgsConstructor
public class CommingleDetails {

  private String grade;

  private String tankName;

  private String quantity;

  private String api;

  private String temperature;

  private String cargo1Abbreviation;

  private String cargo2Abbreviation;

  private String cargo1Percentage;

  private String cargo2Percentage;

  private String cargo1Mt;

  private String cargo2Mt;

  private String commingleColour;
}
