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
    LOADABLE_STUDY("Loadable Study"),
    LOADING_PLAN("Loading Plan"),
    DISCHARGE_STUDY("Discharge Study"),
    DISCHARGE_PLAN("Discharge Plan"),
    ANY("Any"),
    BUNKERING("Bunkering"),
    DISCHARGING("Discharging"),
    LOADING("Loading");

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

  public static final String FILE_REPO_CATEGORY = "Process";
}
