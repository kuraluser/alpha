/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** Repository interface for synopticalTable entity */
public interface SynopticalTableRepository extends CommonCrudRepository<SynopticalTable, Long> {

  public Optional<SynopticalTable> findByIdAndIsActive(Long id, Boolean isActive);

  /*@Query(
  "select s from SynopticalTable s "
      + "left outer join LoadableStudyPortRotation lp "
      + "on s.loadableStudyXId=lp.loadableStudy.id and s.portXid=lp.portXId "
      + "where s.loadableStudyXId=?1 and s.isActive=true and lp.isActive=true "
      + "order by lp.portOrder, s.operationType")*/
  public List<SynopticalTable> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, boolean isActive);

  public List<SynopticalTable> findByLoadableStudyXIdAndIsActiveAndPortXid(
      Long loadableStudyXId, boolean isActive, Long portId);

  @Transactional
  @Modifying
  @Query(
      "Update SynopticalTable set isActive = false where loadableStudyXId = ?1 AND portXid = ?2 ")
  public void deleteSynopticalPorts(long loadableStudyXId, Long portIds);

  Page<SynopticalTable> findByloadableStudyPortRotation(
      LoadableStudyPortRotation lsPr, Pageable pageable);
}
