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
      + "left outer join LoadableStudyPortRotationService lp "
      + "on s.loadableStudyXId=lp.loadableStudy.id and s.portXid=lp.portXId "
      + "where s.loadableStudyXId=?1 and s.isActive=true and lp.isActive=true "
      + "order by lp.portOrder, s.operationType")*/
  public List<SynopticalTable> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, boolean isActive);

  public List<SynopticalTable> findByLoadableStudyXIdAndLoadableStudyPortRotation_idAndIsActive(
      Long loadableStudyXId, Long portRotationId, boolean isActive);

  @Query(
      "SELECT ST FROM SynopticalTable ST WHERE ST.loadableStudyXId = ?1 AND ST.isActive = ?2 ORDER BY ST.loadableStudyPortRotation.portOrder, ST.operationType")
  public List<SynopticalTable> findByLoadableStudyXIdAndIsActiveOrderByPortOrder(
      Long loadableStudyXId, boolean isActive);

  @Query(
      "SELECT ST FROM SynopticalTable ST WHERE ST.loadableStudyXId = ?1 AND ST.isActive = ?2 ORDER BY ST.loadableStudyPortRotation.operation.id, ST.loadableStudyPortRotation.portOrder, ST.operationType")
  public List<SynopticalTable> findByLoadableStudyXIdAndIsActiveOrderByOperationAndPortOrder(
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

  @Query(
      "FROM SynopticalTable WHERE loadableStudyXId = ?1 AND loadableStudyPortRotation.id = ?2 AND operationType = ?3 AND isActive = ?4")
  public Optional<SynopticalTable> findByLoadableStudyAndPortRotationAndOperationTypeAndIsActive(
      Long loadableStudyXId, Long portRotationId, String operationType, Boolean isActive);

  @Query(
      "FROM SynopticalTable WHERE loadableStudyPortRotation.id = ?1 AND operationType = ?2 AND isActive = ?3")
  public Optional<SynopticalTable> findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
      Long portRotationId, String operationType, Boolean isActive);

  @Modifying
  @Query("Update SynopticalTable set isActive = false where loadableStudyPortRotation.id = ?1")
  public void deleteByPortRotationId(Long id);

  @Query(
      "from SynopticalTable st where st.loadableStudyPortRotation.id = ?1 and st.isActive = true")
  List<SynopticalTable> findAllByPortRotationId(Long id);

  @Query("FROM SynopticalTable ST WHERE ST.loadableStudyPortRotation in ?1 AND ST.isActive = ?2")
  public List<SynopticalTable> findByLoadableStudyPortRotationAndIsActive(
      List<LoadableStudyPortRotation> loadableStudyPortRotations, Boolean isActive);
}
