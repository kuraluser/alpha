/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingCargoApiTemperature;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadingCargoToBeLoadedRepository;
import com.cpdss.loadingplan.service.LoadingCargoToBeLoadedService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class to implement methods related to cargo api, temperature and max loading rate
 *
 * @author sreemanikandan.k
 * @since 07/01/2022
 */
@Slf4j
@Service
@Transactional
public class LoadingCargoToBeLoadedServiceImpl implements LoadingCargoToBeLoadedService {

  @Autowired private LoadingCargoToBeLoadedRepository loadingCargoToBeLoadedRepository;

  /**
   * Method to save cargo to be loaded details
   *
   * @param loadableQuantityCargoDetailsList List of cargo to be loaded details
   * @param loadingInformation Loading Information object
   */
  @Override
  public void saveCargoToBeLoaded(
      List<LoadableStudy.LoadableQuantityCargoDetails> loadableQuantityCargoDetailsList,
      LoadingInformation loadingInformation) {

    log.info("Inside saveCargoToBeLoaded method!");
    List<LoadingCargoApiTemperature> loadingCargoApiTemperatures = new ArrayList<>();

    loadableQuantityCargoDetailsList.forEach(
        loadableQuantityCargoDetails -> {
          Optional<LoadingCargoApiTemperature> loadingCargoApiTemperatureWrapper =
              loadingCargoToBeLoadedRepository
                  .findByLoadingInformationAndCargoNominationIdAndIsActiveTrue(
                      loadingInformation, loadableQuantityCargoDetails.getCargoNominationId());
          LoadingCargoApiTemperature loadingCargoApiTemperature;

          // Create new entry of entity in table if not present
          if (loadingCargoApiTemperatureWrapper.isEmpty()) {
            loadingCargoApiTemperature = new LoadingCargoApiTemperature();
          } else { // Fetch existing
            loadingCargoApiTemperature = loadingCargoApiTemperatureWrapper.get();
          }
          buildLoadingCargoApiTemperature(
              loadingCargoApiTemperature, loadableQuantityCargoDetails, loadingInformation);
          loadingCargoApiTemperatures.add(loadingCargoApiTemperature);
        });
    loadingCargoToBeLoadedRepository.saveAll(loadingCargoApiTemperatures);
  }

  /**
   * Method to fetch cargo to be loaded details
   *
   * @param loadingInformation LoadingInformation input
   * @param builder LoadingPlanModels.LoadingInformation.Builder input to be set
   */
  @Override
  public void getCargoToBeLoaded(
      LoadingInformation loadingInformation, LoadingPlanModels.LoadingInformation.Builder builder) {

    log.info("Inside getCargoToBeLoaded method!");

    // Fetch from repo
    List<LoadingCargoApiTemperature> loadingCargoApiTemperatures =
        loadingCargoToBeLoadedRepository.findByLoadingInformationAndIsActiveTrue(
            loadingInformation);
    buildLoadingInformationResponse(loadingCargoApiTemperatures, builder);
  }

  /**
   * Method to build cargo to be loaded details
   *
   * @param loadingCargoApiTemperatures List of LoadingCargoApiTemperature input
   * @param builder LoadingPlanModels.LoadingInformation.Builder input to be set
   */
  public void buildLoadingInformationResponse(
      List<LoadingCargoApiTemperature> loadingCargoApiTemperatures,
      LoadingPlanModels.LoadingInformation.Builder builder) {

    log.info("Inside buildLoadingInformationResponse method!");

    // Set fields
    loadingCargoApiTemperatures.forEach(
        loadingCargoApiTemperature -> {
          LoadableStudy.LoadableQuantityCargoDetails.Builder apiBuilder =
              LoadableStudy.LoadableQuantityCargoDetails.newBuilder();

          Optional.ofNullable(loadingCargoApiTemperature.getApi())
              .ifPresent(api -> apiBuilder.setEstimatedAPI(String.valueOf(api)));
          Optional.ofNullable(loadingCargoApiTemperature.getTemperature())
              .ifPresent(temp -> apiBuilder.setEstimatedTemp(String.valueOf(temp)));
          Optional.ofNullable(loadingCargoApiTemperature.getMaxLoadingRate())
              .ifPresent(rate -> apiBuilder.setMaxLoadingRate(String.valueOf(rate)));
          Optional.ofNullable(loadingCargoApiTemperature.getCargoNominationId())
              .ifPresent(apiBuilder::setCargoNominationId);

          builder.addLoadableQuantityCargoDetails(apiBuilder.build());
        });
  }

  /**
   * Method to build Cargo api, temp and max loading rate details
   *
   * @param loadingCargoApiTemperature LoadingCargoApiTemperature object to be set and saved
   * @param loadableQuantityCargoDetails Object containing cargo api details
   * @param loadingInformation LoadingInformation object
   */
  public void buildLoadingCargoApiTemperature(
      LoadingCargoApiTemperature loadingCargoApiTemperature,
      LoadableStudy.LoadableQuantityCargoDetails loadableQuantityCargoDetails,
      LoadingInformation loadingInformation) {

    log.info("Inside buildLoadingCargoApiTemperature method!");

    // Set fields
    Optional.of(loadableQuantityCargoDetails.getEstimatedAPI())
        .ifPresent(api -> loadingCargoApiTemperature.setApi(new BigDecimal(api)));
    Optional.of(loadableQuantityCargoDetails.getEstimatedTemp())
        .ifPresent(
            temperature -> loadingCargoApiTemperature.setTemperature(new BigDecimal(temperature)));
    Optional.of(loadableQuantityCargoDetails.getMaxLoadingRate())
        .ifPresent(
            maxLoadingRate ->
                loadingCargoApiTemperature.setMaxLoadingRate(new BigDecimal(maxLoadingRate)));
    Optional.ofNullable(loadingInformation)
        .ifPresent(loadingCargoApiTemperature::setLoadingInformation);
    Optional.of(loadableQuantityCargoDetails.getCargoNominationId())
        .ifPresent(loadingCargoApiTemperature::setCargoNominationId);
    loadingCargoApiTemperature.setIsActive(true);
  }
}
