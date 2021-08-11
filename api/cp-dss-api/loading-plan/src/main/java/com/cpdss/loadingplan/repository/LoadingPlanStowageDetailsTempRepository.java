/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStowageTempDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LoadingPlanStowageDetailsTempRepository
        extends CommonCrudRepository<LoadingPlanStowageTempDetails, Long> {

    List<LoadingPlanStowageDetails> findByLoadingPlanPortWiseDetailsAndIsActiveTrueOrderById(
            LoadingPlanStowageTempDetails loadingPlanPortWiseDetails);

    @Modifying
    @Transactional
    @Query(
            "UPDATE LoadingPlanStowageDetails SET isActive = false WHERE loadingPlanPortWiseDetails = ?1")
    public void deleteByLoadingPlanPortWiseDetails(
            LoadingPlanStowageTempDetails loadingPlanPortWiseDetails);

}