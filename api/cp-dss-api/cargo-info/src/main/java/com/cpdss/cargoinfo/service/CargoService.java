/* Licensed at AlphaOri Technologies */
package com.cpdss.cargoinfo.service;

import com.cpdss.cargoinfo.domain.CargoSpecification;
import com.cpdss.cargoinfo.domain.FilterCriteria;
import com.cpdss.cargoinfo.entity.Cargo;
import com.cpdss.cargoinfo.repository.CargoRepository;
import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoDetail;
import com.cpdss.common.generated.CargoInfo.CargoListRequest;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceImplBase;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import io.grpc.stub.StreamObserver;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

  /**
   * Retrieve all Master cargos with Pagination, Sorting and Filtering
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void getCargoInfoDetailed(
      com.cpdss.common.generated.CargoInfo.CargoRequest request,
      io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailedReply>
          responseObserver) {
    CargoInfo.CargoDetailedReply.Builder cargoReply = CargoInfo.CargoDetailedReply.newBuilder();
    try {

      // Filtering
      List<String> filterKeys =
          Arrays.asList("id", "crudeType", "abbreviation", "companyId", "api", "minLoadTemp");
      Map<String, String> params = new HashMap<>();
      request.getParamList().forEach(param -> params.put(param.getKey(), param.getValue()));
      Map<String, String> filterParams =
          params.entrySet().stream()
              .filter(e -> filterKeys.contains(e.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
      Specification<Cargo> specification =
          Specification.where(new CargoSpecification(new FilterCriteria("isActive", ":", true)));

      for (Map.Entry<String, String> entry : filterParams.entrySet()) {
        String filterKey = entry.getKey();
        String value = entry.getValue();
        specification =
            specification.and(new CargoSpecification(new FilterCriteria(filterKey, "like", value)));
      }

      // Paging and sorting
      Pageable paging =
          PageRequest.of(
              request.getPage(),
              request.getPageSize(),
              Sort.by(
                  Sort.Direction.valueOf(request.getOrderBy().toUpperCase()), request.getSortBy()));
      Page<Cargo> pagedResult = this.cargoRepository.findAll(specification, paging);

      List<Cargo> cargos = pagedResult.toList();
      cargos.forEach(
          cargo -> {
            CargoInfo.CargoDetailed.Builder cargoDetail = CargoInfo.CargoDetailed.newBuilder();
            buildCargoDetailed(cargo, cargoDetail);
            cargoReply.addCargos(cargoDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);
      cargoReply.setTotalElements(pagedResult.getTotalElements());
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

  /**
   * Building CargoDetail
   *
   * @param cargo
   * @param cargoDetail
   */
  private void buildCargoDetailed(Cargo cargo, CargoInfo.CargoDetailed.Builder cargoDetail) {
    cargoDetail.setAbbreviation(cargo.getAbbreviation() == null ? "" : cargo.getAbbreviation());
    cargoDetail.setApi(cargo.getApi() == null ? "" : cargo.getApi());
    cargoDetail.setId(cargo.getId());
    cargoDetail.setName(cargo.getCrudeType() == null ? "" : cargo.getCrudeType());
    cargoDetail.setBenzene(cargo.getBenzene() == null ? "" : cargo.getBenzene());
    cargoDetail.setCloudPoint(cargo.getMaxCloudPoint() == null ? "" : cargo.getMaxCloudPoint());
    cargoDetail.setType(cargo.getCrudeType() == null ? "" : cargo.getCrudeType());
    cargoDetail.setTemp(cargo.getMinLoadTemp() == null ? "" : cargo.getMinLoadTemp());
    cargoDetail.setGas(cargo.getGasC4() == null ? "" : cargo.getGasC4());
    cargoDetail.setTotalWax(cargo.getTotalWax() == null ? "" : cargo.getTotalWax());
    cargoDetail.setPourPoint(cargo.getMaxPourPoint() == null ? "" : cargo.getMaxPourPoint());
    cargoDetail.setCloudPoint(cargo.getMaxCloudPoint() == null ? "" : cargo.getMaxCloudPoint());
    cargoDetail.setViscosity(cargo.getViscocityT1() == null ? "" : cargo.getViscocityT1());
    cargoDetail.setCowCodes(
        cargo.getCowCodeRecommendedSummer() == null ? "" : cargo.getCowCodeRecommendedSummer());
    cargoDetail.setHydrogenSulfideOil(cargo.getH2sOilPhase() == null ? "" : cargo.getH2sOilPhase());
    cargoDetail.setHydrogenSulfideVapour(
        cargo.getH2sVapourPhaseConfirmed() == null ? "" : cargo.getH2sVapourPhaseConfirmed());
    cargoDetail.setSpecialInstrictionsRemark(cargo.getRemarks() == null ? "" : cargo.getRemarks());
  }

  @Override
  public void getCargoInfoDetailedById(
      com.cpdss.common.generated.CargoInfo.CargoRequest request,
      io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply>
          responseObserver) {
    CargoInfo.CargoByIdDetailedReply.Builder cargoReply =
        CargoInfo.CargoByIdDetailedReply.newBuilder();
    try {
      Optional<Cargo> cargo = cargoRepository.findById(request.getCargoId());
      CargoInfo.CargoDetailed.Builder cargoDetail = CargoInfo.CargoDetailed.newBuilder();
      buildCargoDetailed(cargo.get(), cargoDetail);
      cargoReply.setCargo(cargoDetail);
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

  /**
   * Deletion of cargo using cargoId
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void deleteCargoById(
      com.cpdss.common.generated.CargoInfo.CargoRequest request,
      io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply>
          responseObserver) {
    CargoInfo.CargoByIdDetailedReply.Builder cargoReply =
        CargoInfo.CargoByIdDetailedReply.newBuilder();
    try {
      Integer numberOfRowsUpdated = this.cargoRepository.deleteByCargoId(request.getCargoId());
      if (numberOfRowsUpdated == 0) {
        log.info("No rows updated!");
      }
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in deleting cargo by id method ", e);
      e.printStackTrace();
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      cargoReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(cargoReply.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Saving new Cargo / Editing existing cargo details
   *
   * @param request
   * @param responseObserver
   */
  @Override
  public void saveCargo(
      CargoInfo.CargoDetailed request,
      io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoByIdDetailedReply>
          responseObserver) {

    CargoInfo.CargoByIdDetailedReply.Builder cargoReply =
        CargoInfo.CargoByIdDetailedReply.newBuilder();

    try {

      List<Cargo> cargos =
          this.cargoRepository.findByCrudeTypeIgnoreCaseAndIsActiveTrue(request.getName());
      if (!cargos.isEmpty()) {
        throw new GenericServiceException(
            "Cargo with the same name already exists",
            CommonErrorCodes.E_CPDSS_CARGO_NAME_ALREADY_EXISTS,
            HttpStatusCode.BAD_REQUEST);
      }
      cargos =
          this.cargoRepository.findByAbbreviationIgnoreCaseAndIsActiveTrue(
              request.getAbbreviation());
      if (!cargos.isEmpty()) {
        throw new GenericServiceException(
            "Cargo with the same abbreviation already exists",
            CommonErrorCodes.E_CPDSS_CARGO_ABBREVIATION_ALREADY_EXISTS,
            HttpStatusCode.BAD_REQUEST);
      }

      Cargo cargo;
      if (request.getId() == 0) {
        cargo = new Cargo();
      } else {
        cargo = this.cargoRepository.getById(request.getId());
      }

      buildCargoEntity(request, cargo);
      Cargo savedCargo = this.cargoRepository.save(cargo);
      CargoInfo.CargoDetailed.Builder cargoDetail = CargoInfo.CargoDetailed.newBuilder();
      buildCargoDetailed(savedCargo, cargoDetail);
      cargoReply.setCargo(cargoDetail.build());
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("SUCCESS");
      cargoReply.setResponseStatus(responseStatus);

    } catch (GenericServiceException e) {
      e.printStackTrace();
      cargoReply.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus("FAILURE")
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Error in saveCargo method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus("FAILURE");
      cargoReply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(cargoReply.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Building cargo entity
   *
   * @param request
   * @param cargo
   */
  private void buildCargoEntity(CargoInfo.CargoDetailed request, Cargo cargo) {

    cargo.setCrudeType(request.getName());
    cargo.setApi(request.getApi());
    cargo.setFromRvp(request.getReidVapourPressure());
    cargo.setGasC4(request.getGas());
    cargo.setTotalWax(request.getTotalWax());
    cargo.setMaxPourPoint(request.getPourPoint());
    cargo.setMaxCloudPoint(request.getCloudPoint());
    cargo.setViscocityT1(request.getViscosity());
    cargo.setMinLoadTemp(request.getTemp());
    cargo.setCowCodeRecommendedSummer(request.getCowCodes());
    cargo.setH2sOilPhase(request.getHydrogenSulfideOil());
    cargo.setH2sVapourPhaseConfirmed(request.getHydrogenSulfideVapour());
    cargo.setBenzene(request.getBenzene());
    cargo.setRemarks(request.getSpecialInstrictionsRemark());
    cargo.setAbbreviation(request.getAbbreviation());
    cargo.setIsActive(true);
  }
}
