/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import java.util.List;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CommingleCargo;

public interface CommingleCargoRepository
extends CommonCrudRepository<CommingleCargo, Long> {

	public List<CommingleCargo> findByLoadableStudyXIdAndIsActive(
			Long loadableStudyXId, Boolean isActive);
}
