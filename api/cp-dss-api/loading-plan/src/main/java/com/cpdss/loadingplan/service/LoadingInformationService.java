/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DownloadTideDetailRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UploadTideDetailRequest;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** Master service for the Loading Information */
public interface LoadingInformationService {

  public LoadingInformation saveLoadingInformationDetail(
      LoadingInformationDetail loadingInformationDetail, LoadingInformation loadingInformation);

  public void deleteLoadablePlanDetails(LoadingInformation loadingInformation);

  Optional<LoadingInformation> getLoadingInformation(
      Long id, Long vesselId, Long voyageId, Long patternId, Long portRotationId);

  /**
   * Fetch Single Data by Id
   *
   * @param id Loading Information Id
   * @return Optional of Object
   */
  Optional<LoadingInformation> getLoadingInformation(Long id);

  LoadingPlanModels.LoadingInformation getLoadingInformation(
      LoadingPlanModels.LoadingInformationRequest request,
      LoadingPlanModels.LoadingInformation.Builder response)
      throws GenericServiceException;

  LoadingInformation saveLoadingInformation(LoadingPlanModels.LoadingInformation loadingInformation)
      throws Exception;

  LoadingInformation saveLoadingInfoRates(
      LoadingPlanModels.LoadingRates loadingRates,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException;

  LoadingInformation saveLoadingInfoBerths(
      List<LoadingPlanModels.LoadingBerths> berths,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException;

  LoadingInformation saveLoadingInfoMachines(
      List<LoadingPlanModels.LoadingMachinesInUse> machines,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException;

  LoadingInformation saveLoadingInfoDelays(
      List<LoadingPlanModels.LoadingDelay> loadingDelays,
      LoadingInformation loadingInformation,
      LoadingPlanModels.LoadingInfoSaveResponse.Builder response)
      throws GenericServiceException;

  LoadingInformation saveLoadingInfoStages(
      LoadingPlanModels.LoadingStages loadingStage, LoadingInformation loadingInformation);

  void updateIsLoadingInfoCompeteStatus(Long loadingInfoId, boolean status);

  void uploadPortTideDetails(UploadTideDetailRequest request) throws GenericServiceException;

  void downloadPortTideDetails(
      XSSFWorkbook workbook,
      DownloadTideDetailRequest request,
      LoadingPlanModels.DownloadTideDetailStatusReply.Builder builder)
      throws GenericServiceException, IOException;
}
