/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.CargoToppingOffSequenceRepository;
import com.cpdss.loadingplan.service.CargoToppingOffSequenceService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CargoToppingOffSequenceServiceImpl implements CargoToppingOffSequenceService {

  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;

  @Override
  public void saveCargoToppingOffSequenceList(
      List<CargoToppingOffSequence> cargoToppingOffSequencesList,
      LoadingInformation loadingInformation) {
    cargoToppingOffSequencesList.stream()
        .forEach(
            cargoToppingOff -> {
              com.cpdss.loadingplan.entity.CargoToppingOffSequence cargoToppingOffSequence =
                  new com.cpdss.loadingplan.entity.CargoToppingOffSequence();
              Optional.ofNullable(cargoToppingOff.getCargoXId())
                  .ifPresent(cargoToppingOffSequence::setCargoXId);
              cargoToppingOffSequence.setFillingRatio(
                  StringUtils.isEmpty(cargoToppingOff.getFillingRatio())
                      ? null
                      : new BigDecimal(cargoToppingOff.getFillingRatio()));
              Optional.ofNullable(cargoToppingOff.getOrderNumber())
                  .ifPresent(cargoToppingOffSequence::setOrderNumber);
              Optional.ofNullable(cargoToppingOff.getRemarks())
                  .ifPresent(cargoToppingOffSequence::setRemarks);
              Optional.ofNullable(cargoToppingOff.getTankXId())
                  .ifPresent(cargoToppingOffSequence::setTankXId);
              cargoToppingOffSequence.setUllage(
                  StringUtils.isEmpty(cargoToppingOff.getUllage())
                      ? null
                      : new BigDecimal(cargoToppingOff.getUllage()));
              // volume, weight remove and quantity added
              /* cargoToppingOffSequence.setVolume(
                  StringUtils.isEmpty(cargoToppingOff.getVolume())
                      ? null
                      : new BigDecimal(cargoToppingOff.getVolume()));
              cargoToppingOffSequence.setWeight(
                  StringUtils.isEmpty(cargoToppingOff.getWeight())
                      ? null
                      : new BigDecimal(cargoToppingOff.getWeight()));*/
              cargoToppingOffSequence.setLoadingInformation(loadingInformation);
              cargoToppingOffSequence.setIsActive(true);
              cargoToppingOffSequenceRepository.save(cargoToppingOffSequence);
            });
  }
}
