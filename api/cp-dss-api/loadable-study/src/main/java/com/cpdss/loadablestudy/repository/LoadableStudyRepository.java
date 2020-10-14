package com.cpdss.loadablestudy.repository;

import java.util.List;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.Voyage;

/**
 * Repository interface for loadbale study entity {@link LoadableStudy}
 *
 * @author suhail.k
 */
public interface LoadableStudyRepository extends CommonCrudRepository<LoadableStudy, Long> {

  public List<LoadableStudy> findByVesselXIdAndVoyage(final Long vesselXId, final Voyage voyage);
}
