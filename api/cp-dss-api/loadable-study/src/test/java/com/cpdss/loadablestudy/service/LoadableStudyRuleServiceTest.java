/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyRuleInput;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleInputRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadableStudyRuleService.class})
public class LoadableStudyRuleServiceTest {

  @Autowired private LoadableStudyRuleService loadableStudyRuleService;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;

  @Test
  void testGetLoadableStudyRules() {
    com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.newBuilder()
            .setVesselId(1L)
            .setDuplicatedFromId(1L)
            .build();
    Mockito.when(
            this.loadableStudyRepository.findByIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadablestudy());
    Mockito.when(
            loadableStudyRuleRepository.findByLoadableStudyAndVesselXIdAndIsActive(
                Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLSR());
    try {
      var loadableStudyRules = this.loadableStudyRuleService.getLoadableStudyRules(request);
      assertEquals(Optional.of(1L), Optional.of(loadableStudyRules.get(0).getRuleTypeXId()));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<LoadableStudy> getLoadablestudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    return Optional.of(loadableStudy);
  }

  private List<LoadableStudyRules> getLSR() {
    List<LoadableStudyRules> loadableStudyRules = new ArrayList<>();
    LoadableStudyRules loadableStudyRules1 = new LoadableStudyRules();
    loadableStudyRules1.setIsActive(true);
    loadableStudyRules1.setRuleTypeXId(1L);
    loadableStudyRules.add(loadableStudyRules1);
    return loadableStudyRules;
  }

  @Test
  void testSaveDuplicateLoadableStudyRules() {
    List<LoadableStudyRules> listOfExistingLSRules = new ArrayList<>();
    LoadableStudyRules loadableStudyRules = new LoadableStudyRules();
    loadableStudyRules.setId(1L);
    loadableStudyRules.setRuleTypeXId(1L);
    loadableStudyRules.setDisplayInSettings(false);
    loadableStudyRules.setIsEnable(false);
    loadableStudyRules.setIsHardRule(false);
    loadableStudyRules.setIsActive(false);
    loadableStudyRules.setNumericPrecision(1L);
    loadableStudyRules.setNumericScale(1L);
    loadableStudyRules.setParentRuleXId(1L);
    loadableStudyRules.setLoadableStudyRuleInputs(getLLSRI());
    loadableStudyRules.setVesselRuleXId(1L);
    listOfExistingLSRules.add(loadableStudyRules);
    LoadableStudy currentLoableStudy = new LoadableStudy();
    com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.newBuilder()
            .setVesselId(1L)
            .build();
    this.loadableStudyRuleService.saveDuplicateLoadableStudyRules(
        listOfExistingLSRules, currentLoableStudy, request);
    Mockito.verify(loadableStudyRuleRepository).saveAll(Mockito.anyList());
  }

  private List<LoadableStudyRuleInput> getLLSRI() {
    List<LoadableStudyRuleInput> loadableStudyRuleInputs = new ArrayList<>();
    LoadableStudyRuleInput loadableStudyRuleInput = new LoadableStudyRuleInput();
    loadableStudyRuleInput.setId(1L);
    loadableStudyRuleInput.setPrefix("1");
    loadableStudyRuleInput.setDefaultValue("1");
    loadableStudyRuleInput.setTypeValue("1");
    loadableStudyRuleInput.setMaxValue("1");
    loadableStudyRuleInput.setMinValue("1");
    loadableStudyRuleInput.setSuffix("1");
    loadableStudyRuleInput.setIsActive(true);
    loadableStudyRuleInput.setIsMandatory(false);
    loadableStudyRuleInputs.add(loadableStudyRuleInput);
    return loadableStudyRuleInputs;
  }
}
