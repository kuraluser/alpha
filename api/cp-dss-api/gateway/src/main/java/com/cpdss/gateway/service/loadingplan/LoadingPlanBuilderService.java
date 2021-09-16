/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest.Builder;
import com.cpdss.gateway.domain.loadingplan.LoadingPlanStabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoadingPlanBuilderService {

  public void buildLoadingPlanSaveRequest(
      LoadingPlanAlgoRequest loadingPlanAlgoRequest, Builder builder) {}

  public List<LoadingPlanStowageDetails> buildLoadingPlanStowageFromRpc(
      List<LoadingPlanModels.LoadingPlanTankDetails> list) {
    List<LoadingPlanStowageDetails> response = new ArrayList<>();
    for (LoadingPlanModels.LoadingPlanTankDetails var1 : list) {
      LoadingPlanStowageDetails var2 = new LoadingPlanStowageDetails();
      var2.setApi(var1.getApi());
      var2.setTemperature(var1.getTemperature());
      var2.setQuantityM3(var1.getQuantityM3());
      var2.setCargoNominationId(var1.getCargoNominationId());
      var2.setTankId(var1.getTankId());
      // tank name not added
      var2.setQuantityMT(var1.getQuantity());
      var2.setConditionType(var1.getConditionType());
      var2.setValueType(var1.getValueType());
      var2.setUllage(var1.getUllage());
      var2.setAbbreviation(var1.getAbbreviation());
      var2.setCargoId(var1.getCargoId());
      var2.setColorCode(var1.getColorCode());
      response.add(var2);
    }
    return response;
  }

  public List<LoadingPlanBallastDetails> buildLoadingPlanBallastFromRpc(
      List<LoadingPlanModels.LoadingPlanTankDetails> list) {
    List<LoadingPlanBallastDetails> response = new ArrayList<>();
    for (LoadingPlanModels.LoadingPlanTankDetails var1 : list) {
      LoadingPlanBallastDetails var2 = new LoadingPlanBallastDetails();
      var2.setTankId(var1.getTankId());
      // tank name not added
      var2.setQuantityMT(var1.getQuantity());
      var2.setQuantityM3(var1.getQuantityM3());
      var2.setSounding(var1.getSounding());
      var2.setConditionType(var1.getConditionType());
      var2.setValueType(var1.getValueType());
      var2.setColorCode(var1.getColorCode());
      var2.setSg(var1.getSg());
      response.add(var2);
    }
    return response;
  }

  public List<LoadingPlanRobDetails> buildLoadingPlanRobFromRpc(
      List<LoadingPlanModels.LoadingPlanTankDetails> list) {
    List<LoadingPlanRobDetails> response = new ArrayList<>();
    for (LoadingPlanModels.LoadingPlanTankDetails var1 : list) {
      LoadingPlanRobDetails var2 = new LoadingPlanRobDetails();
      var2.setTankId(var1.getTankId());
      // tank name not added
      var2.setQuantityMT(var1.getQuantity());
      var2.setQuantityM3(var1.getQuantityM3());
      var2.setConditionType(var1.getConditionType());
      var2.setValueType(var1.getValueType());
      var2.setColorCode(var1.getColorCode());
      var2.setDensity(
          StringUtils.isEmpty(var1.getDensity()) ? null : new BigDecimal(var1.getDensity()));
      response.add(var2);
    }
    return response;
  }

  public List<LoadingPlanStabilityParam> buildLoadingPlanStabilityParamFromRpc(
      List<LoadingPlanModels.LoadingPlanStabilityParameters> list) {
    List<LoadingPlanStabilityParam> response = new ArrayList<>();
    for (LoadingPlanModels.LoadingPlanStabilityParameters var1 : list) {
      LoadingPlanStabilityParam var2 = new LoadingPlanStabilityParam();
      var2.setBm(var1.getBm());
      var2.setSf(var1.getSf());
      var2.setForeDraft(var1.getForeDraft());
      var2.setMeanDraft(var1.getMeanDraft());
      var2.setAftDraft(var1.getAftDraft());
      var2.setTrim(var1.getTrim());
      var2.setConditionType(var1.getConditionType());
      var2.setValueType(var1.getValueType());
      response.add(var2);
    }
    return response;
  }
}
