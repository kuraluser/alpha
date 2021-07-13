/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunicationStatus {
  UPLOAD_WITH_HASH_VERIFIED("UPLOAD_WITH_HASH_VERIFIED"),
  CONFIRM_SUCCESS("CONFIRM_SUCCESS"),
  RECEIVED_WITH_HASH_VERIFIED("RECEIVED_WITH_HASH_VERIFIED");

  private String id;
}
