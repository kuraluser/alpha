/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g */
@Data
public class BMAndSF {
  private List<BendingMoment> bendingMoment;
  private List<ShearingForce> shearingForce;
  private List<CalculationSheet> calculationSheet;
  private List<CalculationSheetTankGroup> calculationSheetTankGroup;
  private List<MinMaxValuesForBMAndSf> minMaxValuesForBMAndSfs;
  private List<StationValues> stationValues;
  private List<InnerBulkHeadValues> innerBulkHeadValues;
}
