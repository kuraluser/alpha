/* Licensed at AlphaOri Technologies */
package com.cpdss.common.constants;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileRepoConstants {

  @Getter
  @AllArgsConstructor
  public enum FileRepoSection {
    LOADABLE_STUDY("LoadableStudy"),
    LOADING_PLAN("LoadingPlan"),
    DISCHARGE_STUDY("DischargeStudy"),
    DISCHARGE_PLAN("DischargePlan");

    private final String section;
  }

  /**
   * Method to get FileRepoSection enum from section name
   *
   * @param section section name value
   * @return FileRepoSection enum
   */
  public static FileRepoSection getFileRepoSection(final String section) {
    return Arrays.stream(FileRepoSection.values())
        .filter(fileRepoSection -> fileRepoSection.getSection().equals(section))
        .findAny()
        .orElseThrow(
            () -> {
              log.error("Invalid section: {} for enum FileRepoSection", section);
              return new IllegalArgumentException("Invalid section: " + section);
            });
  }
}
