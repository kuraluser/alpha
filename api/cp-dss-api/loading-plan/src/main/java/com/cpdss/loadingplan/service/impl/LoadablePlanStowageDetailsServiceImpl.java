/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanStowageDetailsRepository;
import com.cpdss.loadingplan.service.LoadablePlanStowageDetailsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadablePlanStowageDetailsServiceImpl implements LoadablePlanStowageDetailsService {

  @Autowired LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;

  @Override
  public void saveLoadablePlanStowageDetailsList(
      List<LoadablePlanStowageDetails> stowageDetailsList, LoadingInformation loadingInformation) {
    stowageDetailsList.stream()
        .forEach(
            loadablePlanStowageDetail -> {
              com.cpdss.loadingplan.entity.LoadablePlanStowageDetails loadablePlanStowageDetails =
                  new com.cpdss.loadingplan.entity.LoadablePlanStowageDetails();
              Optional.ofNullable(loadablePlanStowageDetail.getApi())
                  .ifPresent(loadablePlanStowageDetails::setApi);
              Optional.ofNullable(loadablePlanStowageDetail.getCargoAbbreviation())
                  .ifPresent(loadablePlanStowageDetails::setAbbreviation);
              Optional.ofNullable(loadablePlanStowageDetail.getCargoNominationId())
                  .ifPresent(loadablePlanStowageDetails::setCargoNominationId);
              Optional.ofNullable(loadablePlanStowageDetail.getColorCode())
                  .ifPresent(loadablePlanStowageDetails::setColorCode);
              Optional.ofNullable(loadablePlanStowageDetail.getCorrectedUllage())
                  .ifPresent(loadablePlanStowageDetails::setCorrectedUllage);
              Optional.ofNullable(loadablePlanStowageDetail.getCorrectionFactor())
                  .ifPresent(loadablePlanStowageDetails::setCorrectionFactor);
              Optional.ofNullable(loadablePlanStowageDetail.getFillingRatio())
                  .ifPresent(loadablePlanStowageDetails::setFillingPercentage);
              Optional.ofNullable(loadablePlanStowageDetail.getId())
                  .ifPresent(loadablePlanStowageDetails::setId);
              Optional.ofNullable(loadablePlanStowageDetail.getObservedBarrels())
                  .ifPresent(loadablePlanStowageDetails::setObservedBarrels);
              Optional.ofNullable(loadablePlanStowageDetail.getObservedBarrelsAt60())
                  .ifPresent(loadablePlanStowageDetails::setObservedBarrelsAt60);
              Optional.ofNullable(loadablePlanStowageDetail.getObservedM3())
                  .ifPresent(loadablePlanStowageDetails::setObservedM3);
              Optional.ofNullable(loadablePlanStowageDetail.getRdgUllage())
                  .ifPresent(loadablePlanStowageDetails::setRdgUllage);
              Optional.ofNullable(loadablePlanStowageDetail.getTankId())
                  .ifPresent(loadablePlanStowageDetails::setTankId);
              Optional.ofNullable(loadablePlanStowageDetail.getTankName())
                  .ifPresent(loadablePlanStowageDetails::setTankname);
              Optional.ofNullable(loadablePlanStowageDetail.getTemperature())
                  .ifPresent(loadablePlanStowageDetails::setTemperature);
              Optional.ofNullable(loadablePlanStowageDetail.getWeight())
                  .ifPresent(loadablePlanStowageDetails::setWeight);
              loadablePlanStowageDetails.setLoadablePatternXId(
                  loadingInformation.getLoadablePatternXId());
              loadablePlanStowageDetails.setLoadingInformation(loadingInformation);
              loadablePlanStowageDetails.setIsActive(true);
              loadablePlanStowageDetailsRepository.save(loadablePlanStowageDetails);
            });
  }
}
