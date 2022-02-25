/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CountrysMapResponse;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.chartermaster.CharterDetailed;
import com.cpdss.gateway.domain.chartermaster.CharterDetailedResponse;
import com.cpdss.gateway.domain.chartermaster.CharterVesselMapping;
import com.cpdss.gateway.domain.chartermaster.ChartersDetailedResponse;
import com.google.protobuf.Empty;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CharterService {
  private static final String SUCCESS = "SUCCESS";

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoServiceBlockingStub;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  public ChartersDetailedResponse getCharterDetails(
      int pageNo,
      int pageSize,
      String sortBy,
      String orderBy,
      Map<String, String> params,
      String correlationIdHeader)
      throws GenericServiceException {

    VesselInfo.CharterInfoRequest.Builder newBuilder = VesselInfo.CharterInfoRequest.newBuilder();

    if (orderBy != null) newBuilder.setOrderBy(orderBy);
    if (sortBy != null) newBuilder.setSortBy(sortBy);

    newBuilder.setPageNo(pageNo);
    newBuilder.setPageSize(pageSize);

    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      newBuilder.addParam(CargoInfo.Param.newBuilder().setKey(key).setValue(value).build());
    }

    VesselInfo.CharterDetailedReply charterDetailsReply =
        this.vesselInfoServiceBlockingStub.getAllCharterDetails(newBuilder.build());

    if (!SUCCESS.equals(charterDetailsReply.getResponseStatus().getStatus())) {
      log.error("failed to get all charter information");
      throw new GenericServiceException(
          "failed to get all charter information ",
          charterDetailsReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(charterDetailsReply.getResponseStatus().getCode())));
    }

    ChartersDetailedResponse chartersDetailedResponse = new ChartersDetailedResponse();

    buildCharterDetailResponse(chartersDetailedResponse, charterDetailsReply, correlationIdHeader);

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    chartersDetailedResponse.setResponseStatus(commonSuccessResponse);
    chartersDetailedResponse.setTotalElements(charterDetailsReply.getTotalElements());

    return chartersDetailedResponse;
  }

  private void buildCharterDetailResponse(
      ChartersDetailedResponse chartersDetailedResponse,
      VesselInfo.CharterDetailedReply charterDetailedReply,
      String correlationIdHeader)
      throws GenericServiceException {
    List<CharterDetailed> charterDetails = new ArrayList<>();

    List<VesselInfo.CharterDetailed> charterDetailsList =
        charterDetailedReply.getCharterDetailsList();

    CountrysMapResponse countryList = new CountrysMapResponse();
    if (!charterDetailsList.isEmpty()) {
      // call the api to get the country listing from port DB
      countryList = getAllCountrys(correlationIdHeader);
    }

    Map<Long, String> countryMap = countryList.getCountryMap();

    charterDetailedReply
        .getCharterDetailsList()
        .forEach(
            charterDetailed -> {
              CharterDetailed eachCharter = new CharterDetailed();
              eachCharter.setId(charterDetailed.getId());
              eachCharter.setCharterName(charterDetailed.getCharterName());
              eachCharter.setCharterTypeId(charterDetailed.getCharterTypeId());
              eachCharter.setCharterTypeName(charterDetailed.getCharterTypeName());
              eachCharter.setCharterCompanyId(charterDetailed.getCharterCompanyId());
              eachCharter.setCharterCompanyName(charterDetailed.getCharterCompanyName());
              eachCharter.setCharterCountryId(charterDetailed.getCharterCountryId());

              eachCharter.setCharterCountryName(
                  countryMap.get(charterDetailed.getCharterCountryId()));

              List<VesselInfo.CharterVesselMappingDetail> charterVesselMappingDetails =
                  charterDetailed.getCharterVesselsList();

              eachCharter.setVesselInformation(new ArrayList<CharterVesselMapping>());

              charterVesselMappingDetails.forEach(
                  charterVesselMappingDetail -> {
                    Vessel vessel = new Vessel();
                    vessel.setName(charterVesselMappingDetail.getVesselName());
                    vessel.setId(charterVesselMappingDetail.getVesselId());

                    com.cpdss.gateway.domain.chartermaster.CharterVesselMapping
                        charterVesselMapping = new CharterVesselMapping();
                    charterVesselMapping.setId(charterVesselMappingDetail.getId());
                    charterVesselMapping.setVessel(vessel);

                    eachCharter.getVesselInformation().add(charterVesselMapping);
                  });
              charterDetails.add(eachCharter);
            });
    chartersDetailedResponse.setCharterDetails(charterDetails);
    chartersDetailedResponse.setTotalElements(charterDetailedReply.getTotalElements());
  }

  public CharterDetailedResponse saveCharterDetails(
      String correlationIdHeader, Long charterId, CharterDetailed charterDetailed)
      throws GenericServiceException {

    CharterDetailedResponse charterDetailedResponse = new CharterDetailedResponse();
    VesselInfo.CharterDetailed.Builder charterDetailsRequest =
        VesselInfo.CharterDetailed.newBuilder();

    charterDetailsRequest.setId(charterId);
    charterDetailsRequest.setCharterName(charterDetailed.getCharterName());
    charterDetailsRequest.setCharterTypeId(charterDetailed.getCharterTypeId());
    charterDetailsRequest.setCharterTypeName(charterDetailed.getCharterTypeName());
    charterDetailsRequest.setCharterCompanyId(charterDetailed.getCharterCompanyId());
    charterDetailsRequest.setCharterCompanyName(charterDetailed.getCharterCompanyName());

    CountrysMapResponse countryResponse =
        getACountry(correlationIdHeader, charterDetailed.getCharterCountryId());
    charterDetailsRequest.setCharterCountryId(charterDetailed.getCharterCountryId());
    charterDetailsRequest.setCharterCountryName(
        countryResponse.getCountryMap().get(charterDetailed.getCharterCountryId()));

    VesselInfo.CharterDetailReply charterDetailReply =
        this.vesselInfoServiceBlockingStub.saveCharterDetails(charterDetailsRequest.build());

    if (charterDetailReply != null
        && !SUCCESS.equalsIgnoreCase(charterDetailReply.getResponseStatus().getStatus())) {
      log.error("Failed to save Charter!");
      throw new GenericServiceException(
          "Failed to save Charter!",
          charterDetailReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(charterDetailReply.getResponseStatus().getHttpStatusCode()));
    }

    VesselInfo.CharterVesselMappingRequest.Builder charterVesselMappingRequest =
        VesselInfo.CharterVesselMappingRequest.newBuilder();

    buildCharterVesselMapping(charterDetailReply, charterDetailed, charterVesselMappingRequest);

    VesselInfo.CharterVesselReply charterVesselReply =
        this.vesselInfoServiceBlockingStub.saveCharterVesselMappings(
            charterVesselMappingRequest.build());

    if (charterVesselReply == null
        || !(SUCCESS.equalsIgnoreCase(charterVesselReply.getResponseStatus().getStatus()))) {
      log.error("Failed to save Charter Vessel Mapping!");
      throw new GenericServiceException(
          "Failed to save Charter Vessel Mapping!",
          charterVesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(charterVesselReply.getResponseStatus().getHttpStatusCode()));
    }
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    charterDetailedResponse.setResponseStatus(commonSuccessResponse);
    return charterDetailedResponse;
  }

  private void buildCharterVesselMapping(
      VesselInfo.CharterDetailReply charterDetailReply,
      CharterDetailed charterDetailed,
      VesselInfo.CharterVesselMappingRequest.Builder charterVesselMappingRequest) {

    Optional.ofNullable(charterDetailed.getVesselInformation())
        .ifPresent(
            charterVesselMappings -> {
              charterVesselMappings.forEach(
                  charterVesselMapping -> {
                    if (charterVesselMapping.getVessel() != null) {

                      VesselInfo.CharterVesselMappingDetail.Builder charterVesselMappingDetail =
                          VesselInfo.CharterVesselMappingDetail.newBuilder();

                      charterVesselMappingDetail.setVesselId(
                          charterVesselMapping.getVessel().getId());
                      charterVesselMappingDetail.setCharterId(
                          charterDetailReply.getCharterDetail().getId());
                      charterVesselMappingRequest.addCharterVesselMappings(
                          charterVesselMappingDetail);
                    }
                  });
            });
  }

  /**
   * Get all country infrmation
   *
   * @param correlationId
   * @return CountrysResponse
   * @throws GenericServiceException
   */
  public CountrysMapResponse getAllCountrys(String correlationId) throws GenericServiceException {

    com.google.protobuf.Empty.Builder builder = Empty.newBuilder();
    PortInfo.CountryReply countryReply =
        this.portInfoServiceBlockingStub.getAllCountries(builder.build());
    if (countryReply == null
        || countryReply.getResponseStatus() == null
        || !SUCCESS.equals(countryReply.getResponseStatus().getStatus())) {
      log.error("failed to get all country information");
      throw new GenericServiceException(
          "failed to get country information ",
          countryReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(countryReply.getResponseStatus().getCode())));
    }

    CountrysMapResponse response = new CountrysMapResponse();
    List<PortInfo.Country> countriesList = countryReply.getCountriesList();

    Map<Long, String> countryMap =
        countriesList.stream()
            .collect(Collectors.toMap(PortInfo.Country::getId, PortInfo.Country::getCountryName));

    response.setCountryMap(countryMap);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Retrieve a country if present, based on id
   *
   * @param correlationId
   * @return CountrysResponse
   * @throws GenericServiceException
   */
  public CountrysMapResponse getACountry(String correlationId, Long CountryId)
      throws GenericServiceException {

    PortInfo.Country.Builder countryRequestBuilder = PortInfo.Country.newBuilder();
    countryRequestBuilder.setId(CountryId);

    PortInfo.Country countryRequest = countryRequestBuilder.setId(CountryId).build();
    PortInfo.CountryDetailReply countryReply =
        portInfoServiceBlockingStub.getACountry(countryRequest);
    if (!SUCCESS.equals(countryReply.getResponseStatus().getStatus())) {
      log.error("failed to get country information with id :" + CountryId);
      throw new GenericServiceException(
          "failed to get country information ",
          countryReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(countryReply.getResponseStatus().getCode())));
    }

    CountrysMapResponse response = new CountrysMapResponse();

    Map<Long, String> countryMap = new HashMap<>();
    countryMap.put(countryReply.getCountry().getId(), countryReply.getCountry().getCountryName());
    response.setCountryMap(countryMap);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }
}
