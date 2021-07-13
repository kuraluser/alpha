/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.OnBoardQuantityRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class OnBoardQuantityService {

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;

  @Autowired private VoyageService voyageService;

  @Autowired private LoadablePatternService loadablePatternService;

  public LoadableStudy.OnBoardQuantityReply.Builder saveOnBoardQuantity(
      LoadableStudy.OnBoardQuantityDetail request,
      LoadableStudy.OnBoardQuantityReply.Builder replyBuilder)
      throws GenericServiceException {
    Optional<com.cpdss.loadablestudy.entity.LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    OnBoardQuantity entity = null;
    if (request.getId() == 0) {
      entity = new OnBoardQuantity();
      entity.setLoadableStudy(loadableStudyOpt.get());
    } else {
      entity = this.onBoardQuantityRepository.findByIdAndIsActive(request.getId(), true);
      if (null == entity) {
        throw new GenericServiceException(
            "On hand quantity does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
    }
    this.voyageService.checkIfVoyageClosed(entity.getLoadableStudy().getVoyage().getId());
    loadablePatternService.isPatternGeneratedOrConfirmed(entity.getLoadableStudy());

    this.buildOnBoardQuantityEntity(entity, request);
    entity = this.onBoardQuantityRepository.save(entity);
    loadableStudyOpt.get().setIsObqComplete(request.getIsObqComplete());
    loadableStudyOpt.get().setLoadOnTop(request.getLoadOnTop());
    this.loadableStudyRepository.save(loadableStudyOpt.get());
    replyBuilder.setId(entity.getId());
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Build on board quantity entity
   *
   * @param entity
   * @param request
   */
  private void buildOnBoardQuantityEntity(
      OnBoardQuantity entity, LoadableStudy.OnBoardQuantityDetail request) {
    entity.setCargoId(0 == request.getCargoId() ? null : request.getCargoId());
    entity.setTankId(request.getTankId());
    entity.setPortId(request.getPortId());
    entity.setSounding(
        isEmpty(request.getSounding()) ? null : new BigDecimal(request.getSounding()));
    entity.setPlannedArrivalWeight(
        isEmpty(request.getWeight()) ? null : new BigDecimal(request.getWeight()));
    entity.setVolumeInM3(request.getVolume());
    entity.setColorCode(isEmpty(request.getColorCode()) ? null : request.getColorCode());
    entity.setAbbreviation(isEmpty(request.getAbbreviation()) ? null : request.getAbbreviation());
    entity.setDensity(isEmpty(request.getDensity()) ? null : new BigDecimal(request.getDensity()));
    entity.setIsActive(true);
  }

  /**
   * @param loadableStudy
   * @param loadableStudyEntity
   * @param modelMapper void
   */
  public void buildOnBoardQuantityDetails(
      com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    loadableStudy.setOnBoardQuantity(new ArrayList<>());
    List<OnBoardQuantity> onBoardQuantities =
        onBoardQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyEntity, true);
    onBoardQuantities.forEach(
        onBoardQuantity -> {
          com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantityDto =
              new com.cpdss.loadablestudy.domain.OnBoardQuantity();
          onBoardQuantityDto =
              modelMapper.map(
                  onBoardQuantity, com.cpdss.loadablestudy.domain.OnBoardQuantity.class);
          onBoardQuantityDto.setApi(
              null != onBoardQuantity.getDensity()
                  ? String.valueOf(onBoardQuantity.getDensity())
                  : "");
          loadableStudy.getOnBoardQuantity().add(onBoardQuantityDto);
        });
  }
}
