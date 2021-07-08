/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.repository.CargoNominationOperationDetailsRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author arun.j */
@Log4j2
@Service
@Transactional
public class CargoNominationService {

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;
  /**
   * fetch cargo nomination based on the loadable study id
   *
   * @param loadableStudyId
   * @return
   * @throws GenericServiceException
   */
  public List<CargoNomination> getCargoNominationByLoadableStudyId(Long loadableStudyId)
      throws GenericServiceException {
    List<CargoNomination> cargos =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
    if (cargos.isEmpty()) {
      throw new GenericServiceException(
          "cargo nomination data missing",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    return cargos;
  }

  public List<CargoNomination> saveDsichargeStudyCargoNominations(
      Long dischargeStudyId, Long loadableStudyId, Long portId) throws GenericServiceException {
    List<CargoNomination> cargos = getCargoNominationByLoadableStudyId(loadableStudyId);
    List<CargoNomination> dischargeStudycargos = new ArrayList<>();

    cargos
        .parallelStream()
        .forEach(
            cargo -> {
              dischargeStudycargos.add(createDsCargoNomination(dischargeStudyId, cargo, portId));
            });
    return cargoNominationRepository.saveAll(dischargeStudycargos);
  }

  private CargoNomination createDsCargoNomination(
      Long dischargeStudyId, CargoNomination cargo, Long portId) {
    CargoNomination dischargeStudyCargo = new CargoNomination();
    dischargeStudyCargo.setAbbreviation(cargo.getAbbreviation());
    dischargeStudyCargo.setApi(cargo.getApi());
    dischargeStudyCargo.setCargoXId(cargo.getCargoXId());
    dischargeStudyCargo.setColor(cargo.getColor());
    dischargeStudyCargo.setIsActive(true);
    dischargeStudyCargo.setLoadableStudyXId(dischargeStudyId);
    dischargeStudyCargo.setMaxTolerance(cargo.getMaxTolerance());
    dischargeStudyCargo.setMinTolerance(cargo.getMinTolerance());
    dischargeStudyCargo.setPriority(cargo.getPriority());
    if (cargo.getCargoNominationPortDetails() == null
        || cargo.getCargoNominationPortDetails().isEmpty()) {
      dischargeStudyCargo.setQuantity(new BigDecimal(0));
    } else {
      dischargeStudyCargo.setQuantity(
          cargo.getCargoNominationPortDetails().stream()
              .map(CargoNominationPortDetails::getQuantity)
              .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
    dischargeStudyCargo.setSegregationXId(cargo.getSegregationXId());
    dischargeStudyCargo.setTemperature(cargo.getTemperature());
    dischargeStudyCargo.setVersion(cargo.getVersion());
    dischargeStudyCargo.setCargoNominationPortDetails(
        createCargoNominationPortDetails(dischargeStudyCargo, cargo, portId));
    return dischargeStudyCargo;
  }

  private Set<CargoNominationPortDetails> createCargoNominationPortDetails(
      CargoNomination dischargeStudyCargo, CargoNomination cargo, Long portId) {
    CargoNominationPortDetails portDetail = new CargoNominationPortDetails();
    portDetail.setPortId(portId);
    portDetail.setIsActive(true);
    portDetail.setCargoNomination(dischargeStudyCargo);
    portDetail.setQuantity(
        cargo.getCargoNominationPortDetails().stream()
            .map(CargoNominationPortDetails::getQuantity)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
    return new HashSet<CargoNominationPortDetails>(Arrays.asList(portDetail));
  }
}
