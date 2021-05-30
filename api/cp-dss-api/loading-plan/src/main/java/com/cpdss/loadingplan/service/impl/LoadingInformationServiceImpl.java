/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.CargoToppingOffSequenceRepository;
import com.cpdss.loadingplan.repository.LoadablePlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.LoadablePlanCommingleDetailsRepository;
import com.cpdss.loadingplan.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadingplan.repository.LoadablePlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.service.LoadingInformationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadingInformationServiceImpl implements LoadingInformationService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;

  @Override
  public LoadingInformation saveLoadingInformation(
      LoadingInformationDetail loadingInformationDetail, LoadingInformation loadingInformation) {
    deleteLoadingInformationOfVessel(
        loadingInformationDetail.getVesselId(), loadingInformationDetail.getLoadablePatternId());

    Optional.ofNullable(loadingInformationDetail.getLoadablePatternId())
        .ifPresent(loadingInformation::setLoadablePatternXId);
    Optional.ofNullable(loadingInformationDetail.getPortId())
        .ifPresent(loadingInformation::setPortXId);
    Optional.ofNullable(loadingInformationDetail.getSynopticalTableId())
        .ifPresent(loadingInformation::setSynopticalTableXId);
    Optional.ofNullable(loadingInformationDetail.getVesselId())
        .ifPresent(loadingInformation::setVesselXId);
    loadingInformation.setIsActive(true);
    return loadingInformationRepository.save(loadingInformation);
  }

  private void deleteLoadingInformationOfVessel(Long vesselId, Long loadablePatternId) {

    List<LoadingInformation> loadingInformations =
        loadingInformationRepository.findByVesselXIdAndLoadablePatternXIdNotAndIsActive(
            vesselId, loadablePatternId, true);
    loadingInformations.forEach(
        loadingInformation -> {
          deleteLoadablePlanDetails(loadingInformation);
          loadingInformationRepository.deleteByLoadingInformationId(loadingInformation.getId());
        });
  }

  @Override
  public void deleteLoadablePlanDetails(LoadingInformation loadingInformation) {
    cargoToppingOffSequenceRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanBallastDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanCommingleDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanQuantityRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanStowageDetailsRepository.deleteByLoadingInformation(loadingInformation);
  }
}
