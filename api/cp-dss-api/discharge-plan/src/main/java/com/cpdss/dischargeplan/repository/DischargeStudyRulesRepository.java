/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargeStudyRules;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface DischargeStudyRulesRepository
    extends CommonCrudRepository<DischargeStudyRules, Long> {

  @Query(
      "FROM DischargeStudyRules DSR WHERE DSR.dischargeInformation.id = ?1 AND DSR.isActive = ?2 AND DSR.parentRuleXId = ?3")
  Optional<DischargeStudyRules> checkIsRuleTemplateExist(
      Long dischargeStudyId, Boolean isActive, Long ruleTemplateId);

  List<DischargeStudyRules>
      findByDischargeInformationAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
          DischargeInformation dischargeInformation,
          long vesselId,
          boolean isActive,
          List<Long> ruleListId);
}
