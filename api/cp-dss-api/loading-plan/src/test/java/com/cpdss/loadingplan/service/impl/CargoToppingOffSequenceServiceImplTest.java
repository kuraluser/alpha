/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.CargoToppingOffSequence;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.CargoToppingOffSequenceRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import com.cpdss.loadingplan.service.LoadingInformationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {CargoToppingOffSequenceServiceImpl.class})
public class CargoToppingOffSequenceServiceImplTest {

  @Autowired CargoToppingOffSequenceServiceImpl cargoToppingOffSequenceService;

  @MockBean CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean LoadingInformationService loadingInformationService;

  @Test
  void testSaveCargoToppingOffSequenceList() {
    List<LoadingPlanModels.CargoToppingOffSequence> cargoToppingOffSequencesList =
        new ArrayList<>();
    LoadingPlanModels.CargoToppingOffSequence sequence =
        LoadingPlanModels.CargoToppingOffSequence.newBuilder()
            .setPortRotationId(1L)
            .setCargoXId(1L)
            .setFillingRatio("1")
            .setOrderNumber(1)
            .setRemarks("1")
            .setTankXId(1L)
            .setUllage("1")
            .setWeight("1")
            .setApi("1")
            .setTemperature("1")
            .setDisplayOrder(1)
            .setCargoNominationId(1)
            .setAbbreviation("1")
            .build();
    cargoToppingOffSequencesList.add(sequence);
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setPortRotationXId(1L);
    this.cargoToppingOffSequenceService.saveCargoToppingOffSequenceList(
        cargoToppingOffSequencesList, loadingInformation);
    Mockito.verify(cargoToppingOffSequenceRepository)
        .save(Mockito.any(com.cpdss.loadingplan.entity.CargoToppingOffSequence.class));
  }

  @Test
  void testSaveCargoToppingOffSequences() {
    List<LoadingPlanModels.LoadingToppingOff> toppingOffSequenceList = new ArrayList<>();
    LoadingPlanModels.LoadingToppingOff toppingOff =
        LoadingPlanModels.LoadingToppingOff.newBuilder()
            .setId(1L)
            .setCargoId(1L)
            .setFillingRatio("1")
            .setOrderNumber(1)
            .setRemark("1")
            .setQuantity("1")
            .setTankId(1L)
            .setUllage("1")
            .setDisplayOrder(1)
            .setLoadingInfoId(1L)
            .build();
    Mockito.when(cargoToppingOffSequenceRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getCTOS());
    Mockito.when(loadingInformationRepository.findByIdAndIsActiveTrue(Mockito.anyLong()))
        .thenReturn(getOLI());
    try {
      this.cargoToppingOffSequenceService.saveCargoToppingOffSequences(toppingOffSequenceList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Optional<CargoToppingOffSequence> getCTOS() {
    CargoToppingOffSequence toppingOffSequence = new CargoToppingOffSequence();
    toppingOffSequence.setId(1L);
    return Optional.of(toppingOffSequence);
  }

  private Optional<LoadingInformation> getOLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    return Optional.of(loadingInformation);
  }

  @Test
  void testUpdateUllageFromLsAlgo() {
    LoadingPlanModels.UpdateUllageLoadingRequest request =
        LoadingPlanModels.UpdateUllageLoadingRequest.newBuilder()
            .setLoadingInfoId(1L)
            .setVoyageId(1L)
            .setVesselId(1L)
            .setPortRotationId(1L)
            .setCorrectedUllage("1")
            .setQuantity("1")
            .setFillingRatio("1")
            .setTankId(1L)
            .setCargoToppingOffId(1L)
            .build();
    LoadingPlanModels.UpdateUllageLoadingReplay.Builder replayBuilder =
        LoadingPlanModels.UpdateUllageLoadingReplay.newBuilder();
    Mockito.when(
            loadingInformationService.getLoadingInformation(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(
            this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(
                Mockito.any()))
        .thenReturn(getOCTOS());
    try {
      this.cargoToppingOffSequenceService.updateUllageFromLsAlgo(request, replayBuilder);
      assertEquals("SUCCESS", replayBuilder.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<CargoToppingOffSequence> getOCTOS() {
    List<CargoToppingOffSequence> list = new ArrayList<>();
    com.cpdss.loadingplan.entity.CargoToppingOffSequence sequence = new CargoToppingOffSequence();
    sequence.setTankXId(1L);
    sequence.setId(1L);
    list.add(sequence);
    return list;
  }
}
