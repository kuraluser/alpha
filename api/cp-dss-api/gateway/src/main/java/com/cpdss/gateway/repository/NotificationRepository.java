/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.Notifications;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notifications, Long> {
  public List<Notifications> findByRequestedByAndIsActive(long requestedUserId, boolean isActive);

  public List<Notifications> findByNotificationTypeAndIsActive(
      String notificationType, boolean isActive);
}
