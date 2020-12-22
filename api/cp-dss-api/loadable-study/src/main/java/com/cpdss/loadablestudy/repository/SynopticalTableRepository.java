/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/** Repository interface for synopticalTable entity */
public interface SynopticalTableRepository extends CommonCrudRepository<SynopticalTable, Long> {

  public Optional<SynopticalTable> findByIdAndIsActive(Long id, Boolean isActive);

  @Query(
      "select s from SynopticalTable s "
          + "left outer join LoadableStudyPortRotation lp "
          + "on s.loadableStudyXId=lp.loadableStudy.id and s.portXid=lp.portXId "
          + "where s.loadableStudyXId=?1 and s.isActive=true and lp.isActive=true "
          + "order by lp.portOrder, s.operationType")
  public List<SynopticalTable> findByLoadableStudyXIdAndIsActive(Long loadableStudyXId);
}
