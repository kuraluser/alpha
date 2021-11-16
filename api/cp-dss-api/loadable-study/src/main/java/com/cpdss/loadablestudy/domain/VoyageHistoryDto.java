/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.domain;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.VOYAGE_DATE_FORMAT;

import com.cpdss.loadablestudy.entity.Voyage;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoyageHistoryDto {

  private Long voyageId;
  private String voyageNo;
  private String actualStartDate;
  private String actualEndDate;
  private List<CargoNomination> cargoNominations;
  private List<PlanStowage> planStowageDetails;
  private static DateTimeFormatter dft = DateTimeFormatter.ofPattern(VOYAGE_DATE_FORMAT);

  public VoyageHistoryDto() {}

  public VoyageHistoryDto(Voyage voyage) {
    this.voyageId = voyage.getId();
    this.voyageNo = voyage.getVoyageNo();
    this.actualStartDate = voyage.getActualStartDate().format(dft);
    this.actualEndDate = voyage.getActualEndDate().format(dft);
    this.cargoNominations = new ArrayList<>();
    this.planStowageDetails = new ArrayList<>();
  }

  public void addAllCargoNominations(List<com.cpdss.loadablestudy.entity.CargoNomination> cnl) {
    if (!cnl.isEmpty()) {
      for (com.cpdss.loadablestudy.entity.CargoNomination var : cnl) {
        CargoNomination cnDto = new CargoNomination();
        cnDto.setCargoId(var.getCargoXId());
        cnDto.setColor(var.getColor());
        cnDto.setAbbreviation(var.getAbbreviation());
        cnDto.setId(var.getId());
        cnDto.isCondensateCargo = var.getIsCondensateCargo();
        this.cargoNominations.add(cnDto);
      }
    }
  }

  public void addAllStowageDetails(
      List<com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails> stowage) {
    if (!stowage.isEmpty()) {
      for (com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails var : stowage) {
        PlanStowage stoDto = new PlanStowage();
        stoDto.setId(var.getId());
        stoDto.setTankId(var.getTankId());
        stoDto.setCargoNominationId(var.getCargoNominationId());
        this.planStowageDetails.add(stoDto);
      }
    }
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  class CargoNomination {
    Long cargoId;
    String color;
    String abbreviation;
    Long id;
    Boolean isCondensateCargo;
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  class PlanStowage {
    Long id;
    Long tankId;
    Long cargoNominationId;
  }
}
