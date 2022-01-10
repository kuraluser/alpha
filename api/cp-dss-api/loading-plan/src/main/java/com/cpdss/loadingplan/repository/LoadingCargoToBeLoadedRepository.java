/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingCargoApiTemperature;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Repository Class of LoadingCargoApiTemperature entity
 *
 * @author sreemanikandan.k
 * @since 07/01/2022
 */
@Repository
public interface LoadingCargoToBeLoadedRepository
    extends CommonCrudRepository<LoadingCargoApiTemperature, Long> {

  /**
   * Method to fetch LoadingCargoApiTemperature entity
   *
   * @param loadingInformation LoadingInformation input
   * @param cargoNominationId cargoNominationId input
   * @return LoadingCargoApiTemperature entity
   */
  Optional<LoadingCargoApiTemperature> findByLoadingInformationAndCargoNominationIdAndIsActiveTrue(
      LoadingInformation loadingInformation, Long cargoNominationId);

  /**
   * Method to fetch all LoadingCargoApiTemperature entities against LoadingInformation input
   *
   * @param loadingInformation LoadingInformation input
   * @return List of LoadingCargoApiTemperature entities
   */
  List<LoadingCargoApiTemperature> findByLoadingInformationAndIsActiveTrue(
      LoadingInformation loadingInformation);
}
