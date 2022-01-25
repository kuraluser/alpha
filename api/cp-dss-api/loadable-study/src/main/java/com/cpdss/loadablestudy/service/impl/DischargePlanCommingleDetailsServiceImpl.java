/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.CommingleCargoToDischargePortwiseDetails;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.repository.CommingleCargoToDischargePortwiseDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.service.DischargePlanCommingleDetailsService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class to implement methods related to Discharge plan commingle details
 *
 * @author sreemanikandan.k
 * @since 24/01/2022
 */
@Slf4j
@Service
@SuppressWarnings("unused")
public class DischargePlanCommingleDetailsServiceImpl
    implements DischargePlanCommingleDetailsService {

  @Autowired private LoadablePatternRepository loadablePatternRepository;

  @Autowired
  private CommingleCargoToDischargePortwiseDetailsRepository
      commingleCargoToDischargePortwiseDetailsRepository;

  /**
   * Method to fetch Discharge commingle details
   *
   * @param request DischargeCommingleRequest object
   * @param replyBuilder Builder of DischargeCommingleReply object
   * @throws GenericServiceException In case of failure
   */
  @Override
  public void fetchDischargeCommingleDetails(
      com.cpdss.common.generated.LoadableStudy.DischargeCommingleRequest request,
      com.cpdss.common.generated.LoadableStudy.DischargeCommingleReply.Builder replyBuilder)
      throws GenericServiceException {

    log.info("Inside fetchDischargeCommingleDetails method!");
    log.info(
        "Fetching Discharge Study Commingle details for discharge pattern {}",
        request.getDischargePatternId());

    // Find Discharge pattern using pattern id
    Optional<LoadablePattern> dischargePatternWrapper =
        loadablePatternRepository.findByIdAndIsActive(request.getDischargePatternId(), true);
    if (dischargePatternWrapper.isEmpty()) {
      log.error("Discharge pattern not found for Id {}", request.getDischargePatternId());
      throw new GenericServiceException(
          "Discharge pattern not found for Id " + request.getDischargePatternId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    // Fetch commingle details using discharge study
    List<CommingleCargoToDischargePortwiseDetails> commingleCargoToDischargePortWiseDetailsList =
        commingleCargoToDischargePortwiseDetailsRepository.findByDischargeStudyIdAndIsActiveTrue(
            dischargePatternWrapper.get().getLoadableStudy().getId());

    buildDischargeCommingleDetails(commingleCargoToDischargePortWiseDetailsList, replyBuilder);
  }

  /**
   * Method to build Discharge Commingle Reply
   *
   * @param commingleCargoToDischargePortWiseDetailsList List of
   *     CommingleCargoToDischargePortWiseDetails objects
   * @param replyBuilder Builder of DischargeCommingleReply object
   */
  private void buildDischargeCommingleDetails(
      List<CommingleCargoToDischargePortwiseDetails> commingleCargoToDischargePortWiseDetailsList,
      LoadableStudy.DischargeCommingleReply.Builder replyBuilder) {

    log.info("Inside buildDischargeCommingleDetails method!");

    commingleCargoToDischargePortWiseDetailsList.forEach(
        commingleCargoToDischargePortWiseDetails -> {
          LoadableStudy.LoadablePatternCommingleDetailsReply.Builder
              dischargePatternCommingleDetailsReplyBuilder =
                  LoadableStudy.LoadablePatternCommingleDetailsReply.newBuilder();

          // Set fields
          BeanUtils.copyProperties(
              commingleCargoToDischargePortWiseDetails,
              dischargePatternCommingleDetailsReplyBuilder);
          dischargePatternCommingleDetailsReplyBuilder.setCargo1Abbrivation(
              commingleCargoToDischargePortWiseDetails.getCargo1Abbreviation());
          dischargePatternCommingleDetailsReplyBuilder.setCargo2Abbrivation(
              commingleCargoToDischargePortWiseDetails.getCargo2Abbreviation());
          dischargePatternCommingleDetailsReplyBuilder.setCargo1Quantity(
              String.valueOf(commingleCargoToDischargePortWiseDetails.getQuantity1MT()));
          dischargePatternCommingleDetailsReplyBuilder.setCargo2Quantity(
              String.valueOf(commingleCargoToDischargePortWiseDetails.getQuantity2MT()));

          replyBuilder.addDischargePatternCommingleDetails(
              dischargePatternCommingleDetailsReplyBuilder.build());
        });
  }
}
