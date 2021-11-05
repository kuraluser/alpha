/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.discharge_plan.CargoForCow;
import com.cpdss.common.generated.discharge_plan.CowPlan;
import com.cpdss.common.generated.discharge_plan.CowTankDetails;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowTankDetail;
import com.cpdss.dischargeplan.entity.CowWithDifferentCargo;
import com.cpdss.dischargeplan.repository.CowPlanDetailRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Discharge Information Cow Section
 *
 * @author JohnSooraj
 * @since 05-11-2021
 */
@Slf4j
@Service
public class CowPlanService {

  @Autowired CowPlanDetailRepository cowPlanDetailRepository;

  public void saveCowPlanDetails(CowPlan cowPlan) {
    Optional<CowPlanDetail> var1 =
        cowPlanDetailRepository.findByDischargingId(cowPlan.getDischargingInfoId());
    if (var1.isPresent()) {
      log.info("Save cow plan, Set values");
      CowPlanDetail cowPlanDetail = var1.get();
      if (!cowPlan.getCowEndTime().isEmpty()) {
        cowPlanDetail.setCowEndTime(new BigDecimal(cowPlan.getCowEndTime()));
      }
      if (!cowPlan.getCowStartTime().isEmpty()) {
        cowPlanDetail.setCowStartTime(new BigDecimal(cowPlan.getCowStartTime()));
      }
      if (!cowPlan.getCowTankPercent().isEmpty()) {
        cowPlanDetail.setCowPercentage(new BigDecimal(cowPlan.getCowTankPercent()));
      }
      cowPlanDetail.setWashTankWithDifferentCargo(cowPlan.getCowWithCargoEnable());

      if (!cowPlan.getEstCowDuration().isEmpty()) {
        cowPlanDetail.setEstimatedCowDuration(new BigDecimal(cowPlan.getEstCowDuration()));
      }

      cowPlanDetail.setNeedFlushingOil(cowPlan.getNeedFlushingOil());
      cowPlanDetail.setNeedFreshCrudeStorage(cowPlan.getNeedFreshCrudeStorage());

      if (!cowPlan.getTrimCowMax().isEmpty()) {
        cowPlanDetail.setCowMaxTrim(new BigDecimal(cowPlan.getTrimCowMax()));
      }
      if (!cowPlan.getTrimCowMin().isEmpty()) {
        cowPlanDetail.setCowMinTrim(new BigDecimal(cowPlan.getTrimCowMin()));
      }
      cowPlanDetail.setCowOperationType(cowPlan.getCowOptionTypeValue());
      updateTanksDetails(cowPlan, cowPlanDetail);
      cowPlanDetailRepository.save(cowPlanDetail);
    }
  }

  public void updateTanksDetails(CowPlan cowPlan, CowPlanDetail cowPlanDetail) {
    if (!cowPlan.getCowTankDetailsList().isEmpty()) {
      cowPlan.getCowTankDetailsList().stream()
          .forEach(
              plan -> {
                if (Common.COW_TYPE.CARGO.equals(plan.getCowType())) {
                  List<CargoForCow> cargoForCowList = plan.getCargoForCowList();

                  Set<CowWithDifferentCargo> cowWithDifferentCargos =
                      cowPlanDetail.getCowWithDifferentCargos();
                  List<Long> tanksIdsToSave =
                      cargoForCowList.stream()
                          .flatMap(cargo -> cargo.getTankIdsList().stream())
                          .collect(Collectors.toList());
                  // to disable existing cargo wash
                  if (!cowWithDifferentCargos.isEmpty()) {
                    cowWithDifferentCargos.forEach(
                        cargoCow -> {
                          if (!tanksIdsToSave.contains(cargoCow.getTankXid())) {
                            cargoCow.setIsActive(false);
                          }
                        });
                  }
                  // method to create new cargo Wash
                  Set<CowWithDifferentCargo> caroCowWashTosave =
                      createCaroCowWash(cargoForCowList, cowPlanDetail);
                  if (!caroCowWashTosave.isEmpty()) {
                    cowPlanDetail.setWashTankWithDifferentCargo(true);
                  }
                  if (cowPlanDetail.getCowWithDifferentCargos() == null) {
                    cowPlanDetail.setCowWithDifferentCargos(new HashSet<>(caroCowWashTosave));
                  } else {
                    cowPlanDetail.getCowWithDifferentCargos().addAll(caroCowWashTosave);
                  }

                } else {
                  List<Long> tanksIdsToSave = plan.getTankIdsList();
                  List<CowTankDetail> existingTanks =
                      cowPlanDetail.getCowTankDetails().stream()
                          .filter(tank -> tank.getCowTypeXid().equals(plan.getCowTypeValue()))
                          .collect(Collectors.toList());
                  // if the values are not in the current list set it as false
                  existingTanks.stream()
                      .forEach(
                          tank -> {
                            if (!tanksIdsToSave.contains(tank.getTankXid())) {
                              tank.setIsActive(false);
                            }
                          });
                  HashSet<CowTankDetail> cowTanksToSave =
                      createCowTankDetails(cowPlan, cowPlanDetail, plan, tanksIdsToSave);
                  if (cowPlanDetail.getCowTankDetails() == null) {
                    cowPlanDetail.setCowTankDetails(cowTanksToSave);
                  } else {
                    cowPlanDetail.getCowTankDetails().addAll(cowTanksToSave);
                  }
                }
              });
    }
  }

  public HashSet<CowTankDetail> createCowTankDetails(
      CowPlan cowPlan,
      CowPlanDetail cowPlanDetail,
      CowTankDetails plan,
      List<Long> tanksIdsToSave) {
    // adding new objects
    List<Long> existingTankIds =
        cowPlanDetail.getCowTankDetails().stream()
            .filter(
                cowtank ->
                    cowtank.getCowTypeXid().equals(plan.getCowTypeValue()) && cowtank.getIsActive())
            .map(CowTankDetail::getTankXid)
            .collect(Collectors.toList());
    List<Long> newTanksTOSave =
        tanksIdsToSave.stream()
            .filter(tankId -> !existingTankIds.contains(tankId))
            .collect(Collectors.toList());
    HashSet<CowTankDetail> newCowTankDetails = new HashSet<>();
    if (newTanksTOSave != null && !newTanksTOSave.isEmpty()) {
      newTanksTOSave.forEach(
          newCowTankId -> {
            CowTankDetail newTank = new CowTankDetail();
            newTank.setCowTypeXid(plan.getCowType().getNumber());
            newTank.setCowPlanDetail(cowPlanDetail);
            newTank.setDischargingXid(cowPlan.getDischargingInfoId());
            newTank.setIsActive(true);
            newTank.setTankXid(newCowTankId);
            newCowTankDetails.add(newTank);
          });
    }
    return newCowTankDetails;
  }

  public Set<CowWithDifferentCargo> createCaroCowWash(
      List<CargoForCow> cargoForCowList, CowPlanDetail cowPlanDetail) {
    Set<CowWithDifferentCargo> newCowWashCargo = new HashSet<>();
    cargoForCowList.forEach(
        newCargoWash -> {

          // finds the tanks ids
          List<Long> savedTanks =
              cowPlanDetail.getCowWithDifferentCargos().stream()
                  .filter(
                      cargoWash ->
                          cargoWash
                                  .getCargoNominationXid()
                                  .equals(newCargoWash.getCargoNominationId())
                              && cargoWash.getIsActive())
                  .map(CowWithDifferentCargo::getTankXid)
                  .collect(Collectors.toList());
          // remove the already existing ids in the db
          List<Long> tankIdsToSave = newCargoWash.getTankIdsList();
          List<Long> newTanksTOSave =
              tankIdsToSave.stream()
                  .filter(tankId -> !savedTanks.contains(tankId))
                  .collect(Collectors.toList());
          // create the new ones
          createCargoCowWash(cowPlanDetail, newCargoWash, newTanksTOSave, newCowWashCargo);
          updateCargoCowWash(newCargoWash, cowPlanDetail);
        });

    return newCowWashCargo;
  }

  public void createCargoCowWash(
      CowPlanDetail cowPlanDetail,
      CargoForCow newCargoWash,
      List<Long> tankIdsToSave,
      Set<CowWithDifferentCargo> newCowWashCargo) {
    tankIdsToSave.forEach(
        tankId -> {
          CowWithDifferentCargo cowWithDifferentCargo = new CowWithDifferentCargo();
          cowWithDifferentCargo.setCowPlanDetail(cowPlanDetail);
          cowWithDifferentCargo.setCargoNominationXid(newCargoWash.getCargoNominationId());
          cowWithDifferentCargo.setCargoXid(newCargoWash.getCargoId());
          cowWithDifferentCargo.setDischargingXid(cowPlanDetail.getDischargeInformation().getId());
          cowWithDifferentCargo.setIsActive(true);
          cowWithDifferentCargo.setWashingCargoXid(newCargoWash.getWashingCargoId());
          cowWithDifferentCargo.setWashingCargoNominationXid(
              newCargoWash.getWashingCargoNominationId());
          cowWithDifferentCargo.setTankXid(tankId);
          newCowWashCargo.add(cowWithDifferentCargo);
        });
  }

  public void updateCargoCowWash(CargoForCow newCargoWash, CowPlanDetail cowPlanDetail) {

    cowPlanDetail
        .getCowWithDifferentCargos()
        .forEach(
            cargoCow -> {
              if (cargoCow.getCargoNominationXid().equals(newCargoWash.getCargoNominationId())
                  && cargoCow.getIsActive()
                  && newCargoWash.getTankIdsList().contains(cargoCow.getTankXid())) {
                cargoCow.setCargoXid(newCargoWash.getCargoId());
                cargoCow.setWashingCargoXid(newCargoWash.getWashingCargoId());
                cargoCow.setWashingCargoNominationXid(newCargoWash.getWashingCargoNominationId());
              }
            });
  }
}
