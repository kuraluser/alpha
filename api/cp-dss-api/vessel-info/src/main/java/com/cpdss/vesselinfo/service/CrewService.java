/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.domain.CrewDetailsSpecification;
import com.cpdss.vesselinfo.domain.CrewVesselSpecification;
import com.cpdss.vesselinfo.domain.FilterCriteria;
import com.cpdss.vesselinfo.entity.CrewDetails;
import com.cpdss.vesselinfo.entity.CrewRank;
import com.cpdss.vesselinfo.entity.CrewVesselMapping;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.repository.CrewDetailsRepository;
import com.cpdss.vesselinfo.repository.CrewRankRepository;
import com.cpdss.vesselinfo.repository.CrewVesselMappingRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Crew Service for crew related services Author: Athul c.p */
@Transactional
@Log4j2
@Service
public class CrewService {

  @Autowired CrewRankRepository crewRankRepository;
  @Autowired CrewVesselMappingRepository crewVesselMappingRepository;
  @Autowired CrewDetailsRepository crewDetailsRepository;
  @Autowired VesselRepository vesselRepository;

  /**
   * To get all crew rank
   *
   * @param builder
   */
  public void getAllCrewRank(VesselInfo.CrewReply.Builder builder) {
    List<Object[]> list = crewRankRepository.findActiveCrewRank();
    if (list == null) return;
    for (Object[] obj : list) {
      VesselInfo.CrewRank.Builder crewRank = VesselInfo.CrewRank.newBuilder();
      crewRank.setId(Long.valueOf(obj[0].toString()));
      String name = (String) obj[1];
      if (name != null) crewRank.setCrewName(name);
      String shortName = (String) obj[2];
      if (shortName != null) crewRank.setRankShortName(shortName);
      builder.addCrewRanks(crewRank);
    }
  }

  /**
   * to find all crew vessel mapping
   *
   * @param vesselName
   * @param builder
   */
  public void getAllCrewVesselMapping(
      String vesselName, VesselInfo.CrewVesselReply.Builder builder) {
    List<CrewVesselMapping> crewVesselMappings = new ArrayList<>();
    if (StringUtils.isEmpty(vesselName)) {
      crewVesselMappings = this.crewVesselMappingRepository.findAll();
    } else {
      Specification<CrewVesselMapping> specification =
          Specification.where(
              new CrewVesselSpecification(
                  new FilterCriteria("name", "like-with-join", vesselName, "vessel")));
      crewVesselMappings = this.crewVesselMappingRepository.findAll(specification);
    }

    if (crewVesselMappings == null) return;

    crewVesselMappings.forEach(
        crewVesselMapping -> {
          VesselInfo.CrewVesselMappingDetail.Builder crewVesselMappingDetail =
              VesselInfo.CrewVesselMappingDetail.newBuilder();
          crewVesselMappingDetail.setId(crewVesselMapping.getId());
          crewVesselMappingDetail.setCrewId(
              crewVesselMapping.getCrewXId() == null ? 0 : crewVesselMapping.getCrewXId());
          crewVesselMappingDetail.setVesselId(
              crewVesselMapping.getVessel().getId() == null
                  ? 0
                  : crewVesselMapping.getVessel().getId());
          crewVesselMappingDetail.setVesselName(
              crewVesselMapping.getVessel().getName() == null
                  ? ""
                  : crewVesselMapping.getVessel().getName());
          builder.addVessels(crewVesselMappingDetail);
        });
  }

  public void getAllCrewDetails(
      com.cpdss.common.generated.VesselInfo.CrewDetailedReply.Builder crewDetailReply,
      VesselInfo.VesselsInfoRequest request) {
    List<String> filterKeys = Arrays.asList("id", "crewName", "rankName", "vesselName");
    Map<String, String> params = new HashMap<>();
    request.getParamList().forEach(param -> params.put(param.getKey(), param.getValue()));
    Map<String, String> filterParams =
        params.entrySet().stream()
            .filter(e -> filterKeys.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Specification<CrewDetails> specification =
        Specification.where(
            new CrewDetailsSpecification(new FilterCriteria("isActive", ":", true, "")));
    for (Map.Entry<String, String> entry : filterParams.entrySet()) {
      String filterKey = entry.getKey();
      String value = entry.getValue();
      if ("vesselName".equalsIgnoreCase(filterKey)) {
        specification =
            specification.and(
                new CrewDetailsSpecification(
                    new FilterCriteria("id", "in", request.getCrewXIdsList(), "")));
      } else if (filterKey.equals("rankName")) {
        specification =
            specification.and(
                new CrewDetailsSpecification(
                    new FilterCriteria("rankName", "like-with-join", value, "crewRank")));
      } else {
        specification =
            specification.and(
                new CrewDetailsSpecification(new FilterCriteria(filterKey, "like", value, "")));
      }
    }
    // Paging and sorting
    Pageable paging =
        PageRequest.of(
            (int) request.getPageNo(),
            (int) request.getPageSize(),
            Sort.by(
                Sort.Direction.valueOf(request.getOrderBy().toUpperCase()), request.getSortBy()));
    Page<CrewDetails> pagedResult = this.crewDetailsRepository.findAll(specification, paging);
    List<CrewDetails> crewDetails = pagedResult.toList();
    crewDetails.forEach(
        crew -> {
          VesselInfo.CrewDetailed.Builder crewDetailed = VesselInfo.CrewDetailed.newBuilder();
          crewDetailed.setId(crew.getId() == null ? 0 : crew.getId());
          crewDetailed.setCrewName(crew.getCrewName() == null ? "" : crew.getCrewName());
          crewDetailed.setCrewRank(
              crew.getCrewRank() == null ? "" : crew.getCrewRank().getRankName());
          crewDetailed.setCrewRankId(crew.getCrewRank() == null ? 0 : crew.getCrewRank().getId());
          crewDetailReply.addCrewDetails(crewDetailed);
        });
    crewDetailReply.setTotalElements(pagedResult.getTotalElements());
  }

  public void saveCrewDetails(
      VesselInfo.CrewDetailed request, VesselInfo.CrewDetailsReply.Builder crewDetailsReply)
      throws GenericServiceException {
    CrewDetails crewDetails;
    Specification<CrewDetails> specification =
        Specification.where(
            new CrewDetailsSpecification(new FilterCriteria("isActive", ":", true, "")));
    specification =
        specification.and(
            new CrewDetailsSpecification(
                new FilterCriteria("crewName", ":", request.getCrewName(), "")));
    if (request.getId() == 0) {
      crewDetails = new CrewDetails();
    } else {
      crewDetails = this.crewDetailsRepository.getById(request.getId());
      specification =
          specification.and(
              new CrewDetailsSpecification(
                  new FilterCriteria("id", "not-equal", request.getId(), "")));
    }
    Optional<CrewDetails> crewDetailsOptional = this.crewDetailsRepository.findOne(specification);
    if (crewDetailsOptional.isPresent()) {
      log.info("Crew Name already exist");
      throw new GenericServiceException(
          "Error in retrieving CrewRank",
          CommonErrorCodes.E_CPDSS_CREW_NAME_EXISTS,
          HttpStatusCode.BAD_REQUEST);
    }
    crewDetails.setIsActive(true);
    if (request.getId() == 0
        || (crewDetails.getCrewRank() != null
            && crewDetails.getCrewRank().getId() != request.getCrewRankId())) {
      CrewRank crewRank = this.crewRankRepository.getById(request.getCrewRankId());
      if (crewRank == null) {
        log.info("Unknown Crew Rank ");
        throw new GenericServiceException(
            "Error in retrieving CrewRank",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      crewDetails.setCrewRank(crewRank);
    }
    crewDetails.setCrewName(request.getCrewName());
    CrewDetails crewDetailEntity = this.crewDetailsRepository.save(crewDetails);
    VesselInfo.CrewDetailed.Builder crewDetailed = VesselInfo.CrewDetailed.newBuilder();
    crewDetailed.setId(crewDetailEntity.getId());
    crewDetailed.setCrewName(crewDetailEntity.getCrewName());
    crewDetailsReply.setCrewDetails(crewDetailed);
  }

  public void saveAllCrewVesselMapping(VesselInfo.CrewVesselMappingRequest request) {
    Optional.of(request.getCrewVesselMappingsList())
        .ifPresent(
            crewVesselMappingDetails -> {
              crewVesselMappingDetails.forEach(
                  crewVesselMappingDetail -> {
                    Optional<Vessel> vesselWrapper =
                        this.vesselRepository.findByIdAndIsActiveTrue(
                            crewVesselMappingDetail.getVesselId());
                    vesselWrapper.ifPresent(
                        vessel -> {
                          Optional<CrewVesselMapping> crewVesselMappingWrapper =
                              this.crewVesselMappingRepository.findByCargoXIdAndPortIdAndIsActive(
                                  crewVesselMappingDetail.getCrewId(),
                                  crewVesselMappingDetail.getVesselId(),
                                  true);
                          if (crewVesselMappingWrapper.isEmpty()) {
                            CrewVesselMapping crewVesselMapping = new CrewVesselMapping();
                            crewVesselMapping.setCrewXId(crewVesselMappingDetail.getCrewId());
                            crewVesselMapping.setVessel(vessel);
                            crewVesselMapping.setIsActive(true);
                            this.crewVesselMappingRepository.save(crewVesselMapping);
                          }
                        });
                  });
            });
  }

  /**
   * to get crew details based on vessel and rank
   *
   * @param crewDetailReply
   * @param request
   */
  public void getAllCrewDetailsByRank(
      com.cpdss.common.generated.VesselInfo.CrewDetailedReply.Builder crewDetailReply,
      VesselInfo.VesselsInfoRequest request) {

    List<Long> crewXIds = new ArrayList<>();
    crewXIds.addAll(request.getCrewXIdsList());
    if (request.getVesselId() != 0) {
      List<Long> reponseCrewXIds = crewVesselMappingRepository.getAllCrewXId(request.getVesselId());
      if (reponseCrewXIds != null) {
        crewXIds.addAll(reponseCrewXIds);
      }
    }

    Specification<CrewDetails> specification =
        Specification.where(
            new CrewDetailsSpecification(new FilterCriteria("isActive", ":", true, "")));

    if (!CollectionUtils.isEmpty(crewXIds)) {
      specification =
          specification.and(
              new CrewDetailsSpecification(new FilterCriteria("id", "in", crewXIds, "")));
    }
    if (request.getRankId() != 0) {
      specification =
          specification.and(
              new CrewDetailsSpecification(
                  new FilterCriteria("id", "join-with-equals", request.getRankId(), "crewRank")));
    }
    if (!StringUtils.isEmpty(request.getRankName())) {
      specification =
          specification.and(
              new CrewDetailsSpecification(
                  new FilterCriteria(
                      "rankName", "join-with-equals", request.getRankName(), "crewRank")));
    }

    List<CrewDetails> crewDetails = this.crewDetailsRepository.findAll(specification);
    if (crewDetails == null) return;
    crewDetails.forEach(
        crew -> {
          VesselInfo.CrewDetailed.Builder crewDetailed = VesselInfo.CrewDetailed.newBuilder();
          crewDetailed.setId(crew.getId() == null ? 0 : crew.getId());
          crewDetailed.setCrewName(crew.getCrewName() == null ? "" : crew.getCrewName());
          crewDetailed.setCrewRank(
              crew.getCrewRank() == null ? "" : crew.getCrewRank().getRankName());
          crewDetailed.setCrewRankId(crew.getCrewRank() == null ? 0 : crew.getCrewRank().getId());
          crewDetailReply.addCrewDetails(crewDetailed);
        });
    crewDetailReply.setTotalElements(crewDetails.size());
  }
}
