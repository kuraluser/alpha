/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CountrysMapResponse;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.chartermaster.*;
import com.google.protobuf.Empty;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.collections.CollectionUtils;
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
    ChartersDetailedResponse chartersDetailedResponse = new ChartersDetailedResponse();

    if (orderBy != null) newBuilder.setOrderBy(orderBy);
    if (sortBy != null) newBuilder.setSortBy(sortBy);

    newBuilder.setPageNo(pageNo);
    newBuilder.setPageSize(pageSize);

    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      newBuilder.addParam(CargoInfo.Param.newBuilder().setKey(key).setValue(value).build());
    }

    String charterCountryName = params.get("charterCountryName");

    CountrysMapResponse countryList = getAllCountrys(correlationIdHeader);
    if (!StringUtils.isEmpty(charterCountryName)) {

      if (null != countryList) {
        Map<Long, String> countryMap = countryList.getCountryMap();
        if (null != countryMap) {
          List<Long> countryXidList = new ArrayList<>();
          for (Map.Entry<Long, String> entry : countryMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(charterCountryName)) {
              countryXidList.add(entry.getKey());
            }
          }
          // if No country is found while country filtering, sending empty response
          if (CollectionUtils.isEmpty(countryXidList)) {
            CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
            commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
            commonSuccessResponse.setCorrelationId(correlationIdHeader);
            chartersDetailedResponse.setResponseStatus(commonSuccessResponse);
            chartersDetailedResponse.setTotalElements(Long.valueOf("0"));
            chartersDetailedResponse.setCharterDetails(new ArrayList<CharterDetailed>());
            return chartersDetailedResponse;
          }
          newBuilder.addAllCountryXIds(countryXidList);
        }
      }
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

    buildCharterDetailResponse(
        chartersDetailedResponse, charterDetailsReply, countryList, correlationIdHeader);

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
      CountrysMapResponse countryList,
      String correlationIdHeader)
      throws GenericServiceException {
    List<CharterDetailed> charterDetails = new ArrayList<>();

    List<VesselInfo.CharterDetailed> charterDetailsList =
        charterDetailedReply.getCharterDetailsList();

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

              eachCharter.setVesselInformation(new ArrayList<Vessel>());

              charterVesselMappingDetails.forEach(
                  charterVesselMappingDetail -> {
                    Vessel vessel = new Vessel();
                    vessel.setName(charterVesselMappingDetail.getVesselName());
                    vessel.setId(charterVesselMappingDetail.getVesselId());

                    eachCharter.getVesselInformation().add(vessel);
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
            vesselList -> {
              vesselList.forEach(
                  vessel -> {
                    if (vessel != null) {

                      VesselInfo.CharterVesselMappingDetail.Builder charterVesselMappingDetail =
                          VesselInfo.CharterVesselMappingDetail.newBuilder();

                      charterVesselMappingDetail.setVesselId(vessel.getId());
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

  /**
   * ** to fetch the list of charter companies
   *
   * @param pageNo
   * @param pageSize
   * @param sortBy
   * @param orderBy
   * @param params
   * @param correlationIdHeader
   * @return
   * @throws GenericServiceException
   */
  public ChartersCompanysResponse getCharterCompanyDetails(
      int pageNo,
      int pageSize,
      String sortBy,
      String orderBy,
      Map<String, String> params,
      String correlationIdHeader)
      throws GenericServiceException {

    VesselInfo.CharterCompanyInfoRequest.Builder newBuilder =
        VesselInfo.CharterCompanyInfoRequest.newBuilder();
    ChartersCompanysResponse chartersCompanyDetailedResponse = new ChartersCompanysResponse();

    if (orderBy != null) newBuilder.setOrderBy(orderBy);
    if (sortBy != null) newBuilder.setSortBy(sortBy);

    newBuilder.setPageNo(pageNo);
    newBuilder.setPageSize(pageSize);

    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      newBuilder.addParam(CargoInfo.Param.newBuilder().setKey(key).setValue(value).build());
    }

    VesselInfo.CharterCompanyDetailedReply allCharterCompanyDetails =
        this.vesselInfoServiceBlockingStub.getAllCharterCompanyDetails(newBuilder.build());

    if (!SUCCESS.equals(allCharterCompanyDetails.getResponseStatus().getStatus())) {
      log.error("failed to get all charter company information");
      throw new GenericServiceException(
          "failed to get all charter company information ",
          allCharterCompanyDetails.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(allCharterCompanyDetails.getResponseStatus().getCode())));
    }

    buildCharterCompanyDetailResponse(chartersCompanyDetailedResponse, allCharterCompanyDetails);

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    chartersCompanyDetailedResponse.setResponseStatus(commonSuccessResponse);
    chartersCompanyDetailedResponse.setTotalElements(allCharterCompanyDetails.getTotalElements());

    return chartersCompanyDetailedResponse;
  }

  /**
   * * to format the charter company response
   *
   * @param chartersCompanysResponse
   * @param charterCompanyDetailedReply
   */
  private void buildCharterCompanyDetailResponse(
      ChartersCompanysResponse chartersCompanysResponse,
      VesselInfo.CharterCompanyDetailedReply charterCompanyDetailedReply) {

    List<CharterCompanyDetailed> charterCompanyDetails = new ArrayList<>();

    charterCompanyDetailedReply
        .getCharterCompanyDetailedList()
        .forEach(
            charterCompanyDetailed -> {
              CharterCompanyDetailed companyDetailed = new CharterCompanyDetailed();
              companyDetailed.setCharterCompanyId(charterCompanyDetailed.getCharterCompanyId());
              companyDetailed.setCharterCompanyName(charterCompanyDetailed.getCharterCompanyName());
              charterCompanyDetails.add(companyDetailed);
            });
    chartersCompanysResponse.setCharterCompanyDetails(charterCompanyDetails);
    chartersCompanysResponse.setTotalElements(charterCompanyDetailedReply.getTotalElements());
  }

  /**
   * ** to fetch the list of charter companies
   *
   * @param pageNo
   * @param pageSize
   * @param sortBy
   * @param orderBy
   * @param params
   * @param correlationIdHeader
   * @return
   * @throws GenericServiceException
   */
  public ChartersTypesResponse getCharterTypeDetails(
      int pageNo,
      int pageSize,
      String sortBy,
      String orderBy,
      Map<String, String> params,
      String correlationIdHeader)
      throws GenericServiceException {

    VesselInfo.CharterTypeInfoRequest.Builder newBuilder =
        VesselInfo.CharterTypeInfoRequest.newBuilder();

    ChartersTypesResponse chartersTypesResponse = new ChartersTypesResponse();

    if (orderBy != null) newBuilder.setOrderBy(orderBy);
    if (sortBy != null) newBuilder.setSortBy(sortBy);

    newBuilder.setPageNo(pageNo);
    newBuilder.setPageSize(pageSize);

    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      newBuilder.addParam(CargoInfo.Param.newBuilder().setKey(key).setValue(value).build());
    }

    VesselInfo.CharterTypeDetailedReply allCharterTypeDetails =
        this.vesselInfoServiceBlockingStub.getAllCharterTypeDetails(newBuilder.build());

    if (!SUCCESS.equals(allCharterTypeDetails.getResponseStatus().getStatus())) {
      log.error("failed to get all charter type information");
      throw new GenericServiceException(
          "failed to get all charter type information ",
          allCharterTypeDetails.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(allCharterTypeDetails.getResponseStatus().getCode())));
    }

    buildCharterTypeDetailResponse(chartersTypesResponse, allCharterTypeDetails);

    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    chartersTypesResponse.setResponseStatus(commonSuccessResponse);
    chartersTypesResponse.setTotalElements(allCharterTypeDetails.getTotalElements());

    return chartersTypesResponse;
  }

  /**
   * * to format the charter type response
   *
   * @param chartersTypesResponse
   * @param charterTypeDetailedReply
   */
  private void buildCharterTypeDetailResponse(
      ChartersTypesResponse chartersTypesResponse,
      VesselInfo.CharterTypeDetailedReply charterTypeDetailedReply) {

    List<CharterTypeDetailed> charterTypeDetails = new ArrayList<>();

    charterTypeDetailedReply
        .getCharterTypeDetailsList()
        .forEach(
            charterTypeDetailed -> {
              CharterTypeDetailed CharterTypeDetailed = new CharterTypeDetailed();
              CharterTypeDetailed.setCharterTypeId(charterTypeDetailed.getCharterTypeId());
              CharterTypeDetailed.setCharterTypeName(charterTypeDetailed.getCharterTypeName());
              charterTypeDetails.add(CharterTypeDetailed);
            });
    chartersTypesResponse.setCharterTypeDetails(charterTypeDetails);
    chartersTypesResponse.setTotalElements(charterTypeDetailedReply.getTotalElements());
  }
}
