/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInstruction;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInstructionRepository extends CommonCrudRepository<LoadingInstruction, Long> {

	@Query(value = "select * from loading_instructions LI "
			+ " join loading_information LIF on LI.loading_xid = LIF.id"
			+ " where LIF.vessel_xid=:vesselId and LI.loading_xid= :loadingInfoId and LIF.port_rotation_xid =:portRotationId "
			+ "and LI.is_active = true and LIF.is_active =true", nativeQuery = true)
	public List<LoadingInstruction> getAllLoadingInstructions(long vesselId, long loadingInfoId, long portRotationId);

	@Query(value = "SELECT CASE WHEN count(*)>0 then true else false END FROM LoadingInstruction "
			+ " where loading_xid = ?1 and is_active = true")
	public Boolean findAny(Long loadingInfoId);

	@Modifying
	@Query("update LoadingInstruction set isActive = false where id=?1")
	public void deleteInstruction(long instructionId);

	
	@Modifying
	@Query("update LoadingInstruction set isChecked = ?2 where id=?1")
	public void updateInstructionStatus(long instructionId, boolean isChecked);
}
