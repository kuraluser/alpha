/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingBerths;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadingBerthDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.service.LoadingBerthService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class LoadingBerthServiceImpl implements LoadingBerthService {

  @Autowired LoadingBerthDetailsRepository loadingBerthDetailRepository;
  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Override
  public void saveLoadingBerthList(
      List<LoadingBerths> loadingBerthsList, LoadingInformation loadingInformation)
      throws Exception {
    List<LoadingBerthDetail> existingBerths =
        loadingBerthDetailRepository.findByLoadingInformationIdAndIsActive(
            loadingInformation.getId(), true);
    List<Long> requestedBerths =
        loadingBerthsList.stream().map(berth -> berth.getId()).collect(Collectors.toList());
    existingBerths.stream()
        .filter(berth -> !requestedBerths.contains(berth.getId()))
        .forEach(
            berth -> {
              loadingBerthDetailRepository.deleteById(berth.getId());
            });
    for (LoadingPlanModels.LoadingBerths berth : loadingBerthsList) {
      log.info(
          "Saving berth {} for LoadingInformation {}",
          berth.getBerthId(),
          berth.getLoadingInfoId());
      LoadingBerthDetail loadingBerthDetail = null;
      if (berth.getId() == 0) {
        loadingBerthDetail = new LoadingBerthDetail();
      } else {
        Optional<LoadingBerthDetail> loadingBerthDetailOpt =
            loadingBerthDetailRepository.findByIdAndIsActiveTrue(berth.getId());
        if (loadingBerthDetailOpt.isPresent()) {
          loadingBerthDetail = loadingBerthDetailOpt.get();
        } else {
          log.error("Cannot find the loading berth with id {}", berth.getId());
          throw new Exception("Cannot find the loading berth with id " + berth.getId());
        }
      }

      buildLoadingBerthDetail(berth, loadingBerthDetail);
      loadingBerthDetailRepository.save(loadingBerthDetail);
    }
  }

  private void buildLoadingBerthDetail(LoadingBerths berth, LoadingBerthDetail loadingBerthDetail)
      throws Exception {
    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(berth.getLoadingInfoId());
    if (loadingInformationOpt.isPresent()) {
      loadingBerthDetail.setLoadingInformation(loadingInformationOpt.get());
    } else {
      throw new Exception(
          "Cannot find the loadable study for berth detail with id " + berth.getLoadingInfoId());
    }
    loadingBerthDetail.setAirDraftLimitation(
        StringUtils.isEmpty(berth.getAirDraftLimitation())
            ? null
            : new BigDecimal(berth.getAirDraftLimitation()));
    Optional.ofNullable(berth.getBerthId()).ifPresent(loadingBerthDetail::setBerthXId);
    loadingBerthDetail.setDepth(
        StringUtils.isEmpty(berth.getDepth()) ? null : new BigDecimal(berth.getDepth()));
    loadingBerthDetail.setMaxManifoldHeight(
        StringUtils.isEmpty(berth.getMaxManifoldHeight())
            ? null
            : new BigDecimal(berth.getMaxManifoldHeight()));
    loadingBerthDetail.setSeaDraftLimitation(
        StringUtils.isEmpty(berth.getSeaDraftLimitation())
            ? null
            : new BigDecimal(berth.getSeaDraftLimitation()));
    Optional.ofNullable(berth.getSpecialRegulationRestriction())
        .ifPresent(loadingBerthDetail::setSpecialRegulationRestriction);
    Optional.ofNullable(berth.getHoseConnections())
        .ifPresent(loadingBerthDetail::setHoseConnections);
    Optional.ofNullable(berth.getItemsToBeAgreedWith())
        .ifPresent(loadingBerthDetail::setItemToBeAgreedWith);
    loadingBerthDetail.setIsActive(true);
  }
}
