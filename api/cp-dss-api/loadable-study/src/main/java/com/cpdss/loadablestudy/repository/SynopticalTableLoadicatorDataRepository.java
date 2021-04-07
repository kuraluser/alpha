/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/** @author suhail.k */
public interface SynopticalTableLoadicatorDataRepository
    extends CommonCrudRepository<SynopticalTableLoadicatorData, Long> {

  public SynopticalTableLoadicatorData findBySynopticalTableAndIsActive(
      SynopticalTable synopticalTable, boolean isActive);

  public SynopticalTableLoadicatorData findBySynopticalTableAndLoadablePatternIdAndIsActive(
      SynopticalTable synopticalTable, Long loadablePatternId, boolean isActive);

  @Modifying
  @Query(
      "UPDATE SynopticalTableLoadicatorData stld SET stld.isActive = false where stld.synopticalTable = ?1 and stld.loadablePatternId = ?2 and stld.isActive = true")
  public void deleteBySynopticalTableAndLoadablePatternId(
      SynopticalTable synopticalTable, Long loadablePatternId);
}
