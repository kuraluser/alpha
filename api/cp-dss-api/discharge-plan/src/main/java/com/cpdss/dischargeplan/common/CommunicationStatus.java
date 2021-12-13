/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunicationStatus {
  UPLOAD_WITH_HASH_VERIFIED("UPLOAD_WITH_HASH_VERIFIED"),
  CONFIRM_SUCCESS("CONFIRM_SUCCESS"),
  RECEIVED_WITH_HASH_VERIFIED("RECEIVED_WITH_HASH_VERIFIED"),
  TIME_OUT("TIME_OUT"),
  COMMUNICATION_FAIL("COMMUNICATION_FAIL"),
  RETRY_AT_SOURCE("RETRY_AT_SOURCE"),
  COMPLETED("COMPLETED");

  private String id;
}
