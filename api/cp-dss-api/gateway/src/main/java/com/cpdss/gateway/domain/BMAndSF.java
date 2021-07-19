/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import java.util.List;
import lombok.Data;

/** @Author jerin.g ravi.r */
@Data
public class BMAndSF {
  private List<CalculationSheet> calculationSheet;
  private List<CalculationSheetTankGroup> calculationSheetTankGroup;
  private List<MinMaxValuesForBMAndSf> minMaxValuesForBMAndSfs;
  private List<StationValues> stationValues;
  private List<InnerBulkHeadValues> innerBulkHeadValues;
  private List<BendingMomentType1> bendingMomentType1;
  private List<ShearingForceType1> shearingForceType1;
  private List<BendingMomentType2> bendingMomentType2;
  private List<BendingMomentType4> bendingMomentType4;
  private List<ShearingForceType2> shearingForceType2;
  private List<ShearingForceType4> shearingForceType4;
  private List<BendingMomentShearingForceType3> bendingMomentShearingForceType3;
}
