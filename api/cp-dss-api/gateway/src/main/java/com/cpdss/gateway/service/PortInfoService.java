/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfo.*;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.Builder;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.google.protobuf.Empty;
import io.micrometer.core.instrument.util.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PortInfoService {

  private static final String SUCCESS = "SUCCESS";

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  public PortInfo.BerthInfoResponse getBerthInfoByPortId(Long id) {
    PortInfo.BerthInfoResponse response =
        this.portInfoServiceBlockingStub.getBerthDetailsByPortId(
            PortInfo.PortIdRequest.newBuilder().setPortId(id).build());
    if (response.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
      return response;
    }
    return null;
  }

  /**
   * Get port master details with berth information for the requested port id.
   *
   * @param portId
   * @param correlationId
   * @return PortDetailResponse
   * @throws NumberFormatException
   * @throws GenericServiceException
   */
  public PortDetailResponse getPortInformationByPortId(Long portId, String correlationId)
      throws GenericServiceException {

    Builder builder = GetPortInfoByPortIdsRequest.newBuilder();
    builder.addId(portId);
    PortReply portReply = portInfoServiceBlockingStub.getPortInfoByPortIds(builder.build());
    if (!SUCCESS.equals(portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get vessel detailed information ",
          portReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(portReply.getResponseStatus().getCode())));
    }
    PortDetailResponse response = new PortDetailResponse();
    PortDetails portDetails = new PortDetails();
    List<PortDetail> portsList = portReply.getPortsList();
    portDetails.setCountry(portsList.get(0).getCountryName());
    portDetails.setCountryId(portsList.get(0).getCountryId());
    portDetails.setDensityOfWater(
        portsList.get(0).getWaterDensity().isEmpty()
            ? null
            : new BigDecimal(portsList.get(0).getWaterDensity()));
    portDetails.setMaxPermissibleDraft(
        portsList.get(0).getMaxPermissibleDraft().isEmpty()
            ? null
            : new BigDecimal(portsList.get(0).getMaxPermissibleDraft()));
    portDetails.setPortCode(portsList.get(0).getCode());
    portDetails.setPortId(portsList.get(0).getId());
    portDetails.setPortName(portsList.get(0).getName());
    portDetails.setTimezoneId(portsList.get(0).getTimezoneId());
    portDetails.setTimezone(portsList.get(0).getTimezone());
    portDetails.setTimezoneAbbreviation(portsList.get(0).getTimezoneAbbreviation());
    portDetails.setTimezoneOffsetVal(portsList.get(0).getTimezoneOffsetVal());
    portDetails.setTideHeightHigh(
        portsList.get(0).getTideHeightTo().isEmpty()
            ? null
            : new BigDecimal(portsList.get(0).getTideHeightTo()));
    portDetails.setTideHeightLow(
        portsList.get(0).getTideHeightFrom().isEmpty()
            ? null
            : new BigDecimal(portsList.get(0).getTideHeightFrom()));
    portDetails.setLatitude(portsList.get(0).getLat().isEmpty() ? null : portsList.get(0).getLat());
    portDetails.setLongitude(
        portsList.get(0).getLon().isEmpty() ? null : portsList.get(0).getLon());

    List<BerthDetail> berthDetailsList = portsList.get(0).getBerthDetailsList();
    List<PortBerthInfoResponse> berthList = setBerthInformationForThePorts(berthDetailsList);
    portDetails.setBerthInfo(berthList);
    response.setPortDetails(portDetails);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Set berth information in the response for the ports.
   *
   * @param berthDetailsList
   * @return List of PortBerthInfoResponse.
   */
  private List<PortBerthInfoResponse> setBerthInformationForThePorts(
      List<BerthDetail> berthDetailsList) {

    List<PortBerthInfoResponse> berthList = new ArrayList<>();
    for (BerthDetail berth : berthDetailsList) {
      PortBerthInfoResponse berthResponse = new PortBerthInfoResponse();
      berthResponse.setBerthId(berth.getId());
      berthResponse.setBerthName(berth.getBerthName());
      berthResponse.setDepthInDatum(
          berth.getBerthDatumDepth().isEmpty() ? null : new BigDecimal(berth.getBerthDatumDepth()));
      berthResponse.setMaxDwt(
          berth.getMaxDwt().isEmpty() ? null : new BigDecimal(berth.getMaxDwt()));
      berthResponse.setMaxManifoldHeight(
          berth.getMaxManifoldHeight().isEmpty()
              ? null
              : new BigDecimal(berth.getMaxManifoldHeight()));
      berthResponse.setMaxDraft(
          StringUtils.isEmpty(berth.getMaxDraft()) ? null : new BigDecimal(berth.getMaxDraft()));
      berthResponse.setMaxShipDepth(
          berth.getMaxShipDepth().isEmpty() ? null : new BigDecimal(berth.getMaxShipDepth()));
      berthResponse.setPortId(berth.getPortId());
      berthResponse.setRegulationAndRestriction(berth.getRegulationAndRestriction());
      berthResponse.setMaxLoa(
          berth.getMaxLoa().isEmpty() ? null : new BigDecimal(berth.getMaxLoa()));
      berthResponse.setMinUKC(berth.getUkc().isEmpty() ? null : berth.getUkc());
      berthList.add(berthResponse);
    }
    return berthList;
  }

  /**
   * Get all country infrmation
   *
   * @param correlationId
   * @return CountrysResponse
   * @throws GenericServiceException
   */
  public CountrysResponse getAllCountrys(String correlationId) throws GenericServiceException {

    com.google.protobuf.Empty.Builder builder = Empty.newBuilder();
    CountryReply countryReply = portInfoServiceBlockingStub.getAllCountries(builder.build());
    if (!SUCCESS.equals(countryReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get country information ",
          countryReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(countryReply.getResponseStatus().getCode())));
    }
    CountrysResponse response = new CountrysResponse();
    List<CountryInfo> countryList = new ArrayList<>();
    List<Country> countriesList = countryReply.getCountriesList();
    countriesList.forEach(
        country -> {
          CountryInfo countryInfo = new CountryInfo();
          countryInfo.setId(country.getId());
          countryInfo.setName(country.getCountryName());
          countryList.add(countryInfo);
        });
    response.setCountrys(countryList);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Save/Update Port Info
   *
   * @param portId
   * @param correlationId
   * @param portDetailed
   * @return
   * @throws GenericServiceException
   */
  public PortDetailResponse savePortInfo(
      Long portId, String correlationId, PortDetails portDetailed) throws GenericServiceException {
    PortDetailResponse response = new PortDetailResponse();
    PortDetail.Builder portDetailRequest = PortDetail.newBuilder();
    buildPortDetailed(portDetailed, portId, portDetailRequest);
    PortInfoReply portInfoReply =
        portInfoServiceBlockingStub.savePortInfo(portDetailRequest.build());

    if (portInfoReply == null
        || !SUCCESS.equalsIgnoreCase(portInfoReply.getResponseStatus().getStatus()))
      throw new GenericServiceException(
          "Error in calling cargo service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);

    buildPortInfoResponse(response, portInfoReply);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Building PortDetails Builder
   *
   * @param portDetailed
   * @param portId
   * @param portDetailRequest
   */
  private void buildPortDetailed(
      PortDetails portDetailed, long portId, PortDetail.Builder portDetailRequest) {
    portDetailRequest.setId(portId);
    portDetailRequest.setCountryName(portDetailed.getCountry());
    portDetailRequest.setCountryId(portDetailed.getCountryId());
    portDetailRequest.setWaterDensity(
        portDetailed.getDensityOfWater() == null
            ? ""
            : portDetailed.getDensityOfWater().toString());
    portDetailRequest.setMaxPermissibleDraft(
        portDetailed.getMaxPermissibleDraft() == null
            ? ""
            : portDetailed.getMaxPermissibleDraft().toString());
    portDetailRequest.setCode(portDetailed.getPortCode());
    portDetailRequest.setName(portDetailed.getPortName());
    portDetailRequest.setTimezoneId(portDetailed.getTimezoneId());
    portDetailRequest.setTimezone(portDetailed.getTimezone());
    portDetailRequest.setTimezoneAbbreviation(portDetailed.getTimezoneAbbreviation());
    portDetailRequest.setTimezoneOffsetVal(portDetailed.getTimezoneOffsetVal());
    portDetailRequest.setTideHeightTo(
        portDetailed.getTideHeightHigh() == null
            ? ""
            : portDetailed.getTideHeightHigh().toString());
    portDetailRequest.setTideHeightFrom(
        portDetailed.getTideHeightLow() == null ? "" : portDetailed.getTideHeightLow().toString());
    portDetailRequest.setLat(
        portDetailed.getLatitude().isEmpty() ? null : portDetailed.getLatitude());
    portDetailRequest.setLon(
        portDetailed.getLongitude().isEmpty() ? null : portDetailed.getLongitude());
    portDetailRequest.setAmbientTemperature(
        portDetailed.getAmbientTemperature() == null
            ? ""
            : portDetailed.getAmbientTemperature().toString());
    buildBirthInfoForPort(portDetailed.getBerthInfo(), portDetailRequest);
  }

  /**
   * Building BerthDetail builder
   *
   * @param portBerthInfoResponseList
   * @param portDetailRequest
   */
  public void buildBirthInfoForPort(
      List<PortBerthInfoResponse> portBerthInfoResponseList, PortDetail.Builder portDetailRequest) {
    if (portBerthInfoResponseList == null) return;
    portBerthInfoResponseList.forEach(
        portBerthInfoResponse -> {
          BerthDetail.Builder berthDetail = BerthDetail.newBuilder();
          berthDetail.setId(portBerthInfoResponse.getBerthId());
          berthDetail.setBerthName(portBerthInfoResponse.getBerthName());
          berthDetail.setBerthDatumDepth(
              portBerthInfoResponse.getDepthInDatum() == null
                  ? ""
                  : portBerthInfoResponse.getDepthInDatum().toString());
          berthDetail.setMaxDwt(
              portBerthInfoResponse.getMaxDwt() == null
                  ? ""
                  : portBerthInfoResponse.getMaxDwt().toString());
          berthDetail.setMaxManifoldHeight(
              portBerthInfoResponse.getMaxManifoldHeight() == null
                  ? ""
                  : portBerthInfoResponse.getMaxManifoldHeight().toString());
          berthDetail.setMaxShipDepth(
              portBerthInfoResponse.getMaxShipDepth() == null
                  ? ""
                  : portBerthInfoResponse.getMaxShipDepth().toString());
          berthDetail.setPortId(portBerthInfoResponse.getPortId());
          berthDetail.setRegulationAndRestriction(
              portBerthInfoResponse.getRegulationAndRestriction());
          berthDetail.setMaxLoa(
              portBerthInfoResponse.getMaxLoa() == null
                  ? ""
                  : portBerthInfoResponse.getMaxLoa().toString());
          berthDetail.setUkc(
              portBerthInfoResponse.getMinUKC() == null ? "" : portBerthInfoResponse.getMinUKC());
          berthDetail.setMaxDraft(
              portBerthInfoResponse.getMaxDraft() == null
                  ? ""
                  : portBerthInfoResponse.getMaxDraft().toString());
          portDetailRequest.addBerthDetails(berthDetail);
        });
  }

  /**
   * Preparing response
   *
   * @param response
   * @param portInfoReply
   */
  public void buildPortInfoResponse(PortDetailResponse response, PortInfoReply portInfoReply) {
    PortDetails portDetails = new PortDetails();
    PortDetail portDetail = portInfoReply.getPort();
    portDetails.setCountry(portDetail.getCountryName());
    portDetails.setCountryId(portDetail.getCountryId());
    portDetails.setDensityOfWater(
        portDetail.getWaterDensity().isEmpty()
            ? null
            : new BigDecimal(portDetail.getWaterDensity()));
    portDetails.setMaxPermissibleDraft(
        portDetail.getMaxPermissibleDraft().isEmpty()
            ? null
            : new BigDecimal(portDetail.getMaxPermissibleDraft()));
    portDetails.setPortCode(portDetail.getCode());
    portDetails.setPortId(portDetail.getId());
    portDetails.setPortName(portDetail.getName());
    portDetails.setTimezoneId(portDetail.getTimezoneId());
    portDetails.setTimezone(portDetail.getTimezone());
    portDetails.setTimezoneAbbreviation(portDetail.getTimezoneAbbreviation());
    portDetails.setTimezoneOffsetVal(portDetail.getTimezoneOffsetVal());
    portDetails.setTideHeightHigh(
        portDetail.getTideHeightTo().isEmpty()
            ? null
            : new BigDecimal(portDetail.getTideHeightTo()));
    portDetails.setTideHeightLow(
        portDetail.getTideHeightFrom().isEmpty()
            ? null
            : new BigDecimal(portDetail.getTideHeightFrom()));
    portDetails.setLatitude(portDetail.getLat().isEmpty() ? null : portDetail.getLat());
    portDetails.setLongitude(portDetail.getLon().isEmpty() ? null : portDetail.getLon());
    portDetails.setAmbientTemperature(
        portDetail.getAmbientTemperature().isEmpty()
            ? null
            : new BigDecimal(portDetail.getAmbientTemperature()));
    List<PortBerthInfoResponse> berthList =
        setBerthInformationForThePorts(portDetail.getBerthDetailsList());
    portDetails.setBerthInfo(berthList);
    response.setPortDetails(portDetails);
  }
}
