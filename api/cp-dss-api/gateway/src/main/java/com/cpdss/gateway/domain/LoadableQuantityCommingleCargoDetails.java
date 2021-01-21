/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class LoadableQuantityCommingleCargoDetails {
  private Long id;
  private String grade;
  private String tankName;
  private String quantity;
  private String api;
  private String temp;
  private String cargo1Abbreviation;
  private String cargo2Abbreviation;
  private String cargo1Percentage;
  private String cargo2Percentage;
  private String cargo1Bblsdbs;
  private String cargo2Bblsdbs;
  private String cargo1Bbls60f;
  private String cargo2Bbls60f;
  private String cargo1LT;
  private String cargo2LT;
  private String cargo1MT;
  private String cargo2MT;
  private String cargo1KL;
  private String cargo2KL;
}
