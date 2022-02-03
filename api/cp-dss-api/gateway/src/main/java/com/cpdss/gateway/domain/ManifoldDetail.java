/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Class to set details regarding manifold of berths in ports
 *
 * @author sreemanikandan.k
 * @since 02/02/2022
 * @version 02/02/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManifoldDetail {

  @NotEmpty(message = "Manifold name is mandatory")
  private String manifoldName;

  @NotNull(message = "Manifold connection number is mandatory")
  private Integer connectionNumber;

  @NotNull(message = "Manifold size is mandatory")
  private Long manifoldSize;

  @NotNull(message = "Manifold height is mandatory")
  private BigDecimal manifoldHeight;

  @NotNull(message = "Max pressure is mandatory")
  private BigDecimal maxPressure;

  @NotNull(message = "Max loading rate is mandatory")
  private Long maxLoadingRate;

  @NotNull(message = "Max discharge rate is mandatory")
  private Long maxDischargeRate;
}
