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
  
  @Query("From LoadingInstructionTemplate LIT where LIT.loadingInsructionType.id = :var1"
  		+ " AND LIT.referenceXId = :var2 AND LIT.isActive = true")
  public List<LoadingInstructionTemplate> findALLByLoadingInsructionTypeIdAndReferenceId(Long var1,Long var2);
  

}
