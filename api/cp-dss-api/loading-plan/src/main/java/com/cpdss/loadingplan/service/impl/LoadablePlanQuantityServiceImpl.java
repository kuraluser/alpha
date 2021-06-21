/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadingplan.service.LoadablePlanQuantityService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoadablePlanQuantityServiceImpl implements LoadablePlanQuantityService {

  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;

  @Override
  public void saveLoadablePlanQuantyList(
      List<LoadableQuantityCargoDetails> cargoDetailsList, LoadingInformation loadingInformation) {
    cargoDetailsList.stream()
        .forEach(
            planQuantity -> {
              com.cpdss.loadingplan.entity.LoadablePlanQuantity loadablePlanQuantity =
                  new com.cpdss.loadingplan.entity.LoadablePlanQuantity();
              Optional.ofNullable(planQuantity.getCargoAbbreviation())
                  .ifPresent(loadablePlanQuantity::setCargoAbbreviation);
              Optional.ofNullable(planQuantity.getCargoId())
                  .ifPresent(loadablePlanQuantity::setCargoXId);
              Optional.ofNullable(planQuantity.getCargoNominationId())
                  .ifPresent(loadablePlanQuantity::setCargoNominationId);
              Optional.ofNullable(planQuantity.getColorCode())
                  .ifPresent(loadablePlanQuantity::setCargoColor);
              Optional.ofNullable(planQuantity.getDifferenceColor())
                  .ifPresent(loadablePlanQuantity::setDifferenceColor);
              Optional.ofNullable(planQuantity.getDifferencePercentage())
                  .ifPresent(loadablePlanQuantity::setDifferencePercentage);
              loadablePlanQuantity.setEstimatedApi(
                  StringUtils.isEmpty(planQuantity.getEstimatedAPI())
                      ? null
                      : new BigDecimal(planQuantity.getEstimatedAPI()));
              loadablePlanQuantity.setEstimatedTemperature(
                  StringUtils.isEmpty(planQuantity.getEstimatedTemp())
                      ? null
                      : new BigDecimal(planQuantity.getEstimatedTemp()));
              Optional.ofNullable(planQuantity.getGrade())
                  .ifPresent(loadablePlanQuantity::setGrade);
              Optional.ofNullable(planQuantity.getId()).ifPresent(loadablePlanQuantity::setId);
              Optional.ofNullable(planQuantity.getLoadableBbls60F())
                  .ifPresent(loadablePlanQuantity::setLoadableBbls60f);
              Optional.ofNullable(planQuantity.getLoadableBblsdbs())
                  .ifPresent(loadablePlanQuantity::setLoadableBblsDbs);
              Optional.ofNullable(planQuantity.getLoadableKL())
                  .ifPresent(loadablePlanQuantity::setLoadableKl);
              Optional.ofNullable(planQuantity.getLoadableLT())
                  .ifPresent(loadablePlanQuantity::setLoadableLt);
              Optional.ofNullable(planQuantity.getLoadableMT())
                  .ifPresent(loadablePlanQuantity::setLoadableMt);
              Optional.ofNullable(planQuantity.getLoadingOrder())
                  .ifPresent(loadablePlanQuantity::setLoadingOrder);
              Optional.ofNullable(planQuantity.getMaxTolerence())
                  .ifPresent(loadablePlanQuantity::setMaxTolerence);
              Optional.ofNullable(planQuantity.getMinTolerence())
                  .ifPresent(loadablePlanQuantity::setMinTolerence);
              Optional.ofNullable(planQuantity.getOrderBbls60F())
                  .ifPresent(loadablePlanQuantity::setOrderBbls60f);
              Optional.ofNullable(planQuantity.getOrderBblsdbs())
                  .ifPresent(loadablePlanQuantity::setOrderBblsDbs);
              loadablePlanQuantity.setOrderQuantity(
                  StringUtils.isEmpty(planQuantity.getOrderedMT())
                      ? null
                      : new BigDecimal(planQuantity.getOrderedMT()));
              Optional.ofNullable(planQuantity.getPriority())
                  .ifPresent(loadablePlanQuantity::setPriority);
              Optional.ofNullable(planQuantity.getSlopQuantity())
                  .ifPresent(loadablePlanQuantity::setSlopQuantity);
              Optional.ofNullable(planQuantity.getTimeRequiredForLoading())
                  .ifPresent(loadablePlanQuantity::setTimeRequiredForLoading);
              loadablePlanQuantity.setLoadablePattern(loadingInformation.getLoadablePatternXId());
              loadablePlanQuantity.setLoadingInformation(loadingInformation);
              loadablePlanQuantity.setIsActive(true);
              loadablePlanQuantityRepository.save(loadablePlanQuantity);
            });
  }
}
