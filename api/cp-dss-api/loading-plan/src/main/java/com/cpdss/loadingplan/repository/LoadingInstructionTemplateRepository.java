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

  @Query(
      "From LoadingInstructionTemplate LIT where LIT.loadingInsructionType.id = ?1"
          + " AND LIT.referenceXId = ?2 OR LIT.loadingInsructionType.id = ?3 AND LIT.isActive = true")
  public List<LoadingInstructionTemplate> findALLByLoadingInsructionTypeIdAndReferenceId(
      Long var1, Long var2, Long var3);
}
