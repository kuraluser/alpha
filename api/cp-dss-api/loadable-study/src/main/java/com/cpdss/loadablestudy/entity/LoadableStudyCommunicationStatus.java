/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import com.cpdss.loadablestudy.domain.CommunicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * author:Lincy Ignus
 */
@Entity
@Table(name = "communication_status_update")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadableStudyCommunicationStatus extends EntityDoc {

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
