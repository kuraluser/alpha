/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import java.util.List;
import java.util.Optional;

/** Repository interface for synopticalTable entity */
public interface SynopticalTableRepository extends CommonCrudRepository<SynopticalTable, Long> {

  public Optional<SynopticalTable> findByIdAndIsActive(Long id, Boolean isActive);

  public List<SynopticalTable> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);
}
