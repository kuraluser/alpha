/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails;
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
      var2.setAbbreviation(var2.getAbbreviation());
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
      var2.setFreeBoard(var1.getFreeboard());
      var2.setManifoldHeight(var1.getManifoldHeight());
      var2.setConditionType(var1.getConditionType());
      var2.setValueType(var1.getValueType());
      response.add(var2);
    }
    return response;
  }

  /**
   * @param portLoadingPlanCommingleDetailsList
   * @return
   */
  public List<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails>
      buildLoadingPlanCommingleFromRpc(
          List<LoadingPlanCommingleDetails> portLoadingPlanCommingleDetailsList) {
    List<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails> response =
        new ArrayList<com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails>();
    for (LoadingPlanCommingleDetails commingle : portLoadingPlanCommingleDetailsList) {
      com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails commingleDetails =
          new com.cpdss.gateway.domain.loadingplan.LoadingPlanCommingleDetails();
      commingleDetails.setAbbreviation(commingle.getAbbreviation());
      commingleDetails.setApi(
          StringUtils.isEmpty(commingle.getApi()) ? null : new BigDecimal(commingle.getApi()));
      commingleDetails.setCargo1Id(commingle.getCargo1Id());
      commingleDetails.setCargo2Id(commingle.getCargo2Id());
      commingleDetails.setCargoNomination1Id(commingle.getCargoNomination1Id());
      commingleDetails.setCargoNomination2Id(commingle.getCargoNomination2Id());
      commingleDetails.setId(commingle.getId());
      commingleDetails.setQuantityM3(
          StringUtils.isEmpty(commingle.getQuantityM3())
              ? null
              : new BigDecimal(commingle.getQuantityM3()));
      commingleDetails.setQuantityMT(
          StringUtils.isEmpty(commingle.getQuantityMT())
              ? null
              : new BigDecimal(commingle.getQuantityMT()));
      commingleDetails.setTankId(commingle.getTankId());
      commingleDetails.setTemperature(
          StringUtils.isEmpty(commingle.getTemperature())
              ? null
              : new BigDecimal(commingle.getTemperature()));
      commingleDetails.setUllage(
          StringUtils.isEmpty(commingle.getUllage())
              ? null
              : new BigDecimal(commingle.getUllage()));
      commingleDetails.setQuantity1MT(
          StringUtils.isEmpty(commingle.getQuantity1MT())
              ? null
              : new BigDecimal(commingle.getQuantity1MT()));

      commingleDetails.setQuantity2MT(
          StringUtils.isEmpty(commingle.getQuantity2MT())
              ? null
              : new BigDecimal(commingle.getQuantity2MT()));

      commingleDetails.setQuantity1M3(
          StringUtils.isEmpty(commingle.getQuantity1M3())
              ? null
              : new BigDecimal(commingle.getQuantity1M3()));

      commingleDetails.setQuantity2M3(
          StringUtils.isEmpty(commingle.getQuantity2M3())
              ? null
              : new BigDecimal(commingle.getQuantity2M3()));

      commingleDetails.setUllage1(
          StringUtils.isEmpty(commingle.getUllage1())
              ? null
              : new BigDecimal(commingle.getUllage1()));
      commingleDetails.setUllage2(
          StringUtils.isEmpty(commingle.getUllage2())
              ? null
              : new BigDecimal(commingle.getUllage2()));
      commingleDetails.setConditionType(commingle.getConditionType());
      commingleDetails.setValueType(commingle.getValueType());
      commingleDetails.setColorCode(commingle.getColorCode());
      response.add(commingleDetails);
    }
    return response;
  }
}
