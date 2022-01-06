/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetailsTemp;
import com.cpdss.loadablestudy.service.builder.LoadablePlanBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadablePlanBuilder.class,
    })
public class LoadablePlanBuilderTest {
  @Autowired LoadablePlanBuilder loadablePlanBuilder;

  @Test
  void testBuildLoadablePlanQuantity() {
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        LoadableStudy.LoadablePattern.newBuilder();

    loadablePlanBuilder.buildLoadablePlanQuantity(getPlanQuantityList(), replyBuilder);
    assertEquals(1l, replyBuilder.getLoadableQuantityCargoDetails(0).getId());
  }

  @Test
  void testBuildLoadablePlanCommingleDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        LoadableStudy.LoadablePattern.newBuilder();

    loadablePlanBuilder.buildLoadablePlanCommingleDetails(
        Arrays.asList(getCommingleDetails()), replyBuilder);
    assertEquals(1l, replyBuilder.getLoadablePlanStowageDetails(0).getId());
  }

  @Test
  void testBuildBallastGridDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder replyBuilder =
        LoadableStudy.LoadablePattern.newBuilder();

    loadablePlanBuilder.buildBallastGridDetails(
        getBallastDetails(), getStowageDetailsTemps(), replyBuilder);
    assertEquals(1l, replyBuilder.getLoadablePlanBallastDetails(0).getId());
  }

  @Test
  void testSetTempBallastDetails() {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder replyBuilder =
        LoadableStudy.LoadablePlanBallastDetails.newBuilder();

    loadablePlanBuilder.setTempBallastDetails(
        getBallastDetails().get(0), getStowageDetailsTemps(), replyBuilder);
    assertEquals("1", replyBuilder.getRdgLevel());
  }

  private List<LoadablePlanBallastDetails> getBallastDetails() {
    List<LoadablePlanBallastDetails> loadablePlanBallastDetails = new ArrayList<>();
    LoadablePlanBallastDetails details = new LoadablePlanBallastDetails();
    details.setId(1l);
    details.setCorrectedLevel("1");
    details.setCorrectionFactor("1");
    details.setCubicMeter("1");
    details.setInertia("1");
    details.setLcg("1");
    details.setMetricTon("1");
    details.setRdgLevel("1");
    details.setPercentage("1");
    details.setSg("1");
    details.setTcg("1");
    details.setVcg("1");
    details.setTankName("1");
    details.setColorCode("1");
    details.setTankId(1l);
    loadablePlanBallastDetails.add(details);
    return loadablePlanBallastDetails;
  }

  private List<LoadablePlanStowageDetailsTemp> getStowageDetailsTemps() {
    List<LoadablePlanStowageDetailsTemp> ballastTempList = new ArrayList<>();
    LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
        new LoadablePlanStowageDetailsTemp();
    loadablePlanStowageDetailsTemp.setRdgUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectedUllage(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setCorrectionFactor(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setQuantity(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setFillingRatio(new BigDecimal(1));
    loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(getBallastDetails().get(0));
    ballastTempList.add(loadablePlanStowageDetailsTemp);
    return ballastTempList;
  }

  private LoadablePlanCommingleDetails getCommingleDetails() {
    LoadablePlanCommingleDetails commingleDetails = new LoadablePlanCommingleDetails();
    commingleDetails.setId(1l);
    commingleDetails.setTankId(1l);
    commingleDetails.setTankName("1");
    commingleDetails.setTemperature("1");
    commingleDetails.setSlopQuantity("1");
    commingleDetails.setGrade("1");
    commingleDetails.setPriority(1);
    commingleDetails.setQuantity("1");
    commingleDetails.setGrade("1");
    commingleDetails.setLoadingOrder(1);
    commingleDetails.setApi("1");
    commingleDetails.setCorrectedUllage("1");
    commingleDetails.setCorrectionFactor("1");
    commingleDetails.setFillingRatio("1");
    commingleDetails.setRdgUllage("1");
    commingleDetails.setCommingleColour("1");
    commingleDetails.setCargo1Mt("1");
    commingleDetails.setCargo2Mt("1");
    commingleDetails.setCargo1Lt("1");
    commingleDetails.setCargo2Lt("1");
    commingleDetails.setCargo1Abbreviation("1");
    commingleDetails.setCargo2Abbreviation("1");
    commingleDetails.setCargo1NominationId(1l);
    commingleDetails.setCargo2NominationId(1l);
    commingleDetails.setCargo1Percentage("1");
    commingleDetails.setCargo2Percentage("1");
    commingleDetails.setCargo2BblsDbs("1");
    commingleDetails.setCargo2Bbls60f("1");
    commingleDetails.setCargo2Kl("1");
    commingleDetails.setCargo1BblsDbs("1");
    commingleDetails.setCargo1Bbls60f("1");
    commingleDetails.setCargo1Kl("1");
    return commingleDetails;
  }

  private List<LoadablePlanQuantity> getPlanQuantityList() {
    List<LoadablePlanQuantity> quantityList = new ArrayList<>();
    LoadablePlanQuantity quantity = new LoadablePlanQuantity();
    quantity.setId(1l);
    quantity.setGrade("1");
    quantity.setEstimatedTemperature(new BigDecimal(1));
    quantity.setCargoNominationTemperature(new BigDecimal(1));
    quantity.setOrderBblsDbs("1");
    quantity.setOrderBbls60f("1");
    quantity.setLoadableBblsDbs("1");
    quantity.setLoadableBbls60f("1");
    quantity.setLoadableLt("1");
    quantity.setLoadableKl("1");
    quantity.setLoadableMt("1");
    quantity.setCargoXId(1l);
    quantity.setDifferencePercentage("1");
    quantity.setDifferenceColor("1");
    quantity.setSlopQuantity("1");
    quantity.setCargoNominationId(1L);
    quantity.setTimeRequiredForLoading("1");
    quantity.setMaxTolerence("1");
    quantity.setMinTolerence("1");
    quantity.setPriority(1);
    quantity.setLoadableMt("1");
    quantity.setOrderQuantity(new BigDecimal(1));
    quantity.setCargoAbbreviation("1");
    quantity.setCargoColor("1");
    quantity.setLoadingOrder(1);
    quantity.setEstimatedApi(new BigDecimal(1));
    quantity.setCargoNominationTemperature(new BigDecimal(1));
    quantityList.add(quantity);
    return quantityList;
  }
}
