/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.domain;

import lombok.Data;

/** @Author jerin.g */
@Data
public class OnBoardQuantity {
  private Integer id;
  private Integer loadablestudyXId;
  private Integer portXId;
  private String portCode;
  private Integer tankXId;
  private Integer cargoXid;
  private String sounding;
  private String weight;
  private String volume;
}
