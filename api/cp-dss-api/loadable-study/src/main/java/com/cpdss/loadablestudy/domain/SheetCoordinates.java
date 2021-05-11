/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Class for sheet coordinates - Loadable Plan Report */
@Getter
@Setter
@AllArgsConstructor
public class SheetCoordinates {
  private int row;
  private int column;
}
