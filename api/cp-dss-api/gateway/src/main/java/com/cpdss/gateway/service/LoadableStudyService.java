/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceBlockingStub;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequest;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.domain.Cargo;
import com.cpdss.gateway.domain.CargoNomination;
import com.cpdss.gateway.domain.CargoNominationResponse;
import com.cpdss.gateway.domain.LoadableQuantity;
import com.cpdss.gateway.domain.LoadableQuantityResponse;
import com.cpdss.gateway.domain.LoadableStudy;
import com.cpdss.gateway.domain.LoadableStudyResponse;
import com.cpdss.gateway.domain.Port;
import com.cpdss.gateway.domain.Voyage;
import com.cpdss.gateway.domain.VoyageResponse;
import com.google.protobuf.ByteString;

import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;

/** GatewayLoadableStudyService - service class for loadable study related operations */
@Service
@Log4j2
public class LoadableStudyService {

	@GrpcClient("loadableStudyService")
	private LoadableStudyServiceBlockingStub loadableStudyServiceBlockingStub;

	@GrpcClient("cargoInfoService")
	private CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

	@GrpcClient("portInfoService")
	private PortInfoServiceBlockingStub portInfoServiceBlockingStub;

	private static final String SUCCESS = "SUCCESS";

	private static final int LOADABLE_STUDY_ATTACHEMENT_MAX_SIZE = 1 * 1024 * 1024;
	private static final List<String> ATTACHMENT_ALLOWED_EXTENSIONS =
			Arrays.asList("docx", "pdf", "txt", "jpg", "png", "msg", "eml");

  /**
   * method for voyage save
   *
   * @param voyage
   * @param companyId
   * @param vesselId
   * @param headers
   * @return response to controller
   * @throws GenericServiceException CommonSuccessResponse
   */
  public VoyageResponse saveVoyage(
      Voyage voyage, long companyId, long vesselId)
      throws GenericServiceException {
    VoyageResponse voyageResponse = new VoyageResponse();
    VoyageRequest voyageRequest =
        VoyageRequest.newBuilder()
            .setCaptainId(voyage.getCaptainId())
            .setChiefOfficerId(voyage.getChiefOfficerId())
            .setCompanyId(companyId)
            .setVesselId(vesselId)
            .setVoyageNo(voyage.getVoyageNo())
            .build();

    VoyageReply voyageReply = this.saveVoyage(voyageRequest);
    if (SUCCESS.equalsIgnoreCase(voyageReply.getStatus())) {
      voyageResponse.setResponseStatus(
          new CommonSuccessResponse(voyageReply.getMessage(), "correlationId"));
      voyageResponse.setVoyageId(voyageReply.getVoyageId());
      return voyageResponse;
    } else {
      throw new GenericServiceException(
          "Error in calling voyage service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
   * 
   * @param voyageRequest
   * @return
   * VoyageReply
   */
  public VoyageReply saveVoyage(VoyageRequest voyageRequest) {
	  return loadableStudyServiceBlockingStub.saveVoyage(voyageRequest);
  }
  
  /**
   * 
   * @param loadableQuantity
   * @param companyId
   * @param vesselId
   * @param loadableStudiesId
   * @param headers
   * @return
   * @throws GenericServiceException
   * CommonSuccessResponse
   */
    public LoadableQuantityResponse saveLoadableQuantity(
        LoadableQuantity loadableQuantity, long loadableStudiesId)
        throws GenericServiceException {
  	  LoadableQuantityResponse loadableQuantityResponse = new LoadableQuantityResponse();
      LoadableQuantityRequest loadableQuantityRequest =
          LoadableQuantityRequest.newBuilder()
              .setConstant(loadableQuantity.getConstant())
              .setDisplacmentDraftRestriction(loadableQuantity.getDisplacmentDraftRestriction())
              .setDistanceFromLastPort(loadableQuantity.getDistanceFromLastPort())
              .setDwt(loadableQuantity.getDwt())
              .setEstDOOnBoard(loadableQuantity.getEstDOOnBoard())
              .setEstFOOnBoard(loadableQuantity.getEstFOOnBoard())
              .setEstFreshWaterOnBoard(loadableQuantity.getEstFreshWaterOnBoard())
              .setEstSagging(loadableQuantity.getEstSagging())
              .setEstSeaDensity(loadableQuantity.getEstSeaDensity())
              .setEstTotalFOConsumption(loadableQuantity.getEstTotalFOConsumption())
              .setFoConsumptionPerDay(loadableQuantity.getFoConsumptionPerDay())
              .setLimitingDraft(loadableQuantity.getLimitingDraft())
              .setOtherIfAny(loadableQuantity.getOtherIfAny())
              .setSaggingDeduction(loadableQuantity.getSaggingDeduction())
              .setSgCorrection(loadableQuantity.getSgCorrection())
              .setTotalQuantity(loadableQuantity.getTotalQuantity())
              .setTpc(loadableQuantity.getTpc())
              .setVesselAverageSpeed(loadableQuantity.getVesselAverageSpeed())
              .setVesselLightWeight(loadableQuantity.getVesselLightWeight())
              .setLoadableStudyId(loadableStudiesId)
              .build();

      LoadableQuantityReply loadableQuantityReply =
          this.saveLoadableQuantity(loadableQuantityRequest);
      if (SUCCESS.equalsIgnoreCase(loadableQuantityReply.getStatus())) {
      	loadableQuantityResponse.setResponseStatus(new CommonSuccessResponse(loadableQuantityReply.getMessage(), "correlationId"));
      	loadableQuantityResponse.setLoadableQuantityId(loadableQuantityReply.getLoadableQuantityId());
        return loadableQuantityResponse;
      } else {
        throw new GenericServiceException(
            "Error in calling loadable quantity service",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    
  public LoadableQuantityReply saveLoadableQuantity(LoadableQuantityRequest loadableQuantityRequest) {
	  return loadableStudyServiceBlockingStub.saveLoadableQuantity(loadableQuantityRequest);
  }
  
	/**
	 * This method calls loadable study microservice to get a list of loadable studies by vessel and
	 * voyage
	 *
	 * @param vesselId - the vessel id
	 * @param voyageId - the voyage id
	 * @param voyageId2
	 * @param correlationdId - the correlation id
	 * @return LoadableStudyResponse
	 * @throws GenericServiceException
	 */
	public LoadableStudyResponse getLoadableStudies(
			Long companyId, Long vesselId, Long voyageId, String correlationdId)
					throws GenericServiceException {
		log.info("fetching loadable studies. correlationId: {}", correlationdId);
		LoadableStudyRequest request =
				LoadableStudyRequest.newBuilder().setVesselId(vesselId).setVoyageId(voyageId).build();
		LoadableStudyReply reply = this.getloadableStudyList(request);
		if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
			throw new GenericServiceException(
					"failed to fetch loadable studies",
					reply.getResponseStatus().getCode(),
					HttpStatus.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
		}
		List<LoadableStudy> list = new ArrayList<>();
		for (LoadableStudyDetail grpcReply : reply.getLoadableStudiesList()) {
			LoadableStudy dto = new LoadableStudy();
			dto.setId(grpcReply.getId());
			dto.setName(grpcReply.getName());
			dto.setDetail(grpcReply.getDetail());
			dto.setCreatedDate(grpcReply.getCreatedDate());
			dto.setCharterer(grpcReply.getCharterer());
			dto.setSubCharterer(grpcReply.getSubCharterer());
			dto.setDraftMark(
					StringUtils.isEmpty(grpcReply.getDraftMark())
					? null
							: new BigDecimal(grpcReply.getDraftMark()));
			dto.setLoadLineXId(grpcReply.getLoadLineXId());
			dto.setDraftRestriction(
					StringUtils.isEmpty(grpcReply.getDraftRestriction())
					? null
							: new BigDecimal(grpcReply.getDraftRestriction()));
			dto.setMaxTempExpected(
					StringUtils.isEmpty(grpcReply.getMaxTempExpected())
					? null
							: new BigDecimal(grpcReply.getMaxTempExpected()));
			list.add(dto);
		}
		LoadableStudyResponse response = new LoadableStudyResponse();
		response.setLoadableStudies(list);
		response.setResponseStatus(
				new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationdId));
		return response;
	}

	/**
	 * Call loadable study microservice through grpc
	 *
	 * @param request {@link LoadableStudyRequest}
	 * @return {@link LoadableStudyReply}
	 */
	public LoadableStudyReply getloadableStudyList(LoadableStudyRequest request) {
		return this.loadableStudyServiceBlockingStub.findLoadableStudiesByVesselAndVoyage(request);
	}


	public LoadableStudyResponse saveLoadableStudy(
			final LoadableStudy request, String correlationId, MultipartFile[] files)
					throws GenericServiceException, IOException {
		this.validateLoadableStudyFiles(files);
		Builder builder = LoadableStudyDetail.newBuilder();
		Optional.ofNullable(request.getName()).ifPresent(builder::setName);
		Optional.ofNullable(request.getDetail()).ifPresent(builder::setDetail);
		Optional.ofNullable(request.getCharterer()).ifPresent(builder::setCharterer);
		Optional.ofNullable(request.getSubCharterer()).ifPresent(builder::setSubCharterer);
		Optional.ofNullable(request.getDraftMark())
		.ifPresent(draftMark -> builder.setDraftMark(String.valueOf(draftMark)));
		Optional.ofNullable(request.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
		Optional.ofNullable(request.getDraftRestriction())
		.ifPresent(
				draftRestriction -> builder.setDraftRestriction(String.valueOf(draftRestriction)));
		Optional.ofNullable(request.getMaxTempExpected())
		.ifPresent(maxTemp -> builder.setMaxTempExpected(String.valueOf(maxTemp)));
		Optional.ofNullable(request.getVesselId()).ifPresent(builder::setVesselId);
		Optional.ofNullable(request.getVoyageId()).ifPresent(builder::setVoyageId);
		Optional.ofNullable(request.getCreatedFromId()).ifPresent(builder::setDuplicatedFromId);
		for (MultipartFile file : files) {
			String orginalFileName = file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
			String fileName = orginalFileName.substring(0, orginalFileName.lastIndexOf("."));
			String extension = orginalFileName.substring(orginalFileName.lastIndexOf(".")).toLowerCase();
			builder.addAttachments(
					LoadableStudyAttachment.newBuilder()
					.setFileName(fileName + System.currentTimeMillis() + extension)
					.setByteString(ByteString.copyFrom(file.getBytes()))
					.build());
		}
		LoadableStudyReply reply = this.saveLoadableStudy(builder.build());
		if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
			throw new GenericServiceException(
					"failed to save loadable studies",
					reply.getResponseStatus().getCode(),
					HttpStatus.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
		}
		LoadableStudyResponse response = new LoadableStudyResponse();
		response.setLoadableStudyId(reply.getId());
		response.setResponseStatus(
				new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
		return response;
	}

	private void validateLoadableStudyFiles(MultipartFile[] files)
			throws GenericServiceException, IOException {
		log.info("validating attachment files");
		for (MultipartFile file : files) {
			if (file.getSize() > LOADABLE_STUDY_ATTACHEMENT_MAX_SIZE) {
				throw new GenericServiceException(
						"loadable study attachment size exceeds maximum allowed size",
						CommonErrorCodes.E_HTTP_BAD_REQUEST,
						HttpStatus.BAD_REQUEST);
			}
			String originalFileName =
					file.getOriginalFilename() == null ? "" : file.getOriginalFilename();
			if (!ATTACHMENT_ALLOWED_EXTENSIONS.contains(
					originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase())) {
				throw new GenericServiceException(
						"unsupported file type", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatus.BAD_REQUEST);
			}
		}
	}

	public LoadableStudyReply saveLoadableStudy(LoadableStudyDetail grpcRequest) {
		return this.loadableStudyServiceBlockingStub.saveLoadableStudy(grpcRequest);
	}

  /**
	 * Retrieves the cargo information from cargo master,
	 * port information from port master and 
	 * cargo nomination details from loadable-study service
	 * @param loadableStudyId
	 * @param headers
	 * @return
	 * @throws GenericServiceException
	 */
	public CargoNominationResponse getCargoNomination(Long loadableStudyId, HttpHeaders headers) throws GenericServiceException{
		CargoNominationResponse cargoNominationResponse = new CargoNominationResponse();
		// Build response with response status
		buildCargoNominationResponseWithResponseStatus(cargoNominationResponse);
		// Retrieve cargo information from cargo master
		CargoRequest cargoRequest = CargoRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
		CargoReply cargoReply = cargoInfoServiceBlockingStub.getCargoInfo(cargoRequest);
		if (cargoReply!=null && cargoReply.getResponseStatus()!=null
				&& SUCCESS.equalsIgnoreCase(cargoReply.getResponseStatus().getStatus())) {
			buildCargoNominationResponseWithCargo(cargoNominationResponse, cargoReply);
		} else {
			throw new GenericServiceException(
					"Error in calling cargo service",
					CommonErrorCodes.E_GEN_INTERNAL_ERR,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Retrieve port information from port master
		PortRequest portRequest = PortRequest.newBuilder().setLoadableStudyId(loadableStudyId).build();
		PortReply portReply = portInfoServiceBlockingStub.getPortInfo(portRequest);
		if (portReply!=null && portReply.getResponseStatus()!=null
				&& SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
			buildCargoNominationResponseWithPort(cargoNominationResponse, portReply);
		} else {
			throw new GenericServiceException(
					"Error in calling port service",
					CommonErrorCodes.E_GEN_INTERNAL_ERR,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return cargoNominationResponse;
	}

	private CargoNominationResponse buildCargoNominationResponseWithResponseStatus(CargoNominationResponse cargoNominationResponse) {
		// set response status irrespective of whether cargo details are available
		CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
		commonSuccessResponse.setStatus(SUCCESS);
		cargoNominationResponse.setResponseStatus(commonSuccessResponse);
		return cargoNominationResponse;
	}

	/**
	 * @param cargoNominationResponse
	 * @param cargoReply
	 * @return
	 */
	private CargoNominationResponse buildCargoNominationResponseWithCargo(CargoNominationResponse cargoNominationResponse, CargoReply cargoReply) {
		if (cargoReply != null && !cargoReply.getCargosList().isEmpty()) {
			List<Cargo> cargoList = new ArrayList<>();
			cargoReply.getCargosList().forEach(cargoDetail -> {
				Cargo cargo = new Cargo();
				cargo.setId(cargoDetail.getId());
				cargo.setApi(cargoDetail.getApi());
				cargo.setAbbreviation(cargoDetail.getAbbreviation());
				cargo.setName(cargoDetail.getCrudeType());
				cargoList.add(cargo);
			});
			cargoNominationResponse.setCargos(cargoList);
		}
		return cargoNominationResponse;
	}

	/**
	 * @param cargoNominationResponse
	 * @param cargoReply
	 * @return
	 */
	private CargoNominationResponse buildCargoNominationResponseWithPort(CargoNominationResponse cargoNominationResponse, PortReply portReply) {
		if (portReply != null && !portReply.getPortsList().isEmpty()) {
			List<Port> portList = new ArrayList<>();
			portReply.getPortsList().forEach(portDetail -> {
				Port port = new Port();
				port.setId(portDetail.getId());
				port.setName(portDetail.getName());
				portList.add(port);
			});
			cargoNominationResponse.setPorts(portList);
		}
		return cargoNominationResponse;
	}

	/**
	 * Save cargo nomination details using loadable-study service
	 * @param loadableStudyId
	 * @param headers
	 * @return
	 * @throws GenericServiceException
	 */
	public CargoNominationResponse saveCargoNomination(Long vesselId, Long voyageId, Long loadableStudyId, CargoNomination request, HttpHeaders headers) throws GenericServiceException{
		CargoNominationResponse cargoNominationResponse = new CargoNominationResponse();
		// Build response with response status
		buildCargoNominationResponseWithResponseStatus(cargoNominationResponse);
		// Build cargoNomination payload for grpc call
		com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
		Optional.ofNullable(vesselId).ifPresent(builder::setVesselId);
		Optional.ofNullable(voyageId).ifPresent(builder::setVoyageId);
		Optional.ofNullable(loadableStudyId).ifPresent(builder::setLoadableStudyId);
		// build inner cargoNomination detail object
		CargoNominationDetail.Builder cargoNominationDetailbuilder = CargoNominationDetail.newBuilder();
		Optional.ofNullable(request.getId()).ifPresent(cargoNominationDetailbuilder::setId);
		Optional.ofNullable(request.getLoadableStudyId()).ifPresent(cargoNominationDetailbuilder::setLoadableStudyId);
		Optional.ofNullable(request.getPriority()).ifPresent(cargoNominationDetailbuilder::setPriority);
		Optional.ofNullable(request.getColor()).ifPresent(cargoNominationDetailbuilder::setColor);
		Optional.ofNullable(request.getCargoId()).ifPresent(cargoNominationDetailbuilder::setCargoId);
		Optional.ofNullable(request.getAbbreviation()).ifPresent(cargoNominationDetailbuilder::setAbbreviation);
		// build inner loadingPort details object
		if (!CollectionUtils.isEmpty(request.getLoadingPorts())) {
			request.getLoadingPorts().forEach(loadingPort -> {
				LoadingPortDetail.Builder loadingPortDetailBuilder = LoadingPortDetail.newBuilder();
				Optional.ofNullable(loadingPort.getPortId()).ifPresent(loadingPortDetailBuilder::setPortId);
				Optional.ofNullable(loadingPort.getQuantity()).ifPresent(quantity -> loadingPortDetailBuilder.setQuantity(String.valueOf(quantity)));
				cargoNominationDetailbuilder.addLoadingPortDetails(loadingPortDetailBuilder);
			});
		}
		Optional.ofNullable(request.getMaxTolerance()).ifPresent(maxTolerance -> cargoNominationDetailbuilder.setMaxTolerance(String.valueOf(maxTolerance)));
		Optional.ofNullable(request.getMinTolerance()).ifPresent(minTolerance -> cargoNominationDetailbuilder.setMinTolerance(String.valueOf(minTolerance)));
		Optional.ofNullable(request.getApi()).ifPresent(api -> cargoNominationDetailbuilder.setApiEst(String.valueOf(api)));
		Optional.ofNullable(request.getTemperature()).ifPresent(temperature -> cargoNominationDetailbuilder.setTempEst(String.valueOf(temperature)));
		Optional.ofNullable(request.getSegregationId()).ifPresent(cargoNominationDetailbuilder::setSegregationId);
		builder.setCargoNominationDetail(cargoNominationDetailbuilder);
		CargoNominationRequest cargoNominationRequest = builder.build();
		CargoNominationReply cargoNominationReply = loadableStudyServiceBlockingStub.saveCargoNomination(cargoNominationRequest);
		if (cargoNominationReply!=null && cargoNominationReply.getResponseStatus()!=null
				&& SUCCESS.equalsIgnoreCase(cargoNominationReply.getResponseStatus().getStatus())) {
			cargoNominationResponse.setCargoNominationId(cargoNominationReply.getCargoNominationId());
		} else {
			throw new GenericServiceException(
					"Error in calling cargo service",
					CommonErrorCodes.E_GEN_INTERNAL_ERR,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return cargoNominationResponse;
	}
}
