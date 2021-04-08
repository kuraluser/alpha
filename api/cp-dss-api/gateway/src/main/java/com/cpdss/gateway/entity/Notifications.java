/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "notifications")
public class Notifications extends EntityDoc {

  @Column(name = "notification_type")
  private String notificationType;

  @Column(name = "requested_by")
  private Long requestedBy;

  @Column(name = "assigned_to")
  private Long assignedTo;

  @Column(name = "description")
  private String description;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "notification_status_xid")
  private NotificationStatus notificationStatus;
}
