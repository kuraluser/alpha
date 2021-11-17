/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/** @author suhail.k */
public interface SynopticalTableLoadicatorDataRepository
    extends CommonCrudRepository<SynopticalTableLoadicatorData, Long> {

  public SynopticalTableLoadicatorData findByloadablePatternIdAndSynopticalTableAndIsActive(
      Long LoadablePatternId, SynopticalTable synopticalTable, boolean isActive);

  public SynopticalTableLoadicatorData findBySynopticalTableAndIsActive(
      SynopticalTable synopticalTable, boolean isActive);

  public SynopticalTableLoadicatorData findBySynopticalTableAndLoadablePatternIdAndIsActive(
      SynopticalTable synopticalTable, Long loadablePatternId, boolean isActive);

  @Modifying
  @Query(
      "UPDATE SynopticalTableLoadicatorData stld SET stld.isActive = false WHERE stld.synopticalTable = ?1 AND stld.loadablePatternId = ?2 AND stld.isActive = true")
  public void deleteBySynopticalTableAndLoadablePatternId(
      SynopticalTable synopticalTable, Long loadablePatternId);

  @Modifying
  @Query(
      "UPDATE SynopticalTableLoadicatorData stld SET stld.isActive = ?1 WHERE stld.loadablePatternId = ?2")
  public void deleteByLoadablePatternId(boolean isActive, Long loadablePatternId);

  public List<SynopticalTableLoadicatorData> findByLoadablePatternIdAndIsActive(
      Long loadablePatternId, boolean isActive);

  public SynopticalTableLoadicatorData findByLoadablePatternIdAndPortIdAndOperationId(
      Long loadablePatternId, Long portId, Long operationId);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadicator_data_for_synoptical_table u where loadable_pattern_xid=?1",
      nativeQuery = true)
  String getSynopticalTableLoadicatorDataWithLoadablePatternId(long id);
}
