/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.domain.CharterDetailsSpecification;
import com.cpdss.vesselinfo.domain.FilterCriteria;
import com.cpdss.vesselinfo.entity.*;
import com.cpdss.vesselinfo.repository.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CharterService for charter related apis like fetching the list of charters, and manipulating the
 * same
 *
 * @author ciliya.s
 * @since 18-02-2022
 */
@Transactional
@Log4j2
@Service
public class CharterService {

  @Autowired CharterDetailsRepository charterDetailsRepository;
  @Autowired CharterTypeRepository charterTypeRepository;
  @Autowired CharterCompanyRepository charterCompanyRepository;
  @Autowired VesselChartererMappingRepository vesselChartererMappingRepository;
  @Autowired VesselRepository vesselRepository;

  /**
   * method to fetch all the charter details(or filtered ones)
   *
   * @param charterDetailReply
   * @param request
   */
  public void getAllCharterDetails(
      com.cpdss.common.generated.VesselInfo.CharterDetailedReply.Builder charterDetailReply,
      VesselInfo.CharterInfoRequest request) {

    List<String> filterKeys =
        Arrays.asList("id", "name", "countryName", "companyName", "charterTypeName");
    Map<String, String> params = new HashMap<>();
    request.getParamList().forEach(param -> params.put(param.getKey(), param.getValue()));
    Map<String, String> filterParams =
        params.entrySet().stream()
            .filter(e -> filterKeys.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Specification<Charterer> specification =
        Specification.where(
            new CharterDetailsSpecification(new FilterCriteria("isActive", ":", true, "")));

    for (Map.Entry<String, String> entry : filterParams.entrySet()) {
      String filterKey = entry.getKey();
      String value = entry.getValue();
      if (filterKey.equals("companyName")) {
        specification =
            specification.and(
                new CharterDetailsSpecification(
                    new FilterCriteria(
                        "charterCompanyName", "like-with-join", value, "charterCompany")));
      } else if (filterKey.equals("charterTypeName")) {
        specification =
            specification.and(
                new CharterDetailsSpecification(
                    new FilterCriteria("charterTypeName", "like-with-join", value, "charterType")));
      } else {
        specification =
            specification.and(
                new CharterDetailsSpecification(new FilterCriteria(filterKey, "like", value, "")));
      }
    }

    // Paging and sorting
    Pageable paging =
        PageRequest.of(
            (int) request.getPageNo(),
            (int) request.getPageSize(),
            Sort.by(
                Sort.Direction.valueOf(request.getOrderBy().toUpperCase()), request.getSortBy()));
    Page<Charterer> pagedResult = this.charterDetailsRepository.findAll(specification, paging);

    List<Charterer> charterDetails = pagedResult.toList();

    if (null != charterDetails && !charterDetails.isEmpty()) {

      Set<Long> charterIds =
          charterDetails.stream().map(Charterer::getId).collect(Collectors.toSet());

      List<VesselChartererMapping> allVesselChartererMappings =
          this.vesselChartererMappingRepository.findBycharterer_idInAndIsActive(charterIds, true);

      charterDetails.forEach(
          charterer -> {
            VesselInfo.CharterDetailed.Builder charterDetailed =
                VesselInfo.CharterDetailed.newBuilder();

            charterDetailed.setId(charterer.getId() == null ? 0 : charterer.getId());
            charterDetailed.setCharterName(charterer.getName() == null ? "" : charterer.getName());
            charterDetailed.setCharterCompanyId(
                charterer.getCharterCompany() == null ? 0 : charterer.getCharterCompany().getId());
            charterDetailed.setCharterCompanyName(
                charterer.getCharterCompany() == null
                    ? ""
                    : charterer.getCharterCompany().getCharterCompanyName());

            charterDetailed.setCharterTypeName(
                charterer.getCharterType() == null
                    ? ""
                    : charterer.getCharterType().getCharterTypeName());
            charterDetailed.setCharterTypeId(
                charterer.getCharterType() == null ? 0 : charterer.getCharterType().getId());

            charterDetailed.setCharterCountryId(
                charterer.getCharterCountry() == null ? 0 : charterer.getCharterCountry());

            if (null != allVesselChartererMappings && !allVesselChartererMappings.isEmpty()) {

              List<VesselChartererMapping> vesselChartererMappings =
                  allVesselChartererMappings.stream()
                      .filter(vm -> (vm.getCharterer().getId() == charterer.getId()))
                      .collect(Collectors.toList());

              allVesselChartererMappings.removeAll(vesselChartererMappings);

              VesselInfo.CharterVesselMappingDetail.Builder VesselDetailed =
                  VesselInfo.CharterVesselMappingDetail.newBuilder();

              vesselChartererMappings.forEach(
                  vesselChartererMapping -> {
                    VesselDetailed.setVesselId(vesselChartererMapping.getVessel().getId());
                    VesselDetailed.setVesselName(vesselChartererMapping.getVessel().getName());
                    VesselDetailed.setId(vesselChartererMapping.getId());
                    VesselDetailed.setCharterId(charterDetailed.getId());
                    charterDetailed.addCharterVessels(VesselDetailed);
                  });
            }
            charterDetailReply.addCharterDetails(charterDetailed);
          });
    }

    charterDetailReply.setTotalElements(pagedResult.getTotalElements());
  }

  /**
   * to create or update the charter details
   *
   * @param request
   * @param charterDetailsReply
   * @throws GenericServiceException
   */
  public void saveCharterDetails(
      VesselInfo.CharterDetailed request, VesselInfo.CharterDetailReply.Builder charterDetailsReply)
      throws GenericServiceException {

    Charterer charterDetails;
    if (request.getId() == 0) {
      charterDetails = new Charterer();
    } else {
      charterDetails = this.charterDetailsRepository.getById(request.getId());
    }
    charterDetails.setIsActive(true);
    charterDetails.setName(request.getCharterName());

    /** charter type details* */
    if (request.getId() == 0
        || (charterDetails.getCharterType() != null
            && charterDetails.getCharterType().getId() != request.getCharterTypeId())) {

      ChartererType charterType = this.charterTypeRepository.getById(request.getCharterTypeId());

      if (charterType == null) {
        log.error("Unknown Charter Type ");
        throw new GenericServiceException(
            "Error in retrieving Charter Type",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      charterDetails.setCharterType(charterType);
    }

    /** charter company details* */
    if (request.getId() == 0
        || (charterDetails.getCharterCompany() != null
            && charterDetails.getCharterCompany().getId() != request.getCharterCompanyId())) {

      ChartererCompany charterCompany =
          this.charterCompanyRepository.getById(request.getCharterCompanyId());

      if (charterCompany == null) {
        log.error("Unknown Charter Company ");
        throw new GenericServiceException(
            "Error in retrieving Charter Company",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      charterDetails.setCharterCompany(charterCompany);
    }

    /** charter Country details* */
    charterDetails.setCharterCountry(request.getCharterCountryId());

    Charterer charterDetailEntity = this.charterDetailsRepository.save(charterDetails);
    VesselInfo.CharterDetailed.Builder charterDetailed = VesselInfo.CharterDetailed.newBuilder();
    charterDetailed.setId(charterDetailEntity.getId());
    charterDetailed.setCharterName(charterDetailEntity.getName());
    charterDetailsReply.setCharterDetail(charterDetailed);
  }

  /**
   * To save the charter<===>vessel mappings. Same charter can be mapped to more than one vessel
   *
   * @param request
   */
  public void saveCharterVesselMappings(VesselInfo.CharterVesselMappingRequest request) {

    Optional.of(request.getCharterVesselMappingsList())
        .ifPresent(
            charterVesselMappingDetails -> {
              charterVesselMappingDetails.forEach(
                  charterVesselMappingDetail -> {
                    Optional<Vessel> vesselWrapper =
                        this.vesselRepository.findByIdAndIsActiveTrue(
                            charterVesselMappingDetail.getVesselId());
                    vesselWrapper.ifPresent(
                        vessel -> {
                          Optional<Charterer> charterWrapper =
                              this.charterDetailsRepository.findByIdAndIsActiveTrue(
                                  charterVesselMappingDetail.getCharterId());

                          charterWrapper.ifPresent(
                              charter -> {
                                Optional<VesselChartererMapping> charterVesselMappingWrapper =
                                    this.vesselChartererMappingRepository
                                        .findByChartererAndVesselAndIsActiveTrue(
                                            charterVesselMappingDetail.getCharterId(),
                                            charterVesselMappingDetail.getVesselId());

                                if (charterVesselMappingWrapper.isEmpty()) {
                                  VesselChartererMapping charterVesselMapping =
                                      new VesselChartererMapping();
                                  charterVesselMapping.setCharterer(charter);
                                  charterVesselMapping.setVessel(vessel);
                                  charterVesselMapping.setIsActive(true);
                                  this.vesselChartererMappingRepository.save(charterVesselMapping);
                                }
                              });
                        });
                  });
            });
  }
}
