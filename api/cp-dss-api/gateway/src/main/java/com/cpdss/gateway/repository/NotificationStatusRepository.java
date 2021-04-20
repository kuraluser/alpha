/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.NotificationStatus;

/**
 * User repository - to interact with {@link NotificationStatus} table
 *
 * @author sreekumar.k
 */
public interface NotificationStatusRepository
    extends CommonCrudRepository<NotificationStatus, Long> {}
