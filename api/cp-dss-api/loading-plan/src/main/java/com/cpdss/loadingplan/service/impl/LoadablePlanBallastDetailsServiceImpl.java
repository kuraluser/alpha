/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanBallastDetailsRepository;
import com.cpdss.loadingplan.service.LoadablePlanBallastDetailsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadablePlanBallastDetailsServiceImpl implements LoadablePlanBallastDetailsService {

  @Autowired LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @Override
  public void saveLoadablePlanBallastDetailsList(
      List<LoadablePlanBallastDetails> ballastDetailsList, LoadingInformation loadingInformation) {
    ballastDetailsList.stream()
        .forEach(
            loadablePlanBallastDetail -> {
              com.cpdss.loadingplan.entity.LoadablePlanBallastDetails loadablePlanBallastDetails =
                  new com.cpdss.loadingplan.entity.LoadablePlanBallastDetails();
              Optional.ofNullable(loadablePlanBallastDetail.getColorCode())
                  .ifPresent(loadablePlanBallastDetails::setColorCode);
              Optional.ofNullable(loadablePlanBallastDetail.getCorrectedLevel())
                  .ifPresent(loadablePlanBallastDetails::setCorrectedLevel);
              Optional.ofNullable(loadablePlanBallastDetail.getCorrectionFactor())
                  .ifPresent(loadablePlanBallastDetails::setCorrectionFactor);
              Optional.ofNullable(loadablePlanBallastDetail.getCubicMeter())
                  .ifPresent(loadablePlanBallastDetails::setCubicMeter);
              Optional.ofNullable(loadablePlanBallastDetail.getId())
                  .ifPresent(loadablePlanBallastDetails::setId);
              Optional.ofNullable(loadablePlanBallastDetail.getInertia())
                  .ifPresent(loadablePlanBallastDetails::setInertia);
              Optional.ofNullable(loadablePlanBallastDetail.getLcg())
                  .ifPresent(loadablePlanBallastDetails::setLcg);
              Optional.ofNullable(loadablePlanBallastDetail.getMetricTon())
                  .ifPresent(loadablePlanBallastDetails::setMetricTon);
              Optional.ofNullable(loadablePlanBallastDetail.getPercentage())
                  .ifPresent(loadablePlanBallastDetails::setPercentage);
              Optional.ofNullable(loadablePlanBallastDetail.getRdgLevel())
                  .ifPresent(loadablePlanBallastDetails::setRdgLevel);
              Optional.ofNullable(loadablePlanBallastDetail.getSg())
                  .ifPresent(loadablePlanBallastDetails::setSg);
              Optional.ofNullable(loadablePlanBallastDetail.getTankId())
                  .ifPresent(loadablePlanBallastDetails::setTankId);
              Optional.ofNullable(loadablePlanBallastDetail.getTankName())
                  .ifPresent(loadablePlanBallastDetails::setTankName);
              Optional.ofNullable(loadablePlanBallastDetail.getTcg())
                  .ifPresent(loadablePlanBallastDetails::setTcg);
              Optional.ofNullable(loadablePlanBallastDetail.getVcg())
                  .ifPresent(loadablePlanBallastDetails::setVcg);
              loadablePlanBallastDetails.setLoadingInformation(loadingInformation);
              loadablePlanBallastDetails.setIsActive(true);
              loadablePlanBallastDetailsRepository.save(loadablePlanBallastDetails);
            });
  }
}
