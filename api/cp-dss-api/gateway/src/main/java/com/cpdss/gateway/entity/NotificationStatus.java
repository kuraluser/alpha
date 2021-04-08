/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification_status")
public class NotificationStatus extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "notification_status")
  private String notificationStatus;

  @Column(name = "is_active")
  private boolean isActive;
}
