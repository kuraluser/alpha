/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInstructionHeader;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInstructionHeaderRepository extends CommonCrudRepository<LoadingInstructionHeader, Long> {

	@Query(value = "SELECT * FROM loading_instructions_header where is_active=true", nativeQuery = true)
	public List<LoadingInstructionHeader> getAllLoadingInstructionHeader();
	
	public List<LoadingInstructionHeader> findAllByIsActiveTrue();

}
