/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.utility;

import java.time.LocalDateTime;

/**
 * Class to include all time related utility methods
 *
 * @author sreemanikandan.k
 * @since 24/02/2022
 * @version 24/02/2022
 * @see UnitConversionUtility
 */
public class TimeUtility {

  /**
   * Method to convert LocalDateTime based on zone offset
   *
   * @param dateTime input date time object
   * @param offset time zone offset value
   * @return converted date time resultant
   */
  public static LocalDateTime getTimezoneConvertedDate(LocalDateTime dateTime, double offset) {
    long hours = (long) offset;
    long minutes = (long) ((offset * 10) % 10) * 6;
    LocalDateTime newDateTime = dateTime.plusHours(hours);
    newDateTime = newDateTime.plusMinutes(minutes);
    return newDateTime;
  }
}
