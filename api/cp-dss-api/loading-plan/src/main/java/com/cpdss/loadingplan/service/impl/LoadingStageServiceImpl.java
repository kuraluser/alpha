/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingStages;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.StageDuration;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.StageOffsets;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.StageOffset;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.repository.StageDurationRepository;
import com.cpdss.loadingplan.repository.StageOffsetRepository;
import com.cpdss.loadingplan.service.LoadingStageService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoadingStageServiceImpl implements LoadingStageService {

  @Autowired StageDurationRepository stageDurationRepository;
  @Autowired StageOffsetRepository stageOffsetRepository;
  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Override
  public void saveLoadingStages(LoadingStages loadingStages) throws Exception {
    saveStageDurations(loadingStages.getDuration());
    saveStageOffsets(loadingStages.getOffset());
  }

  private void saveStageOffsets(StageOffsets offset) throws Exception {
    StageOffset stageOffset = null;
    if (offset.getId() == 0) {
      stageOffset = new StageOffset();
    } else {
      Optional<StageOffset> stageOffsetOpt =
          stageOffsetRepository.findByIdAndIsActiveTrue(offset.getId());
      if (stageOffsetOpt.isPresent()) {
        stageOffset = stageOffsetOpt.get();
      } else {
        throw new Exception("Cannot find the offset with id " + offset.getId());
      }
    }

    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(offset.getId());
    // Optional.ofNullable(offset.getStageOffsetVal()).ifPresent(stageOffset::set);
    // stageOffset.setLoadingInfo
    stageOffset.setIsActive(true);
    stageOffsetRepository.save(stageOffset);
  }

  private void saveStageDurations(StageDuration duration) throws Exception {
    com.cpdss.loadingplan.entity.StageDuration stageDuration = null;
    if (duration.getId() == 0) {
      stageDuration = new com.cpdss.loadingplan.entity.StageDuration();
    } else {
      Optional<com.cpdss.loadingplan.entity.StageDuration> stageDurationOpt =
          stageDurationRepository.findByIdAndIsActiveTrue(duration.getId());
      if (stageDurationOpt.isPresent()) {
        stageDuration = stageDurationOpt.get();
      } else {
        throw new Exception("Cannot find the offset with id " + duration.getId());
      }
    }

    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(duration.getId());
    // Optional.ofNullable(offset.getStageOffsetVal()).ifPresent(stageOffset::set);
    // stageOffset.setLoadingInfo
    stageDuration.setIsActive(true);
    stageDurationRepository.save(stageDuration);
  }
}
