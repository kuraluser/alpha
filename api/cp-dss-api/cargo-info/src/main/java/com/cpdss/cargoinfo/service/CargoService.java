/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.service;

import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.cargoinfo.repository.CargoRepository;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoDetail;
import com.cpdss.common.generated.CargoInfo.CargoListRequest;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceImplBase;
import com.cpdss.common.generated.Common.ResponseStatus;
import io.grpc.stub.StreamObserver;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/** Service with operations related to cargo information */
@Log4j2
@GrpcService
@Transactional
public class CargoService extends CargoInfoServiceImplBase {

  @Autowired private CargoRepository cargoRepository;

  /** retrieves cargo info from cargo master */
  @Override
  public void getCargoInfo(CargoRequest request, StreamObserver<CargoReply> responseObserver) {
    CargoReply.Builder cargoReply = CargoReply.newBuilder();
    try {
      List<Cargo> cargoList = cargoRepository.findAll();
      cargoList =
          cargoList.stream()
              .sorted(Comparator.comparing(Cargo::getCrudeType, String.CASE_INSENSITIVE_ORDER))
              .collect(Collectors.toList());
      cargoList.forEach(
          cargo -> {
            CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
            buildCargoDetail(cargo, cargoDetail);
            cargoReply.addCargos(cargoDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getCargoInfo method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      cargoReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(cargoReply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getCargoInfoByPaging(
      CargoInfo.CargoRequestWithPaging request, StreamObserver<CargoReply> responseObserver) {
    CargoReply.Builder cargoReply = CargoReply.newBuilder();
    Pageable pageable = PageRequest.of((int) request.getOffset(), (int) request.getLimit());
    try {
      Page<Cargo> cargoPage = cargoRepository.findAll(pageable);
      cargoPage.forEach(
          cargo -> {
            CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
            buildCargoDetail(cargo, cargoDetail);
            cargoReply.addCargos(cargoDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getCargoInfoByPage method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      cargoReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(cargoReply.build());
      responseObserver.onCompleted();
    }
  }

  private void buildCargoDetail(Cargo cargo, CargoDetail.Builder cargoDetail) {
    if (cargo.getId() != null) {
      cargoDetail.setId(cargo.getId());
    }
    if (!StringUtils.isEmpty(cargo.getApi())) {
      cargoDetail.setApi(cargo.getApi());
    }
    if (!StringUtils.isEmpty(cargo.getAbbreviation())) {
      cargoDetail.setAbbreviation(cargo.getAbbreviation());
    }
    if (!StringUtils.isEmpty(cargo.getCrudeType())) {
      cargoDetail.setCrudeType(cargo.getCrudeType());
    }

    Optional.ofNullable(cargo.getIsCondensateCargo()).ifPresent(cargoDetail::setIsCondensateCargo);
    Optional.ofNullable(cargo.getIsCondensateCargo()).ifPresent(cargoDetail::setIsHrvpCargo);
  }

  @Override
  public void getCargoInfoById(
      CargoRequest request, StreamObserver<CargoInfo.CargoDetailReply> responseObserver) {
    CargoInfo.CargoDetailReply.Builder cargoReply = CargoInfo.CargoDetailReply.newBuilder();
    try {
      Cargo cargo = cargoRepository.getOne(request.getCargoId());
      CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
      buildCargoDetail(cargo, cargoDetail);
      cargoReply.setCargoDetail(cargoDetail);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getCargoInfoById method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      cargoReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(cargoReply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getCargoInfosByCargoIds(
      CargoListRequest request, StreamObserver<CargoReply> responseObserver) {

    CargoReply.Builder cargoReply = CargoReply.newBuilder();
    try {
      List<Cargo> cargos = cargoRepository.findByIdIn(request.getIdList());
      cargos.forEach(
          cargo -> {
            CargoDetail.Builder cargoDetail = CargoDetail.newBuilder();
            buildCargoDetail(cargo, cargoDetail);
            cargoReply.addCargos(cargoDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getCargoInfoByPage method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      cargoReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(cargoReply.build());
      responseObserver.onCompleted();
    }
  }
}
