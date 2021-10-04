/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest;
import com.cpdss.common.generated.discharge_plan.PortData;
import com.cpdss.common.utils.Utils;
import com.cpdss.dischargeplan.entity.CowPlanDetail;
import com.cpdss.dischargeplan.entity.CowTankDetail;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.repository.CowPlanDetailRepository;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargePlanSynchronizeService {

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired DischargeInformationService dischargeInformationService;

  @Autowired CowPlanDetailRepository cowPlanDetailRepository;

  public void saveDischargeInformation(DischargeStudyDataTransferRequest request) {
    log.info("Discharge Study Synchronization Starts");
    List<PortData> portDataList = request.getPortDataList();
    List<DischargeInformation> infos = new ArrayList<>();
    portDataList.stream()
        .forEach(
            port -> {
              DischargeInformation dischargeInformation = new DischargeInformation();
              dischargeInformation.setVoyageXid(request.getVoyageId());
              dischargeInformation.setVesselXid(request.getVesselId());
              dischargeInformation.setDischargingPatternXid(request.getDischargePatternId());
              dischargeInformation.setPortRotationXid(port.getPortRotationId());
              dischargeInformation.setSynopticTableXid(port.getSynopticTableId());
              dischargeInformation.setIsActive(true);
              dischargeInformation.setPortXid(port.getPortId());
              infos.add(dischargeInformation);
              log.info("Discharge Study Synchronization Port Data - {}", Utils.toJson(port));
            });
    dischargeInformationRepository.saveAll(infos);
    log.info("Discharge Study Synchronization Finished");
  }

  public void saveCowDetailsForDischargeStudy(DischargeStudyDataTransferRequest request) {
    for (PortData portData : request.getPortDataList()) {
      try {
        DischargeInformation entity =
            this.dischargeInformationService.getDischargeInformation(
                request.getVesselId(), request.getVoyageId(), portData.getPortRotationId());
        if (entity != null) {
          CowPlanDetail cowPlanDetail = new CowPlanDetail();
          cowPlanDetail.setDischargeInformation(entity);
          cowPlanDetail.setCowOperationType(portData.getCowDetails().getCowOptionTypeValue());
          cowPlanDetail.setCowPercentage(new BigDecimal(portData.getCowDetails().getPercent()));
          if (portData.getCowDetails().getCowTankDetails() != null) {
            portData
                .getCowDetails()
                .getCowTankDetails()
                .getTankIdsList()
                .forEach(
                    v -> {
                      CowTankDetail tankDetails = new CowTankDetail();
                      tankDetails.setCowPlanDetail(cowPlanDetail);
                      tankDetails.setDischargingXid(entity.getId());
                      tankDetails.setCowTypeXid(
                          portData.getCowDetails().getCowTankDetails().getCowType().getNumber());
                      tankDetails.setTankXid(v);
                    });
          }
          cowPlanDetailRepository.save(cowPlanDetail);
          log.info("Cow Plan Details saved for Port R Id - {}", portData.getPortRotationId());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
