/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/** Class for PortOperation details - Loadable Plan Report */
@Builder
@Data
public class PortOperationTable {
  private List<OperationsTable> operationsTableList;
}
