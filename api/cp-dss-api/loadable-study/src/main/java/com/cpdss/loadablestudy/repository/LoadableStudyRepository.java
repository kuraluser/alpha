/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for loadable study entity {@link LoadableStudy}
 *
 * @author suhail.k
 */
public interface LoadableStudyRepository extends CommonCrudRepository<LoadableStudy, Long> {

  public List<LoadableStudy> findByVesselXIdAndVoyageAndIsActive(
      final Long vesselXId, final Voyage voyage, final boolean isActive);

  public Optional<LoadableStudy> findByIdAndIsActive(Long id, Boolean isActive);
}