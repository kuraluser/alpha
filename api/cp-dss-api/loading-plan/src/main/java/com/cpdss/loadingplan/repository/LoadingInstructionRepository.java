/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInstruction;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInstructionRepository extends CommonCrudRepository<LoadingInstruction, Long> {

	@Query(value = "select LIT.id,LIT.type_name,LI.reference_xid,LH.id,LH.header_name,"
			+ " LI.loading_instruction,LI.parent_instruction_xid,"
			+ " LI.is_checked from loading_instructions LI "
			+ " join loading_instructions_header LH on LI.loading_instruction_header_xid = LH.id"
			+ " join loading_instructions_type LIT ON LIT.id = LI.loading_type_xid"
			+ " join loading_information LIF on LI.loading_xid = LIF.id"
			+ " where LIF.vessel_xid=?1 and LI.loading_xid= ?2 and LIF.port_rotation_xid =?3 "
			+ "and LI.is_active = true"	, nativeQuery = true) 
	public List<LoadingInstruction> getAllLoadingInstructions(long vesselId, long loadingInfoId, long portRotationId);

	
	@Query(value ="SELECT CASE WHEN count(*)>0 then true else false END FROM LoadingInstruction "
			+ " where loading_xid = ?1 and is_active = true")
	public Boolean findAny(Long loadingInfoId);

}
