/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.cargomaster;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CargoDetailed {
  private Long id;
  private String name;
  private String abbreviation;
  private String api;
  private String temp;
  private String reidVapourPressure;
  private String gas;
  private String totalWax;
  private String pourPoint;
  private String cloudPoint;
  private String viscosity;
  private String cowCodes;
  private String hydrogenSulfideOil;
  private String hydrogenSulfideVapour;
  private String benzene;
  private String specialInstrictionsRemark;
  private List<CargoPortMapping> loadingInformation;
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate assayDate;
}
