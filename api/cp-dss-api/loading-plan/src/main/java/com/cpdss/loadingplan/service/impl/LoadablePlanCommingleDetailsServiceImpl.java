/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails;
import com.cpdss.loadingplan.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadablePlanCommingleDetailsRepository;
import com.cpdss.loadingplan.service.LoadablePlanCommingleDetailsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadablePlanCommingleDetailsServiceImpl
    implements LoadablePlanCommingleDetailsService {

  @Autowired LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @Override
  public void saveLoadablePlanCommingleDetailsList(
      List<LoadableQuantityCommingleCargoDetails> commingleDetailsList,
      LoadingInformation loadingInformation) {
    commingleDetailsList.stream()
        .forEach(
            loadablePlanCommingleDetail -> {
              LoadablePlanCommingleDetails loadablePlanCommingleDetails =
                  new LoadablePlanCommingleDetails();
              Optional.ofNullable(loadablePlanCommingleDetail.getApi())
                  .ifPresent(loadablePlanCommingleDetails::setApi);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Abbreviation())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Abbreviation);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Bbls60F())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Bbls60f);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Bblsdbs())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1BblsDbs);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1KL())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Kl);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1LT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Lt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1MT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Mt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Percentage())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Percentage);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Abbreviation())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Abbreviation);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Bbls60F())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Bbls60f);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Bblsdbs())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2BblsDbs);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2KL())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Kl);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2LT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Lt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2MT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Mt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Percentage())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Percentage);
              Optional.ofNullable(loadablePlanCommingleDetail.getCorrectedUllage())
                  .ifPresent(loadablePlanCommingleDetails::setCorrectedUllage);
              Optional.ofNullable(loadablePlanCommingleDetail.getCorrectionFactor())
                  .ifPresent(loadablePlanCommingleDetails::setCorrectionFactor);
              Optional.ofNullable(loadablePlanCommingleDetail.getFillingRatio())
                  .ifPresent(loadablePlanCommingleDetails::setFillingRatio);
              Optional.ofNullable(loadablePlanCommingleDetail.getGrade())
                  .ifPresent(loadablePlanCommingleDetails::setGrade);
              Optional.ofNullable(loadablePlanCommingleDetail.getId())
                  .ifPresent(loadablePlanCommingleDetails::setId);
              Optional.ofNullable(loadablePlanCommingleDetail.getLoadingOrder())
                  .ifPresent(loadablePlanCommingleDetails::setLoadingOrder);
              Optional.ofNullable(loadablePlanCommingleDetail.getOrderedMT())
                  .ifPresent(loadablePlanCommingleDetails::setOrderQuantity);
              Optional.ofNullable(loadablePlanCommingleDetail.getPriority())
                  .ifPresent(loadablePlanCommingleDetails::setPriority);
              Optional.ofNullable(loadablePlanCommingleDetail.getQuantity())
                  .ifPresent(loadablePlanCommingleDetails::setQuantity);
              Optional.ofNullable(loadablePlanCommingleDetail.getRdgUllage())
                  .ifPresent(loadablePlanCommingleDetails::setRdgUllage);
              Optional.ofNullable(loadablePlanCommingleDetail.getSlopQuantity())
                  .ifPresent(loadablePlanCommingleDetails::setSlopQuantity);
              Optional.ofNullable(loadablePlanCommingleDetail.getTankId())
                  .ifPresent(loadablePlanCommingleDetails::setTankId);
              Optional.ofNullable(loadablePlanCommingleDetail.getTankName())
                  .ifPresent(loadablePlanCommingleDetails::setTankName);
              Optional.ofNullable(loadablePlanCommingleDetail.getTemp())
                  .ifPresent(loadablePlanCommingleDetails::setTemperature);
              Optional.ofNullable(loadablePlanCommingleDetail.getTimeRequiredForLoading())
                  .ifPresent(loadablePlanCommingleDetails::setTimeRequiredForLoading);
              loadablePlanCommingleDetails.setLoadablePatternXId(
                  loadingInformation.getLoadablePatternXId());
              loadablePlanCommingleDetails.setLoadingInformation(loadingInformation);
              loadablePlanCommingleDetails.setIsActive(true);
              loadablePlanCommingleDetailsRepository.save(loadablePlanCommingleDetails);
            });
  }
}
