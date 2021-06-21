/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.BillOfLadding;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@Transactional
@Service
public class LoadingInformationDischargeService {

  @Autowired BillOfLaddingRepository billOfLaddingRepository;

  /**
   * @param request
   * @param builder void
   */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply
          .Builder
      getLoadigInformationBySynoptical(
          LoadingInformationSynopticalRequest request,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingInformationSynopticalReply.Builder
              builder)
          throws GenericServiceException {
    List<BillOfLadding> billOfLaddings =
        billOfLaddingRepository.findBySynopticalTableXIdAndIsActive(
            request.getSynopticalId(), true);
    if (billOfLaddings.isEmpty()) {
      log.info(
          "getDischargeStudyByVoyage in getDischargeStudyByVoyage in LoadingInformationDischargeService");
      throw new GenericServiceException(
          "BillOfLadding does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    buildBillOfLading(billOfLaddings, builder);
    builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    return builder;
  }

  /**
   * @param billOfLaddings
   * @param builder void
   */
  private void buildBillOfLading(
      List<BillOfLadding> billOfLaddings,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply
              .Builder
          builder) {
    billOfLaddings.forEach(
        billOfLadding -> {
          com.cpdss.common.generated.Common.BillOfLadding.Builder bolBuilder =
              com.cpdss.common.generated.Common.BillOfLadding.newBuilder();

          ofNullable(billOfLadding.getApi()).ifPresent(api -> bolBuilder.setApi(valueOf(api)));
          ofNullable(billOfLadding.getTemperature())
              .ifPresent(temp -> bolBuilder.setTemperature(valueOf(temp)));
          ofNullable(billOfLadding.getQuantityBbls())
              .ifPresent(bbls -> bolBuilder.setQuantityBbls(valueOf(bbls)));
          ofNullable(billOfLadding.getQuantityKl())
              .ifPresent(kl -> bolBuilder.setQuantityKl(valueOf(kl)));
          ofNullable(billOfLadding.getQuantityMt())
              .ifPresent(mt -> bolBuilder.setQuantityMt(valueOf(mt)));
          ofNullable(billOfLadding.getCargoId())
              .ifPresent(cargoId -> bolBuilder.setCargoId(cargoId));
          ofNullable(billOfLadding.getPortId()).ifPresent(portId -> bolBuilder.setPortId(portId));
          ofNullable(billOfLadding.getId()).ifPresent(id -> bolBuilder.setId(id));

          builder.addBillOfLadding(bolBuilder);
        });
  }
}
