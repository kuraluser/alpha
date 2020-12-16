/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.entity.VoyageHistory;

/** @author suhail.k */
public interface VoyageHistoryRepository extends CommonCrudRepository<VoyageHistory, Long> {

  public VoyageHistory findFirstByVoyageOrderByPortOrderDesc(Voyage voyage);
}
