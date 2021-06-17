/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadingInformationRepository
    extends CommonCrudRepository<LoadingInformation, Long> {

  public List<LoadingInformation> findByVesselXIdAndLoadablePatternXIdNotAndIsActive(
      Long vesselXId, Long loadablePatternXId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingInformation SET isActive = false WHERE id = ?1")
  public void deleteByLoadingInformationId(Long id);

  Optional<LoadingInformation> findByIdAndVesselXIdAndIsActiveTrue(Long id, Long vesselId);

  Optional<LoadingInformation> findByVesselXIdAndLoadablePatternXIdAndIsActiveTrue(
      Long vesselId, Long patternId);

  Optional<LoadingInformation> findByIdAndIsActiveTrue(Long id);

  Optional<LoadingInformation> findByVesselXIdAndVoyageIdAndPortRotationXId(Long var1, Long var2, Long var3);

}
