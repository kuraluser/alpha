/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.dischargeplan;

import com.cpdss.common.generated.Common;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CowPlan {

  @JsonIgnore private Common.COW_OPTION_TYPE cow_option_type;

  private String cowOptionType;
  private Integer cowOption;

  private String cowPercentage;
  private String cowStart; // numeric
  private String cowEnd; // numeric
  private String cowDuration;
  private String cowTrimMin;
  private String cowTrimMax;
  private Boolean needFreshCrudeStorage;
  private Boolean needFlushingOil;

  private Boolean washTanksWithDifferentCargo;

  @JsonAlias("topCowTankIds")
  private List<Long> topCow;

  @JsonAlias("bottomCowTankIds")
  private List<Long> bottomCow;

  @JsonAlias("allCowTankIds")
  private List<Long> allCow;

  private List<CargoForCowDetails> cargoCow;
}
