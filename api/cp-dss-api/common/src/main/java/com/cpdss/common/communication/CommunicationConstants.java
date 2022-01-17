/* Licensed at AlphaOri Technologies */
package com.cpdss.common.communication;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommunicationConstants {

  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Getter
  @AllArgsConstructor
  public enum CommunicationModule {
    LOADABLE_STUDY("LoadableStudy"),
    LOADING_PLAN("LoadingPlan"),
    DISCHARGE_PLAN("DischargePlan");

    private final String moduleName;

    /**
     * Method to get CommunicationModule enum from moduleName
     *
     * @param moduleName module name value
     * @return CommunicationModule enum
     */
    public static CommunicationModule getCommunicationModule(final String moduleName) {
      return Arrays.stream(CommunicationModule.values())
          .filter(communicationModule -> communicationModule.getModuleName().equals(moduleName))
          .findAny()
          .orElseThrow(
              () -> {
                log.error("Invalid moduleName: {} for enum CommunicationModule", moduleName);
                return new IllegalArgumentException("Invalid moduleName: " + moduleName);
              });
    }
  }

  @Getter
  @AllArgsConstructor
  public enum CommunicationMetadata {
    PROCESS_IDENTIFIER("processIdentifier"),
    PROCESS_ID("processId"),
    PROCESS_GROUP_ID("processGroupId"),
    DEPENDANT_PROCESS_ID("dependantProcessId"),
    DEPENDANT_PROCESS_MODULE("dependantProcessModule");

    private final String fieldName;
  }
}
