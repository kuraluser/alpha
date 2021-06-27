/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.BillOfLadding;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@Transactional
@Service
public class LoadingInformationDischargeService {

  @Autowired BillOfLaddingRepository billOfLaddingRepository;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  /**
   * @param request
   * @param builder void
   */
  public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply
          .Builder
      getLoadigInformationByVoyage(
          LoadingInformationSynopticalRequest request,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                  .LoadingInformationSynopticalReply.Builder
              builder)
          throws GenericServiceException {
    List<BillOfLadding> billOfLaddings =
        billOfLaddingRepository.findByLoadablePatternXIdAndIsActive(
            request.getLoadablePatternId(), true);
    if (billOfLaddings.isEmpty()) {
      log.info(
          "getDischargeStudyByVoyage in getDischargeStudyByVoyage in LoadingInformationDischargeService");
      throw new GenericServiceException(
          "BillOfLadding does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    buildBillOfLading(billOfLaddings, builder);
    List<Common.BillOfLadding> billOfLaddingList = builder.getBillOfLaddingList();
    Map<String, List<Common.BillOfLadding>> billOfLaddingByCargo =
        billOfLaddingList.stream()
            .collect(Collectors.groupingBy(Common.BillOfLadding::getCargoColor));
    builder.clearBillOfLadding();
    billOfLaddingByCargo
        .entrySet()
        .forEach(
            entry -> {
              com.cpdss.common.generated.Common.BillOfLadding.Builder bolBuilder =
                  com.cpdss.common.generated.Common.BillOfLadding.newBuilder();
              List<Common.BillOfLadding> BillOfLaddingList = entry.getValue();
              Common.BillOfLadding billOfLadding = BillOfLaddingList.get(0);
              bolBuilder.setCargoAbbrevation(billOfLadding.getCargoAbbrevation());
              bolBuilder.setCargoColor(billOfLadding.getCargoColor());
              bolBuilder.setCargoName(billOfLadding.getCargoName());
              bolBuilder.setCargoId(billOfLadding.getCargoId());
              bolBuilder.setLoadingPort(billOfLadding.getLoadingPort());
              Double qtyBbls =
                  BillOfLaddingList.stream()
                      .mapToDouble(o -> Double.parseDouble(o.getQuantityBbls()))
                      .sum();
              bolBuilder.setQuantityBbls(String.valueOf(qtyBbls));
              Double qtyKl =
                  BillOfLaddingList.stream()
                      .mapToDouble(o -> Double.parseDouble(o.getQuantityKl()))
                      .sum();
              bolBuilder.setQuantityKl(String.valueOf(qtyKl));
              Double qtyMT =
                  BillOfLaddingList.stream()
                      .mapToDouble(o -> Double.parseDouble(o.getQuantityMt()))
                      .sum();
              bolBuilder.setQuantityMt(String.valueOf(qtyMT));
              Double api =
                  BillOfLaddingList.stream()
                      .mapToDouble(o -> Double.parseDouble(o.getApi()))
                      .average()
                      .getAsDouble();
              bolBuilder.setApi(String.valueOf(api));
              Double temperature =
                  BillOfLaddingList.stream()
                      .mapToDouble(o -> Double.parseDouble(o.getTemperature()))
                      .average()
                      .getAsDouble();
              bolBuilder.setTemperature(String.valueOf(temperature));
              builder.addBillOfLadding(bolBuilder);
            });
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
          ofNullable(billOfLadding.getCargoNominationId())
              .ifPresent(cargoId -> bolBuilder.setCargoNominationId(cargoId));
          ofNullable(billOfLadding.getPortId()).ifPresent(portId -> bolBuilder.setPortId(portId));
          if (bolBuilder.getPortId() != 0L) {
            LoadableStudy.LoadingPortDetail.Builder loadingPortDetail =
                this.fetchPortNameFromPortService(bolBuilder.getPortId());
            if (loadingPortDetail != null) {
              bolBuilder.setLoadingPort(loadingPortDetail.getName());
            }
          }
          buildCargoDetails(bolBuilder.getCargoNominationId(), bolBuilder);

          ofNullable(billOfLadding.getId()).ifPresent(id -> bolBuilder.setId(id));

          builder.addBillOfLadding(bolBuilder);
        });
  }

  public LoadableStudy.LoadingPortDetail.Builder fetchPortNameFromPortService(Long portId) {
    PortInfo.GetPortInfoByPortIdsRequest.Builder builder =
        PortInfo.GetPortInfoByPortIdsRequest.newBuilder();
    builder.addAllId(Arrays.asList(portId));
    PortInfo.PortReply grpcReplay = portInfoGrpcService.getPortInfoByPortIds(builder.build());
    if (grpcReplay.getResponseStatus().getStatus().equals(SUCCESS)) {
      Optional<PortInfo.PortDetail> portInfo = grpcReplay.getPortsList().stream().findFirst();
      if (portInfo.isPresent()) {
        LoadableStudy.LoadingPortDetail.Builder portsBuilder =
            LoadableStudy.LoadingPortDetail.newBuilder();
        log.info("Port Info Service, port id {} and name {}", portId, portInfo.get().getName());
        portsBuilder.setName(portInfo.get().getName()).setPortId(portInfo.get().getId()).build();
        return portsBuilder;
      }
    }
    return null;
  }

  public void buildCargoDetails(Long cargoNominationId, Common.BillOfLadding.Builder bolBuilder) {
    LoadableStudy.CargoNominationRequest.Builder builder =
        LoadableStudy.CargoNominationRequest.newBuilder();
    builder.setCargoNominationId(cargoNominationId);
    LoadableStudy.CargoNominationDetailReply grpcReplay =
        this.loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
    if (grpcReplay.getResponseStatus().getStatus().equals(SUCCESS)) {
      LoadableStudy.CargoNominationDetail cargoNominationdetail =
          grpcReplay.getCargoNominationdetail();
      if (cargoNominationdetail != null) {
        bolBuilder.setCargoAbbrevation(cargoNominationdetail.getAbbreviation());
        bolBuilder.setCargoColor(cargoNominationdetail.getColor());
        bolBuilder.setCargoName(cargoNominationdetail.getCargoName());
        bolBuilder.setCargoId(cargoNominationdetail.getCargoId());
      }
    }
  }
}
