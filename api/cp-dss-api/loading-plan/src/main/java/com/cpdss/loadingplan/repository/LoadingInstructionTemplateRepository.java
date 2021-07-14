/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInstructionTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingInstructionTemplateRepository
    extends CommonCrudRepository<LoadingInstructionTemplate, Long> {

//  @Query(
//      value =
//          "SELECT * FROM loading_instructions_template where"
//              + " (loading_insruction_typexid = 1 AND reference_xid = :portXId) "
//              + " or (loading_insruction_typexid = 2 and reference_xid = :portXId)",nativeQuery = true)
//  public List<LoadingInstructionTemplate> getCommonInstructionTemplate(Long portXId);
  
  @Query("From LoadingInstructionTemplate LIT where LIT.loadingInsructionType.id = :var1 AND LIT.referenceXId = :var2")
  public List<LoadingInstructionTemplate> findALLByLoadingInsructionTypeIdAndReferenceId(Long var1,Long var2);
  

}
