/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity; /* Licensed at AlphaOri Technologies */

import com.cpdss.common.utils.EntityDoc;
import com.cpdss.dischargeplan.common.CommunicationStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** author:Selvy Thomas */
@Entity
@Table(name = "communication_status_update")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargePlanCommunicationStatus extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "message_uuid")
  private String messageUUID;

  @Column(name = "status")
  private String communicationStatus = CommunicationStatus.UPLOAD_WITH_HASH_VERIFIED.getId();

  @Column(name = "updated_time", columnDefinition = "TIMESTAMP")
  private LocalDateTime communicationDateTime;

  @Column(name = "message_type")
  private String messageType;

  @Column(name = "reference_id")
  private Long referenceId;
}
