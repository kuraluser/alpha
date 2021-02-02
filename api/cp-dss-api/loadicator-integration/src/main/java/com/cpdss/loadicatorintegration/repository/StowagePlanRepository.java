/* Licensed under Apache-2.0 */
package com.cpdss.loadicatorintegration.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadicatorintegration.entity.StowagePlan;
import java.util.Optional;

public interface StowagePlanRepository extends CommonCrudRepository<StowagePlan, Long> {

  Optional<StowagePlan> findByBookingListId(Long bookingListId);
}
