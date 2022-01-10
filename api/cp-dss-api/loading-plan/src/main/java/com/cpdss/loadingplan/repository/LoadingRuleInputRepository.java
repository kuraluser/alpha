/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingRule;
import com.cpdss.loadingplan.entity.LoadingRuleInput;
import java.util.List;

public interface LoadingRuleInputRepository extends CommonCrudRepository<LoadingRuleInput, Long> {

  List<LoadingRuleInput> findAllByLoadingRule(LoadingRule loadingRule);
}
