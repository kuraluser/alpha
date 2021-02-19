/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData;

/** @author suhail.k */
public interface SynopticalTableLoadicatorDataRepository
    extends CommonCrudRepository<SynopticalTableLoadicatorData, Long> {

  public SynopticalTableLoadicatorData findBySynopticalTableAndIsActive(
      SynopticalTable synopticalTable, boolean isActive);

  public SynopticalTableLoadicatorData findBySynopticalTableAndLoadablePatternIdAndIsActive(
      SynopticalTable synopticalTable, Long loadablePatternId, boolean isActive);
}
