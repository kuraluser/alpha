/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

import org.springframework.stereotype.Component;

/** @author sanalkumar.k */
@Component
public class UnitConversionUtility {

  /** Convert to BBLS */
  public static Double convertToBBLS(
      UnitTypes unitFrom, Double api, Double temperature, Double value) {
    Double constant = getConversionConstant(unitFrom, api, temperature);
    return unitFrom.equals(UnitTypes.OBSKL) || unitFrom.equals(UnitTypes.OBSBBLS)
        ? value * constant
        : value / constant;
  }

  /** Convert From BBLS */
  public static Double convertFromBBLS(
      UnitTypes unitTo, Double api, Double temperature, Double value) {
    Double constant = getConversionConstant(unitTo, api, temperature);
    return unitTo.equals(UnitTypes.OBSKL) || unitTo.equals(UnitTypes.OBSBBLS)
        ? value / constant
        : value * constant;
  }

  public static Double getConversionConstant(UnitTypes unitFrom, Double api, Double temperature) {
    Double conversionConstant = 0.0;
    switch (unitFrom) {
      case MT:
        conversionConstant = (((535.1911 / (api + 131.5)) - 0.0046189) * 0.42) / 10;
        break;
      case KL:
        conversionConstant = 0.15899;
        break;
      case OBSKL:
        conversionConstant =
            6.28981
                * (Math.exp(
                    -(341.0957 / Math.pow((141360.198 / (api + 131.5)), 2))
                        * (temperature - 60)
                        * (1
                            + (0.8
                                * (341.0957 / Math.pow((141360.198 / (api + 131.5)), 2))
                                * (temperature - 60)))));
        break;
      case LT:
        conversionConstant = ((589.943 / (api + 131.5)) - 0.0050789) * 0.0375;
        break;

      default:
        conversionConstant = 1.0;
        break;
    }
    return conversionConstant;
  }

  /** Set double value precision to required decimal places */
  public static String setPrecision(Object value, Integer precision) {
    return String.format("%." + precision + "f", value);
  }
}
