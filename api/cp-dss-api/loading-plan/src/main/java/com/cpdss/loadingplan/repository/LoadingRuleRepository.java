/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingRule;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoadingRuleRepository extends CommonCrudRepository<LoadingRule, Long> {

  List<LoadingRule> findByLoadingXidAndVesselXidAndIsActiveTrueAndVesselRuleXidInOrderById(
      Long loadingInformationId, Long vesselId, List<Long> vesselRuleXid);

  @Modifying
  @Query("UPDATE LoadingRule SET displayInSettings = (:var1) WHERE vesselRuleXid IN (:var2)")
  void updateDisplayInSettingsInLoadingRules(
      @Param("var1") Boolean var1, @Param("var2") Set<Long> var2);

  @Query(
      "FROM LoadingRule LR WHERE LR.loadingXid = ?1 AND LR.isActive = ?2 AND LR.parentRuleXid = ?3")
  Optional<LoadingRule> checkIsLoadingRuleExist(Long id, boolean isActive, Long parentRuleId);
}
