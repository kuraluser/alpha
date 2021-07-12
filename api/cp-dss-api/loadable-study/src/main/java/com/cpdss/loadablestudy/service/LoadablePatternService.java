/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Master Service for Loadable Pattern
 *
 * @author vinothkumar M
 * @since 08-07-2021
 */
@Slf4j
@Service
public class LoadablePatternService {

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired private LoadablePlanService loadablePlanService;

  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;

  @Autowired private VoyageRepository voyageRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;

  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @Autowired private CargoOperationRepository cargoOperationRepository;

  @Autowired private AlgoErrorsRepository algoErrorsRepository;

  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;

  @Autowired private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;

  @Autowired private LoadableQuantityService loadableQuantityService;

  @Autowired private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @Autowired private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;

  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;

  @Autowired private StabilityParameterRepository stabilityParameterRepository;

  @Autowired private LoadicatorService loadicatorService;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired private AlgoErrorService algoErrorService;

  @Autowired private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;

  @Autowired private LoadablePlanQuantityRepository loadablePlanQuantityRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  /**
   * @param loadableStudy
   * @throws GenericServiceException
   */
  public void isPatternGeneratedOrConfirmed(LoadableStudy loadableStudy)
      throws GenericServiceException {
    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);
    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);
    if (!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty()) {
      throw new GenericServiceException(
          "Save/Edit/Delte not allowed for plan generated /confirmed loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  public void getLoadableCommingleByPatternId(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
      com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.Builder builder)
      throws Exception {
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (loadablePatternOpt.isPresent()) {
      List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
          this.loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
              loadablePatternOpt.get(), true);
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder
          loadableCommingle =
              com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails
                  .newBuilder();
      loadablePlanCommingleDetails.forEach(
          lpcd -> {
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder
                loadableQtyCommCargo = getCommingleCargoBuilder(lpcd);
            builder.addLoadableQuantityCommingleCargoDetails(loadableQtyCommCargo);
          });
      builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } else throw new Exception("Cannot find loadable pattern");
    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  private com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder
      getCommingleCargoBuilder(LoadablePlanCommingleDetails lpcd) {
    com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails.newBuilder();
    ofNullable(lpcd.getId()).ifPresent(builder::setId);
    ofNullable(lpcd.getApi()).ifPresent(builder::setApi);
    ofNullable(lpcd.getCargo1Abbreviation()).ifPresent(builder::setCargo1Abbreviation);
    ofNullable(lpcd.getCargo1Bbls60f()).ifPresent(builder::setCargo1Bbls60F);
    ofNullable(lpcd.getCargo1BblsDbs()).ifPresent(builder::setCargo1Bblsdbs);
    ofNullable(lpcd.getCargo1Kl()).ifPresent(builder::setCargo1KL);
    ofNullable(lpcd.getCargo1Lt()).ifPresent(builder::setCargo1LT);
    ofNullable(lpcd.getCargo1Mt()).ifPresent(builder::setCargo1MT);
    ofNullable(lpcd.getCargo1Percentage()).ifPresent(builder::setCargo1Percentage);
    ofNullable(lpcd.getCargo2Abbreviation()).ifPresent(builder::setCargo2Abbreviation);
    ofNullable(lpcd.getCargo2Bbls60f()).ifPresent(builder::setCargo2Bbls60F);
    ofNullable(lpcd.getCargo2BblsDbs()).ifPresent(builder::setCargo2Bblsdbs);
    ofNullable(lpcd.getCargo2Kl()).ifPresent(builder::setCargo2KL);
    ofNullable(lpcd.getCargo2Lt()).ifPresent(builder::setCargo2LT);
    ofNullable(lpcd.getCargo2Mt()).ifPresent(builder::setCargo2MT);
    ofNullable(lpcd.getCargo2Percentage()).ifPresent(builder::setCargo2Percentage);
    ofNullable(lpcd.getGrade()).ifPresent(builder::setGrade);
    ofNullable(lpcd.getQuantity()).ifPresent(builder::setQuantity);
    ofNullable(lpcd.getTankName()).ifPresent(builder::setTankName);
    ofNullable(lpcd.getTemperature()).ifPresent(builder::setTemp);
    ofNullable(lpcd.getTankShortName()).ifPresent(builder::setTankShortName);
    return builder;
  }

  public com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson.Builder
      getLoadablePatternDetailsJson(
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson.Builder
              builder)
          throws Exception {
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (loadablePatternOpt.isPresent()) {
      LoadabalePatternValidateRequest loadabalePatternValidateRequest =
          new LoadabalePatternValidateRequest();
      loadablePlanService.buildLoadablePlanPortWiseDetails(
          loadablePatternOpt.get(), loadabalePatternValidateRequest);
      ObjectMapper mapper = new ObjectMapper();
      builder.setLoadablePatternDetails(
          mapper.writeValueAsString(
              loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails()));
      builder.setLoadableStudyId(loadablePatternOpt.get().getLoadableStudy().getId());
      builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } else throw new Exception("Cannot find loadable pattern");

    return builder;
  }

  public com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.Builder
      getLoadablePatternByVoyageAndStatus(
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.Builder builder)
          throws GenericServiceException {
    Voyage voyage = this.voyageRepository.findByIdAndIsActive(request.getVoyageId(), true);

    Optional<LoadableStudy> loadableStudy =
        loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActiveAndPlanningTypeXId(
            voyage, CONFIRMED_STATUS_ID, true, Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE);
    if (loadableStudy.isEmpty()) {
      throw new GenericServiceException(
          "Confirmed Loadable study does not exist",
          CommonErrorCodes.E_CPDSS_CONFIRMED_LS_DOES_NOT_EXIST,
          HttpStatusCode.BAD_REQUEST);
    }
    builder.setLoadableStudyId(loadableStudy.get().getId());
    log.info("Confirmed Ls - ls id {}", loadableStudy.get().getId());
    Optional<LoadablePattern> pattern =
        loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
            loadableStudy.get(), CONFIRMED_STATUS_ID, true);
    if (pattern.isPresent()) {
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder loadablePattern =
          com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
      loadablePattern.setLoadablePatternId(pattern.get().getId());
      builder.setPattern(loadablePattern.build());
    }
    builder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  public com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder saveLoadablePatterns(
      com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
      com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder)
      throws GenericServiceException {
    log.info("saveLoadablePatternDetails - loadable study micro service");
    Optional<LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    if (request.getLoadablePlanDetailsList().isEmpty()) {
      log.info("saveLoadablePatternDetails - loadable study micro service - no plans available");
      loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
          LOADABLE_STUDY_NO_PLAN_AVAILABLE_ID, request.getProcesssId(), true);
      loadableStudyRepository.updateLoadableStudyStatus(
          LOADABLE_STUDY_NO_PLAN_AVAILABLE_ID, loadableStudyOpt.get().getId());
    } else {
      // uncomment with communication service implementation
      // savePatternDtails(request, loadableStudyOpt);
      Long lastLoadingPort =
          loadableStudyPortRotationService.getLastPort(
              loadableStudyOpt.get(), this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));
      List<LoadablePattern> loadablePatterns = new ArrayList<LoadablePattern>();
      request
          .getLoadablePlanDetailsList()
          .forEach(
              lpd -> {
                LoadablePattern loadablePattern =
                    saveloadablePattern(lpd, loadableStudyOpt.get(), request.getHasLodicator());
                loadablePatterns.add(loadablePattern);
                saveConstrains(lpd, loadablePattern);
                Optional<com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails>
                    lppwdOptional =
                        lpd.getLoadablePlanPortWiseDetailsList().stream()
                            .filter(lppwd -> lppwd.getPortId() == lastLoadingPort)
                            .findAny();

                if (lppwdOptional.isPresent()) {
                  loadableQuantityService.saveLoadableQuantity(
                      lppwdOptional.get(), loadablePattern);
                  saveLoadablePlanCommingleCargo(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadableQuantityCommingleCargoDetailsList(),
                      loadablePattern);
                  saveLoadablePlanStowageDetails(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadablePlanStowageDetailsList(),
                      loadablePattern);
                  saveLoadablePlanBallastDetails(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadablePlanBallastDetailsList(),
                      loadablePattern);
                }

                AtomicInteger displayOrder = new AtomicInteger(0);
                saveLoadableQuantityCommingleCargoPortwiseDetails(
                    lpd.getLoadablePlanPortWiseDetailsList(), loadablePattern, displayOrder);
                saveStabilityParameters(loadablePattern, lpd, lastLoadingPort);
                saveLoadablePlanStowageDetails(loadablePattern, lpd, displayOrder);
                saveLoadablePlanBallastDetails(loadablePattern, lpd);
                saveStabilityParameterForNonLodicator(
                    request.getHasLodicator(), loadablePattern, lpd);
              });
      if (request.getHasLodicator()) {
        loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
            LOADABLE_STUDY_STATUS_VERIFICATION_WITH_LOADICATOR_ID, request.getProcesssId(), true);
        loadicatorService.saveLoadicatorInfo(
            loadableStudyOpt.get(), request.getProcesssId(), 0L, loadablePatterns);
      } else {

        loadableStudyRepository.updateLoadableStudyStatus(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudyOpt.get().getId());
        loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, request.getProcesssId(), true);
      }
    }
    if (request.getAlgoErrorsCount() > 0) {
      algoErrorsRepository.deleteAlgoErrorByLSId(false, request.getLoadableStudyId());
      algoErrorHeadingRepository.deleteAlgoErrorHeadingByLSId(false, request.getLoadableStudyId());
      algoErrorService.saveAlgoErrorToDB(
          request, new LoadablePattern(), loadableStudyOpt.get(), false);
      loadableStudyRepository.updateLoadableStudyStatus(
          LOADABLE_STUDY_STATUS_ERROR_OCCURRED_ID, loadableStudyOpt.get().getId());
      loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
          LOADABLE_STUDY_STATUS_ERROR_OCCURRED_ID, request.getProcesssId(), true);
    }

    builder
        .setResponseStatus(
            Common.ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
        .build();
    return builder;
  }

  /**
   * @param lpd void
   * @param loadableStudy
   * @param hasLoadicator
   */
  private LoadablePattern saveloadablePattern(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails lpd,
      LoadableStudy loadableStudy,
      boolean hasLoadicator) {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setCaseNumber(lpd.getCaseNumber());
    if (hasLoadicator) {
      loadablePattern.setIsActive(false);
    } else {
      loadablePattern.setIsActive(true);
    }

    loadablePattern.setLoadableStudy(loadableStudy);
    loadablePattern.setLoadableStudyStatus(LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
    Optional<LoadablePattern> lp =
        loadablePatternRepository.findOneByLoadableStudyAndCaseNumberAndIsActiveTrue(
            loadableStudy, lpd.getCaseNumber());
    if (lp.isPresent()) { // Delete old one and add new pattern
      log.info(
          "Lodable Pattern delete for LS Id {}, Case Number {}",
          loadableStudy.getId(),
          lpd.getCaseNumber());
      loadablePatternRepository.deleteLoadablePattern(lp.get().getId());
    }
    loadablePatternRepository.save(loadablePattern);
    log.info(
        "Loadable Pattern Saved for LS Id {}, Case Number {}",
        loadableStudy.getId(),
        lpd.getCaseNumber());
    return loadablePattern;
  }

  /**
   * @param lpd
   * @param loadablePattern void
   */
  private void saveConstrains(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails lpd,
      LoadablePattern loadablePattern) {
    if (!lpd.getConstraintsList().isEmpty()) {
      lpd.getConstraintsList()
          .forEach(
              constrains -> {
                LoadablePlanConstraints constraints = new LoadablePlanConstraints();
                constraints.setConstraintsData(constrains);
                constraints.setIsActive(true);
                constraints.setLoadablePattern(loadablePattern);
                loadablePlanConstraintsRespository.save(constraints);
              });
    }
  }

  /**
   * @param loadableQuantityCommingleCargoDetailsList
   * @param loadablePattern void
   */
  public void saveLoadablePlanCommingleCargo(
      List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails>
          loadableQuantityCommingleCargoDetailsList,
      LoadablePattern loadablePattern) {
    for (int i = 0; i < loadableQuantityCommingleCargoDetailsList.size(); i++) {
      LoadablePlanCommingleDetails loadablePlanCommingleDetails =
          new LoadablePlanCommingleDetails();
      loadablePlanCommingleDetails.setApi(
          loadableQuantityCommingleCargoDetailsList.get(i).getApi());
      loadablePlanCommingleDetails.setCargo1Abbreviation(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo1Abbreviation());
      loadablePlanCommingleDetails.setCargo1Mt(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo1MT());
      loadablePlanCommingleDetails.setCargo1Percentage(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo1Percentage());
      loadablePlanCommingleDetails.setCargo2Abbreviation(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2Abbreviation());
      loadablePlanCommingleDetails.setCargo2Mt(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2MT());
      loadablePlanCommingleDetails.setCargo2Percentage(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2Percentage());
      loadablePlanCommingleDetails.setGrade(COMMINGLE + (i + 1));
      loadablePlanCommingleDetails.setIsActive(true);
      loadablePlanCommingleDetails.setLoadablePattern(loadablePattern);
      loadablePlanCommingleDetails.setQuantity(
          loadableQuantityCommingleCargoDetailsList.get(i).getQuantity());
      loadablePlanCommingleDetails.setTankName(
          loadableQuantityCommingleCargoDetailsList.get(i).getTankName());
      loadablePlanCommingleDetails.setTemperature(
          loadableQuantityCommingleCargoDetailsList.get(i).getTemp());
      loadablePlanCommingleDetails.setOrderQuantity(
          loadableQuantityCommingleCargoDetailsList.get(i).getOrderedMT());
      loadablePlanCommingleDetails.setPriority(
          loadableQuantityCommingleCargoDetailsList.get(i).getPriority());
      loadablePlanCommingleDetails.setLoadingOrder(
          loadableQuantityCommingleCargoDetailsList.get(i).getLoadingOrder());
      loadablePlanCommingleDetails.setTankId(
          loadableQuantityCommingleCargoDetailsList.get(i).getTankId());
      loadablePlanCommingleDetails.setFillingRatio(
          loadableQuantityCommingleCargoDetailsList.get(i).getFillingRatio());
      loadablePlanCommingleDetails.setCorrectionFactor(
          loadableQuantityCommingleCargoDetailsList.get(i).getCorrectionFactor());
      loadablePlanCommingleDetails.setCorrectedUllage(
          loadableQuantityCommingleCargoDetailsList.get(i).getCorrectedUllage());
      loadablePlanCommingleDetails.setRdgUllage(
          loadableQuantityCommingleCargoDetailsList.get(i).getRdgUllage());
      loadablePlanCommingleDetails.setSlopQuantity(
          loadableQuantityCommingleCargoDetailsList.get(i).getSlopQuantity());
      loadablePlanCommingleDetails.setTimeRequiredForLoading(
          loadableQuantityCommingleCargoDetailsList.get(i).getTimeRequiredForLoading());
      loadablePlanCommingleDetails.setCargo2NominationId(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2NominationId());
      loadablePlanCommingleDetails.setCargo2NominationId(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2NominationId());
      loadablePlanCommingleDetails.setTankShortName(
          loadableQuantityCommingleCargoDetailsList.get(i).getTankShortName());
      loadablePlanCommingleDetailsRepository.save(loadablePlanCommingleDetails);
      loadableQuantityCommingleCargoDetailsList
          .get(i)
          .getToppingOffSequencesList()
          .forEach(
              toppingSequence -> {
                LoadablePatternCargoToppingOffSequence lpctos =
                    new LoadablePatternCargoToppingOffSequence();
                lpctos.setCargoXId(toppingSequence.getCargoId());
                lpctos.setTankXId(toppingSequence.getTankId());
                lpctos.setOrderNumber(toppingSequence.getOrderNumber());
                lpctos.setLoadablePattern(loadablePattern);
                lpctos.setIsActive(true);
                toppingOffSequenceRepository.save(lpctos);
              });
    }
  }

  public com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder savePatternValidateResult(
      com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
      com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder builder)
      throws GenericServiceException {
    log.info("savePatternValidateResult - loadable study micro service");
    Optional<LoadablePattern> loadablePatternOpt =
        this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
    if (!loadablePatternOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable pattern does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    if (!request.getValidated()) {
      loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
          LOADABLE_PATTERN_VALIDATION_FAILED_ID, request.getProcesssId(), true);

      algoErrorsRepository.deleteAlgoError(false, request.getLoadablePatternId());
      algoErrorHeadingRepository.deleteAlgoErrorHeading(false, request.getLoadablePatternId());

      algoErrorService.saveAlgoErrorToDB(
          request, loadablePatternOpt.get(), new LoadableStudy(), true);

    } else {

      deleteExistingPlanDetails(loadablePatternOpt.get());

      Long lastLoadingPort =
          loadableStudyPortRotationService.getLastPort(
              loadablePatternOpt.get().getLoadableStudy(),
              this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));
      request
          .getLoadablePlanDetailsList()
          .forEach(
              lpd -> {
                Optional<com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails>
                    lppwdOptional =
                        lpd.getLoadablePlanPortWiseDetailsList().stream()
                            .filter(lppwd -> lppwd.getPortId() == lastLoadingPort)
                            .findAny();
                if (lppwdOptional.isPresent()) {
                  loadableQuantityService.saveLoadableQuantity(
                      lppwdOptional.get(), loadablePatternOpt.get());
                  saveLoadablePlanCommingleCargo(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadableQuantityCommingleCargoDetailsList(),
                      loadablePatternOpt.get());
                  saveLoadablePlanStowageDetails(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadablePlanStowageDetailsList(),
                      loadablePatternOpt.get());
                  saveLoadablePlanBallastDetails(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadablePlanBallastDetailsList(),
                      loadablePatternOpt.get());
                }
                AtomicInteger displayOrder = new AtomicInteger(0);
                saveLoadableQuantityCommingleCargoPortwiseDetails(
                    lpd.getLoadablePlanPortWiseDetailsList(),
                    loadablePatternOpt.get(),
                    displayOrder);
                saveLoadablePlanStowageDetails(loadablePatternOpt.get(), lpd, displayOrder);
                saveLoadablePlanBallastDetails(loadablePatternOpt.get(), lpd);
                saveStabilityParameterForNonLodicator(
                    request.getHasLodicator(), loadablePatternOpt.get(), lpd);
              });
      if (request.getHasLodicator()) {
        loadicatorService.saveLoadicatorInfo(
            loadablePatternOpt.get().getLoadableStudy(),
            request.getProcesssId(),
            request.getLoadablePatternId(),
            new ArrayList<LoadablePattern>());
      } else {
        loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
            LOADABLE_PATTERN_VALIDATION_SUCCESS_ID, request.getProcesssId(), true);
      }
    }

    builder
        .setResponseStatus(
            Common.ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
        .build();
    return builder;
  }

  /**
   * @param loadablePlanStowageDetailsList
   * @param loadablePattern void
   */
  private void saveLoadablePlanStowageDetails(
      List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails>
          loadablePlanStowageDetailsList,
      LoadablePattern loadablePattern) {
    loadablePlanStowageDetailsList.forEach(
        lpsd -> {
          LoadablePlanStowageDetails loadablePlanStowageDetails = new LoadablePlanStowageDetails();
          loadablePlanStowageDetails.setApi(lpsd.getApi());
          loadablePlanStowageDetails.setAbbreviation(lpsd.getCargoAbbreviation());
          loadablePlanStowageDetails.setColorCode(lpsd.getColorCode());
          loadablePlanStowageDetails.setFillingPercentage(lpsd.getFillingRatio());
          loadablePlanStowageDetails.setRdgUllage(lpsd.getRdgUllage());
          loadablePlanStowageDetails.setTankId(lpsd.getTankId());
          loadablePlanStowageDetails.setTankname(lpsd.getTankName());
          loadablePlanStowageDetails.setWeight(lpsd.getWeight());
          loadablePlanStowageDetails.setTemperature(lpsd.getTemperature());
          loadablePlanStowageDetails.setIsActive(true);
          loadablePlanStowageDetails.setLoadablePattern(loadablePattern);
          loadablePlanStowageDetails.setCorrectionFactor(lpsd.getCorrectionFactor());
          loadablePlanStowageDetails.setCorrectedUllage(lpsd.getCorrectedUllage());
          loadablePlanStowageDetails.setCargoNominationId(lpsd.getCargoNominationId());
          loadablePlanStowageDetails.setCargoNominationTemperature(
              new BigDecimal(lpsd.getCargoNominationTemperature()));
          loadablePlanStowageDetailsRespository.save(loadablePlanStowageDetails);
        });
  }

  /**
   * @param loadablePattern
   * @param lpd void
   */
  private void saveLoadablePlanBallastDetails(
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails lpd) {
    lpd.getLoadablePlanPortWiseDetailsList()
        .forEach(
            lppwd -> {
              LoadableStudyPortRotation loadableStudyPortRotation =
                  this.loadableStudyPortRotationRepository.getOne(lppwd.getPortRotationId());
              lppwd
                  .getArrivalCondition()
                  .getLoadablePlanBallastDetailsList()
                  .forEach(
                      lpbd -> {
                        saveLoadablePlanBallastDetailsOperationWise(
                            SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                            lpbd,
                            lppwd.getPortId(),
                            lppwd.getPortRotationId(),
                            loadablePattern);
                      });
              lppwd
                  .getDepartureCondition()
                  .getLoadablePlanBallastDetailsList()
                  .forEach(
                      lpbd -> {
                        saveLoadablePlanBallastDetailsOperationWise(
                            SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                            lpbd,
                            lppwd.getPortId(),
                            lppwd.getPortRotationId(),
                            loadablePattern);
                      });
            });
  }

  /**
   * @param loadablePlanBallastDetailsList
   * @param loadablePattern void
   */
  private void saveLoadablePlanBallastDetails(
      List<com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails>
          loadablePlanBallastDetailsList,
      LoadablePattern loadablePattern) {
    loadablePlanBallastDetailsList.forEach(
        lpbd -> {
          LoadablePlanBallastDetails loadablePlanBallastDetails = new LoadablePlanBallastDetails();
          loadablePlanBallastDetails.setMetricTon(lpbd.getMetricTon());
          loadablePlanBallastDetails.setPercentage(lpbd.getPercentage());
          loadablePlanBallastDetails.setSg(lpbd.getSg());
          loadablePlanBallastDetails.setTankName(lpbd.getTankName());
          loadablePlanBallastDetails.setTankId(lpbd.getTankId());
          loadablePlanBallastDetails.setRdgLevel(lpbd.getRdgLevel());
          loadablePlanBallastDetails.setIsActive(true);
          loadablePlanBallastDetails.setLoadablePattern(loadablePattern);
          loadablePlanBallastDetails.setColorCode(BALLAST_TANK_COLOR_CODE);
          loadablePlanBallastDetails.setCorrectedLevel(lpbd.getCorrectedLevel());
          loadablePlanBallastDetails.setCorrectionFactor(lpbd.getCorrectionFactor());
          loadablePlanBallastDetailsRepository.save(loadablePlanBallastDetails);
        });
  }

  /**
   * @param loadablePattern
   * @param lpd void
   * @param displayOrder
   */
  private void saveLoadablePlanStowageDetails(
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails lpd,
      AtomicInteger displayOrder) {
    lpd.getLoadablePlanPortWiseDetailsList()
        .forEach(
            lppwd -> {
              lppwd
                  .getArrivalCondition()
                  .getLoadablePlanStowageDetailsList()
                  .forEach(
                      lpsd -> {
                        saveLoadablePlanStowageDetailsOperationWise(
                            SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
                            lpsd,
                            lppwd.getPortId(),
                            lppwd.getPortRotationId(),
                            loadablePattern);
                      });
              lppwd
                  .getDepartureCondition()
                  .getLoadablePlanStowageDetailsList()
                  .forEach(
                      lpsd -> {
                        saveLoadablePlanStowageDetailsOperationWise(
                            SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
                            lpsd,
                            lppwd.getPortId(),
                            lppwd.getPortRotationId(),
                            loadablePattern);
                      });
              saveCargoToppingOffList(lppwd, loadablePattern, displayOrder);
            });
  }

  /**
   * @param synopticalTableOpTypeArrival
   * @param lpsd
   * @param portId void
   * @param loadablePattern
   */
  private void saveLoadablePlanStowageDetailsOperationWise(
      String synopticalTableOpTypeArrival,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails lpsd,
      long portId,
      Long portRotationId,
      LoadablePattern loadablePattern) {
    com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails loadablePatternCargoDetails =
        new com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails();
    loadablePatternCargoDetails.setIsActive(true);
    loadablePatternCargoDetails.setLoadablePatternId(loadablePattern.getId());
    loadablePatternCargoDetails.setOperationType(synopticalTableOpTypeArrival);
    loadablePatternCargoDetails.setPlannedQuantity(
        !StringUtils.isEmpty(lpsd.getWeight()) ? new BigDecimal(lpsd.getWeight()) : null);
    loadablePatternCargoDetails.setPortId(portId);
    loadablePatternCargoDetails.setPortRotationId(portRotationId);
    loadablePatternCargoDetails.setTankId(lpsd.getTankId());
    loadablePatternCargoDetails.setAbbreviation(lpsd.getCargoAbbreviation());
    loadablePatternCargoDetails.setApi(new BigDecimal(lpsd.getApi()));
    loadablePatternCargoDetails.setTemperature(new BigDecimal(lpsd.getTemperature()));
    loadablePatternCargoDetails.setColorCode(lpsd.getColorCode());
    loadablePatternCargoDetails.setCorrectionFactor(lpsd.getCorrectionFactor());
    loadablePatternCargoDetails.setCorrectedUllage(
        !StringUtils.isEmpty(lpsd.getCorrectedUllage())
            ? new BigDecimal(lpsd.getCorrectedUllage())
            : null);
    loadablePatternCargoDetails.setCargoNominationId(lpsd.getCargoNominationId());
    loadablePatternCargoDetails.setCargoNominationTemperature(
        new BigDecimal(lpsd.getCargoNominationTemperature()));
    loadablePatternCargoDetails.setFillingRatio(lpsd.getFillingRatio());
    loadablePatternCargoDetailsRepository.save(loadablePatternCargoDetails);
  }

  private void saveCargoToppingOffList(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails lppwd,
      LoadablePattern loadablePattern,
      AtomicInteger displayOrder) {
    lppwd
        .getDepartureCondition()
        .getLoadableQuantityCargoDetailsList()
        .forEach(
            lqcd -> {
              lqcd.getToppingOffSequencesList()
                  .forEach(
                      toppingSequence -> {
                        LoadablePatternCargoToppingOffSequence lpctos =
                            new LoadablePatternCargoToppingOffSequence();
                        lpctos.setCargoXId(toppingSequence.getCargoId());
                        lpctos.setTankXId(toppingSequence.getTankId());
                        lpctos.setOrderNumber(toppingSequence.getOrderNumber());
                        lpctos.setLoadablePattern(loadablePattern);
                        lpctos.setDisplayOrder(displayOrder.incrementAndGet());
                        lpctos.setPortRotationXId(lppwd.getPortRotationId());
                        lpctos.setIsActive(true);
                        toppingOffSequenceRepository.save(lpctos);
                      });
            });
  }

  /**
   * @param synopticalTableOpTypeDeparture
   * @param lpbd
   * @param portId
   * @param portRotationId
   * @param loadablePattern void
   */
  private void saveLoadablePlanBallastDetailsOperationWise(
      String synopticalTableOpTypeDeparture,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails lpbd,
      long portId,
      Long portRotationId,
      LoadablePattern loadablePattern) {
    LoadablePlanStowageBallastDetails loadablePlanStowageBallastDetails =
        new LoadablePlanStowageBallastDetails();
    loadablePlanStowageBallastDetails.setLoadablePatternId(loadablePattern.getId());
    loadablePlanStowageBallastDetails.setOperationType(synopticalTableOpTypeDeparture);
    loadablePlanStowageBallastDetails.setPortXId(portId);
    loadablePlanStowageBallastDetails.setPortRotationId(portRotationId);
    loadablePlanStowageBallastDetails.setQuantity(
        !StringUtils.isEmpty(lpbd.getMetricTon()) ? new BigDecimal(lpbd.getMetricTon()) : null);
    loadablePlanStowageBallastDetails.setTankXId(lpbd.getTankId());
    loadablePlanStowageBallastDetails.setIsActive(true);
    loadablePlanStowageBallastDetails.setColorCode(BALLAST_TANK_COLOR_CODE);
    loadablePlanStowageBallastDetails.setSg(lpbd.getSg());
    loadablePlanStowageBallastDetails.setCorrectedUllage(lpbd.getCorrectedLevel());
    loadablePlanStowageBallastDetails.setCorrectionFactor(lpbd.getCorrectionFactor());
    loadablePlanStowageBallastDetails.setRdgUllage(lpbd.getRdgLevel());
    loadablePlanStowageBallastDetails.setFillingPercentage(lpbd.getPercentage());
    loadablePlanStowageBallastDetailsRepository.save(loadablePlanStowageBallastDetails);
  }

  /**
   * save comminglo cargo portwise information into loadable_plan_commingle_details_portwise table *
   */
  private void saveLoadableQuantityCommingleCargoPortwiseDetails(
      List<com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails>
          loadablePlanPortWiseDetailsList,
      LoadablePattern loadablePattern,
      AtomicInteger displayOrder) {

    loadablePlanPortWiseDetailsList.forEach(
        it -> {
          saveLodableQtyCommingleCargoPortData(
              it.getPortId(),
              it.getPortRotationId(),
              SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL,
              it.getArrivalCondition().getLoadableQuantityCommingleCargoDetailsList(),
              loadablePattern,
              displayOrder);

          saveLodableQtyCommingleCargoPortData(
              it.getPortId(),
              it.getPortRotationId(),
              SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
              it.getDepartureCondition().getLoadableQuantityCommingleCargoDetailsList(),
              loadablePattern,
              displayOrder);
        });
  }

  private void saveLodableQtyCommingleCargoPortData(
      long portId,
      long portRotationXid,
      String operationType,
      List<com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails>
          loadableQuantityCommingleCargoDetailsList,
      LoadablePattern loadablePattern,
      AtomicInteger displayOrder) {

    if (Optional.ofNullable(loadableQuantityCommingleCargoDetailsList).isPresent()) {

      loadableQuantityCommingleCargoDetailsList.forEach(
          it -> {
            LoadablePlanComminglePortwiseDetails loadablePlanComminglePortwiseDetails =
                LoadablePlanComminglePortwiseDetails.builder()
                    .portId(portId)
                    .operationType(operationType)
                    .api(it.getApi())
                    .cargo1Abbreviation(it.getCargo1Abbreviation())
                    .cargo1Mt(it.getCargo1MT())
                    .cargo1Percentage(it.getCargo1Percentage())
                    .cargo2Abbreviation(it.getCargo2Abbreviation())
                    .cargo2Mt(it.getCargo2MT())
                    .cargo2Percentage(it.getCargo2Percentage())
                    .grade(it.getGrade())
                    .isActive(true)
                    .loadablePattern(loadablePattern)
                    .quantity(it.getQuantity())
                    .tankName(it.getTankName())
                    .temperature(it.getTemp())
                    .orderQuantity(it.getOrderedMT())
                    .priority(it.getPriority())
                    .loadingOrder(it.getLoadingOrder())
                    .tankId(it.getTankId())
                    .fillingRatio(it.getFillingRatio())
                    .correctionFactor(it.getCorrectionFactor())
                    .correctedUllage(it.getCorrectedUllage())
                    .rdgUllage(it.getRdgUllage())
                    .cargo1NominationId(it.getCargo1NominationId())
                    .cargo2NominationId(it.getCargo2NominationId())
                    .portRotationXid(portRotationXid)
                    .tankName(it.getTankShortName())
                    // .actualQuantity(it.getActualQuantity()!=  null ? new
                    // BigDecimal(it.getActualQuantity()): null)
                    .build();

            loadablePlanCommingleDetailsPortwiseRepository.save(
                loadablePlanComminglePortwiseDetails);

            if (operationType.equals(SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE)) {
              it.getToppingOffSequencesList()
                  .forEach(
                      toppingSequence -> {
                        LoadablePatternCargoToppingOffSequence lpctos =
                            new LoadablePatternCargoToppingOffSequence();
                        lpctos.setCargoXId(toppingSequence.getCargoId());
                        lpctos.setTankXId(toppingSequence.getTankId());
                        lpctos.setOrderNumber(toppingSequence.getOrderNumber());
                        lpctos.setLoadablePattern(loadablePattern);
                        lpctos.setDisplayOrder(displayOrder.incrementAndGet());
                        lpctos.setPortRotationXId(portRotationXid);
                        lpctos.setIsActive(true);
                        toppingOffSequenceRepository.save(lpctos);
                      });
            }
          });
    }
  }

  /**
   * @param loadablePattern
   * @param lpd void
   * @param lastLoadingPort
   */
  private void saveStabilityParameters(
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails lpd,
      Long lastLoadingPort) {
    log.info("saving stability parameter to DB");
    StabilityParameters stabilityParameter = new StabilityParameters();
    stabilityParameter.setAftDraft(lpd.getStabilityParameters().getAfterDraft());
    stabilityParameter.setBendingMoment(lpd.getStabilityParameters().getBendinMoment());
    stabilityParameter.setFwdDraft(lpd.getStabilityParameters().getForwardDraft());
    stabilityParameter.setHeal(lpd.getStabilityParameters().getHeel());
    stabilityParameter.setLoadablePattern(loadablePattern);
    stabilityParameter.setMeanDraft(lpd.getStabilityParameters().getMeanDraft());
    stabilityParameter.setPortXid(lastLoadingPort);
    stabilityParameter.setShearingForce(lpd.getStabilityParameters().getShearForce());
    stabilityParameter.setTrimValue(lpd.getStabilityParameters().getTrim());
    stabilityParameter.setIsActive(true);
    stabilityParameterRepository.save(stabilityParameter);
  }

  private void saveStabilityParameterForNonLodicator(
      boolean hasLodicator,
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails lpd) {

    if (!hasLodicator) {
      for (com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails portWiseDetails :
          lpd.getLoadablePlanPortWiseDetailsList()) {
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply arrivalCondition =
            portWiseDetails.getArrivalCondition();
        if (Optional.ofNullable(arrivalCondition).isPresent()) {
          loadicatorService.saveLodicatorDataForSynoptical(
              loadablePattern, arrivalCondition, lpd, "ARR", portWiseDetails.getPortRotationId());
        }

        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply departureCondition =
            portWiseDetails.getDepartureCondition();
        if (Optional.ofNullable(departureCondition).isPresent()) {
          loadicatorService.saveLodicatorDataForSynoptical(
              loadablePattern, departureCondition, lpd, "DEP", portWiseDetails.getPortRotationId());
        }
      }
    }
  }

  /** @param loadablePattern */
  public void deleteExistingPlanDetails(LoadablePattern loadablePattern) {
    stowageDetailsTempRepository.deleteLoadablePlanStowageDetailsTemp(
        false, loadablePattern.getId());
    loadablePlanQuantityRepository.deleteLoadablePlanQuantity(false, loadablePattern.getId());
    loadablePlanCommingleDetailsRepository.deleteLoadablePlanCommingleDetails(
        false, loadablePattern.getId());
    loadablePlanStowageDetailsRespository.deleteLoadablePlanStowageDetails(
        false, loadablePattern.getId());
    loadablePatternCargoDetailsRepository.deleteLoadablePatternCargoDetails(
        false, loadablePattern.getId());
    loadablePlanBallastDetailsRepository.deleteLoadablePlanBallastDetails(
        false, loadablePattern.getId());
    loadablePlanStowageBallastDetailsRepository.deleteLoadablePlanStowageBallastDetails(
        false, loadablePattern.getId());
    synopticalTableLoadicatorDataRepository.deleteByLoadablePatternId(
        false, loadablePattern.getId());
  }

  public com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder
      getLoadablePatternList(
          com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder replyBuilder)
          throws GenericServiceException {
    log.info("getLoadablePatternList - loadable study micro service");
    Optional<LoadableStudy> loadableStudyOpt =
        this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (!loadableStudyOpt.isPresent()) {
      throw new GenericServiceException(
          "Loadable study does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    List<LoadablePattern> patterns =
        this.loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
            loadableStudyOpt.get(), true);
    if (null != patterns && !patterns.isEmpty()) {
      this.buildPatternDetails(patterns, replyBuilder);
    }
    replyBuilder.setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return replyBuilder;
  }

  /**
   * Build pattern reply
   *
   * @param patterns
   * @param replyBuilder
   */
  private void buildPatternDetails(
      List<LoadablePattern> patterns,
      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.Builder replyBuilder) {
    for (LoadablePattern pattern : patterns) {
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder builder =
          com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
      builder.setLoadablePatternId(pattern.getId());
      builder.setLoadableStudyStatusId(pattern.getLoadableStudyStatus());
      ofNullable(pattern.getCaseNumber()).ifPresent(item -> builder.setCaseNumber(item));
      replyBuilder.addLoadablePattern(builder.build());
    }
  }
}
