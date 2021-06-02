/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingToppingOff;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.CargoToppingOffSequenceRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.service.CargoToppingOffSequenceService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class CargoToppingOffSequenceServiceImpl implements CargoToppingOffSequenceService {

  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired LoadingInformationRepository loadingInformationRepository;

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

  @Override
  public void saveCargoToppingOffSequences(List<LoadingToppingOff> toppingOffSequenceList)
      throws Exception {
    for (LoadingToppingOff toppingOff : toppingOffSequenceList) {
      com.cpdss.loadingplan.entity.CargoToppingOffSequence cargoToppingOff = null;
      if (toppingOff.getId() == 0) {
        cargoToppingOff = new com.cpdss.loadingplan.entity.CargoToppingOffSequence();
      } else {
        Optional<com.cpdss.loadingplan.entity.CargoToppingOffSequence> cargoToppingOffOpt =
            cargoToppingOffSequenceRepository.findByIdAndIsActiveTrue(toppingOff.getId());
        if (cargoToppingOffOpt.isPresent()) {
          cargoToppingOff = cargoToppingOffOpt.get();
        } else {
          throw new Exception("Cannot find the cargo topping off with id " + toppingOff.getId());
        }
      }

      buildCargoToppingOffSequence(toppingOff, cargoToppingOff);
      cargoToppingOffSequenceRepository.save(cargoToppingOff);
    }
  }

  private void buildCargoToppingOffSequence(
      LoadingToppingOff toppingOff,
      com.cpdss.loadingplan.entity.CargoToppingOffSequence cargoToppingOff)
      throws Exception {
    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(toppingOff.getLoadingInfoId());
    if (loadingInformationOpt.isPresent()) {
      cargoToppingOff.setLoadingInformation(loadingInformationOpt.get());
    } else {
      throw new Exception(
          "Cannot find loadable study with id "
              + toppingOff.getLoadingInfoId()
              + " for cargo topping off sequence "
              + toppingOff.getId());
    }
    Optional.ofNullable(toppingOff.getCargoId()).ifPresent(cargoToppingOff::setCargoXId);
    cargoToppingOff.setFillingRatio(
        StringUtils.isEmpty(toppingOff.getFillingRatio())
            ? null
            : new BigDecimal(toppingOff.getFillingRatio()));
    Optional.ofNullable(toppingOff.getOrderNumber()).ifPresent(cargoToppingOff::setOrderNumber);
    Optional.ofNullable(toppingOff.getRemark()).ifPresent(cargoToppingOff::setRemarks);
    cargoToppingOff.setQuantity(
        StringUtils.isEmpty(toppingOff.getQuantity())
            ? null
            : new BigDecimal(toppingOff.getQuantity()));
    Optional.ofNullable(toppingOff.getTankId()).ifPresent(cargoToppingOff::setTankXId);
    cargoToppingOff.setUllage(
        StringUtils.isEmpty(toppingOff.getUllage())
            ? null
            : new BigDecimal(toppingOff.getUllage()));
    cargoToppingOff.setIsActive(true);
  }
}
