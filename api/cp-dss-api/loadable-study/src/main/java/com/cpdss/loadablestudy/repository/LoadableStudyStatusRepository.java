/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadableStudyStatus;

/**
 * Loadable study status repository
 *
 * @author suhail.k
 */
public interface LoadableStudyStatusRepository
    extends CommonCrudRepository<LoadableStudyStatus, Long> {}
