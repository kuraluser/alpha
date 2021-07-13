/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInstruction;
import com.cpdss.loadingplan.entity.LoadingInstructionTemplate;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInstructionTemplateRepository extends CommonCrudRepository<LoadingInstructionTemplate, Long> {

	@Query(value = "SELECT loading_insruction_typexid,loading_instruction,loading_instruction_header_xid,"
			+ " reference_xid,is_active,parent_instruction_xid FROM loading_instructions_template where"
			+ " (loading_insruction_typexid = 1 AND reference_xid = ?1) "
			+ " or (loading_insruction_typexid = 2 and reference_xid = ?1)", nativeQuery = true)
	public List<LoadingInstructionTemplate> getCommonInstructionTemplate(Long portXId);

}
