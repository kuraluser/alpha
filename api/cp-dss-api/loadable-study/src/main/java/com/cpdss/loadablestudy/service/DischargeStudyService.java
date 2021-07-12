/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.CONFIRMED_STATUS_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.DISCHARGING_OPERATION_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.FAILED;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.LOADABLE_STUDY_INITIAL_STATUS_ID;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.DischargeStudyOperationServiceGrpc.DischargeStudyOperationServiceImplBase;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyDetail;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyStatusRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/** @author arun.j */
@Log4j2
@GrpcService
@Transactional
public class DischargeStudyService extends DischargeStudyOperationServiceImplBase {

  @Autowired private LoadableStudyRepository dischargeStudyRepository;
  @Autowired VoyageService voyageService;
  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @Autowired private VoyageRepository voyageRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;
  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @Autowired private CargoNominationService cargoNominationService;

  @Override
  public void saveDischargeStudy(
      DischargeStudyDetail request, StreamObserver<DischargeStudyReply> responseObserver) {
    DischargeStudyReply.Builder builder = DischargeStudyReply.newBuilder();
    try {
      Voyage voyage = this.voyageRepository.findByIdAndIsActive(request.getVoyageId(), true);
      if (null == voyage) {
        throw new GenericServiceException(
            "Voyage does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      if (dischargeStudyRepository.existsByNameAndPlanningTypeXIdAndVoyageAndIsActive(
          request.getName(), 2, voyage, true)) {
        throw new GenericServiceException(
            "name already exists",
            CommonErrorCodes.E_CPDSS_LS_NAME_EXISTS,
            HttpStatusCode.BAD_REQUEST);
      }

      List<LoadableStudy> loadables =
          this.dischargeStudyRepository
              .findByVesselXIdAndVoyageAndIsActiveAndLoadableStudyStatus_id(
                  request.getVesselId(), voyage, true, CONFIRMED_STATUS_ID);
      if (loadables.isEmpty()) {
        throw new GenericServiceException(
            "No confirmed loadable study",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudy loadableStudy = loadables.get(0);
      LoadableStudyPortRotation loadableStudyPortRotation = getportRotationData(loadableStudy);
      List<SynopticalTable> synopticalData =
          this.synopticalTableRepository
              .findByLoadableStudyXIdAndLoadableStudyPortRotation_idAndIsActive(
                  loadableStudy.getId(), loadableStudyPortRotation.getId(), true);
      if (synopticalData.isEmpty()) {
        throw new GenericServiceException(
            "synoptical data missing",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      List<OnHandQuantity> onhandQuantity =
          this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
              loadableStudy, loadableStudyPortRotation, true);
      if (onhandQuantity.isEmpty()) {
        throw new GenericServiceException(
            "on hand quantity data missing",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }

      LoadableStudy savedDischargeStudy = saveDischargeStudy(request, voyage);
      LoadableStudyPortRotation dischargeStudyPortRotation =
          createDischargeStudyPortRotationData(loadableStudyPortRotation, savedDischargeStudy);
      LoadableStudyPortRotation savedDischargeport =
          loadableStudyPortRotationRepository.save(dischargeStudyPortRotation);
      List<CargoNomination> dischargeCargos =
          this.cargoNominationService.saveDsichargeStudyCargoNominations(
              savedDischargeStudy.getId(), loadableStudy.getId(), savedDischargeport.getPortXId());

      this.synopticalTableRepository.saveAll(
          createDischargeSynoptical(synopticalData, savedDischargeport));
      this.onHandQuantityRepository.saveAll(
          createDischargeOnHandQuantity(onhandQuantity, savedDischargeport));
      builder.setId(savedDischargeStudy.getId());
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    } catch (GenericServiceException e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * duplicate the on hand quantity object for discharge study
   *
   * @param onhandQuantity data for discharge study
   * @param savedDischargeport data from loadable study
   * @return
   */
  private List<OnHandQuantity> createDischargeOnHandQuantity(
      List<OnHandQuantity> onhandQuantity, LoadableStudyPortRotation savedDischargeport) {
    List<OnHandQuantity> dischargeOHQList = new ArrayList<>();
    onhandQuantity.forEach(
        ohq -> {
          OnHandQuantity dischargeOHQ = new OnHandQuantity();
          dischargeOHQ.setActualArrivalQuantity(ohq.getActualArrivalQuantity());
          dischargeOHQ.setActualDepartureQuantity(ohq.getActualDepartureQuantity());
          dischargeOHQ.setArrivalQuantity(ohq.getArrivalQuantity());
          dischargeOHQ.setArrivalVolume(ohq.getArrivalVolume());
          dischargeOHQ.setDensity(ohq.getDensity());
          dischargeOHQ.setDepartureQuantity(ohq.getDepartureQuantity());
          dischargeOHQ.setDepartureVolume(ohq.getDepartureVolume());
          dischargeOHQ.setFuelTypeXId(ohq.getFuelTypeXId());
          dischargeOHQ.setIsActive(ohq.getIsActive());
          dischargeOHQ.setLoadableStudy(savedDischargeport.getLoadableStudy());
          dischargeOHQ.setPortRotation(savedDischargeport);
          dischargeOHQ.setPortXId(ohq.getPortXId());
          dischargeOHQ.setTankXId(ohq.getTankXId());
          dischargeOHQList.add(dischargeOHQ);
        });
    return dischargeOHQList;
  }

  /**
   * duplicate the synoptical table object for discharge study
   *
   * @param synopticalData data for discharge study
   * @param entity data from loadable study
   * @return
   */
  private List<SynopticalTable> createDischargeSynoptical(
      List<SynopticalTable> synopticalData, LoadableStudyPortRotation entity) {
    List<SynopticalTable> dischargeSynopticalList = new ArrayList<>();
    synopticalData.forEach(
        data -> {
          SynopticalTable dischargeSynoptical = new SynopticalTable();
          dischargeSynoptical.setConstantActual(data.getConstantActual());
          dischargeSynoptical.setConstantPlanned(data.getConstantPlanned());
          dischargeSynoptical.setDeadWeightActual(data.getDeadWeightActual());
          dischargeSynoptical.setDeadWeightPlanned(data.getDeadWeightPlanned());
          dischargeSynoptical.setDisplacementActual(data.getDisplacementActual());
          dischargeSynoptical.setDisplacementPlanned(data.getDisplacementPlanned());
          dischargeSynoptical.setDistance(data.getDistance());
          dischargeSynoptical.setEtaActual(data.getEtaActual());
          dischargeSynoptical.setEtdActual(data.getEtdActual());
          dischargeSynoptical.setHwTideFrom(data.getHwTideFrom());
          dischargeSynoptical.setHwTideTimeFrom(data.getHwTideTimeFrom());
          dischargeSynoptical.setHwTideTimeTo(data.getHwTideTimeTo());
          dischargeSynoptical.setHwTideTo(data.getHwTideTo());
          dischargeSynoptical.setInPortHours(data.getInPortHours());
          dischargeSynoptical.setIsActive(data.getIsActive());
          dischargeSynoptical.setLoadableStudyPortRotation(entity);
          dischargeSynoptical.setLoadableStudyXId(entity.getLoadableStudy().getId());
          dischargeSynoptical.setLwTideFrom(data.getLwTideFrom());
          dischargeSynoptical.setLwTideTimeFrom(data.getLwTideTimeFrom());
          dischargeSynoptical.setLwTideTimeTo(data.getLwTideTimeTo());
          dischargeSynoptical.setLwTideTo(data.getLwTideTo());
          dischargeSynoptical.setOperationType(data.getOperationType());
          dischargeSynoptical.setOthersActual(data.getOthersActual());
          dischargeSynoptical.setOthersPlanned(data.getOthersPlanned());
          dischargeSynoptical.setPortXid(data.getPortXid());
          dischargeSynoptical.setRunningHours(data.getRunningHours());
          dischargeSynoptical.setSpeed(data.getSpeed());
          dischargeSynoptical.setTimeOfSunrise(data.getTimeOfSunrise());
          dischargeSynoptical.setTimeOfSunSet(data.getTimeOfSunSet());
          dischargeSynopticalList.add(dischargeSynoptical);
        });

    return dischargeSynopticalList;
  }
  /**
   * discharge study save
   *
   * @param request data to set to discharge study
   * @param voyage current voyage
   * @return
   */
  private LoadableStudy saveDischargeStudy(DischargeStudyDetail request, Voyage voyage) {
    LoadableStudy dischargeStudy = new LoadableStudy();
    dischargeStudy.setName(request.getName());
    dischargeStudy.setDetails(request.getEnquiryDetails());
    dischargeStudy.setActive(true);
    dischargeStudy.setVesselXId(request.getVesselId());
    dischargeStudy.setVoyage(voyage);
    dischargeStudy.setPlanningTypeXId(2);
    dischargeStudy.setLoadableStudyStatus(
        loadableStudyStatusRepository.getOne(LOADABLE_STUDY_INITIAL_STATUS_ID));
    LoadableStudy savedDischargeStudy = dischargeStudyRepository.save(dischargeStudy);
    return savedDischargeStudy;
  }

  private LoadableStudyPortRotation getportRotationData(LoadableStudy loadableStudy)
      throws GenericServiceException {
    List<LoadableStudyPortRotation> portrotation =
        loadableStudyPortRotationRepository.findByLoadableStudyAndOperation_idAndIsActive(
            loadableStudy, DISCHARGING_OPERATION_ID, true);
    if (portrotation.isEmpty()) {
      throw new GenericServiceException(
          "No discharging port in port rotation against loadable study",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    portrotation.sort(Comparator.comparing(LoadableStudyPortRotation::getPortOrder).reversed());
    return portrotation.get(0);
  }

  private LoadableStudyPortRotation createDischargeStudyPortRotationData(
      LoadableStudyPortRotation loadableStudyPortRotation, LoadableStudy savedDischargeStudy) {
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setActive(loadableStudyPortRotation.isActive());
    portRotation.setAirDraftRestriction(loadableStudyPortRotation.getAirDraftRestriction());
    portRotation.setBerthXId(loadableStudyPortRotation.getBerthXId());
    portRotation.setDistanceBetweenPorts(loadableStudyPortRotation.getDistanceBetweenPorts());
    portRotation.setEta(loadableStudyPortRotation.getEta());
    portRotation.setEtd(loadableStudyPortRotation.getEtd());
    portRotation.setIsPortRotationOhqComplete(
        loadableStudyPortRotation.getIsPortRotationOhqComplete());
    portRotation.setLayCanFrom(loadableStudyPortRotation.getLayCanFrom());
    portRotation.setLayCanTo(loadableStudyPortRotation.getLayCanTo());
    portRotation.setLoadableStudy(savedDischargeStudy);
    portRotation.setMaxDraft(loadableStudyPortRotation.getMaxDraft());
    portRotation.setOperation(loadableStudyPortRotation.getOperation());
    portRotation.setPortOrder(loadableStudyPortRotation.getPortOrder());
    portRotation.setPortXId(loadableStudyPortRotation.getPortXId());
    portRotation.setSeaWaterDensity(loadableStudyPortRotation.getSeaWaterDensity());
    portRotation.setSynopticalTable(loadableStudyPortRotation.getSynopticalTable());
    portRotation.setTimeOfStay(loadableStudyPortRotation.getTimeOfStay());
    portRotation.setVersion(loadableStudyPortRotation.getVersion());
    return portRotation;
  }

  @Override
  public void updateDischargeStudy(
      DischargeStudyDetail request, StreamObserver<UpdateDischargeStudyReply> responseObserver) {
    UpdateDischargeStudyReply.Builder builder = UpdateDischargeStudyReply.newBuilder();
    try {
      LoadableStudy loadable =
          this.dischargeStudyRepository.findById(request.getDischargeStudyId()).orElse(null);
      if (loadable == null) {
        throw new GenericServiceException(
            "No discharge study found",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      loadable.setName(request.getName());
      loadable.setDetails(request.getEnquiryDetails());
      LoadableStudy updatedDischarge = this.dischargeStudyRepository.save(loadable);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      UpdateDischargeStudyDetail.Builder detail = UpdateDischargeStudyDetail.newBuilder();
      detail.setId(updatedDischarge.getId());
      detail.setName(updatedDischarge.getName());
      detail.setEnquiryDetails(updatedDischarge.getDetails());
      builder.setDischargeStudy(detail.build());
    } catch (GenericServiceException e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      e.printStackTrace();
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      e.printStackTrace();
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void deleteDischargeStudy(
      DischargeStudyRequest request, StreamObserver<DischargeStudyReply> responseObserver) {
    DischargeStudyReply.Builder replyBuilder = DischargeStudyReply.newBuilder();
    try {
      Optional<LoadableStudy> entityOpt =
          this.dischargeStudyRepository.findById(request.getDischargeStudyId());
      if (!entityOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudy entity = entityOpt.get();

      this.voyageService.checkIfVoyageClosed(entity.getVoyage().getId());

      entity.setActive(false);
      this.dischargeStudyRepository.save(entity);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting discharge study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Exception when deleting discharge study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception  when deleting discharge study")
              .setStatus(FAILED)
              .setHttpStatusCode(Integer.valueOf(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
