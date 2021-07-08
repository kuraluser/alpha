/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInstruction;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInstructionRepository extends CommonCrudRepository<LoadingInstruction, Long> {

	@Query("select query") // TODO
	public List<LoadingInstruction> getAllInstructionsforVessel(long vesselId, long loadingInfoId, long portRotationId);
}
