/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.loadingplan;

import com.cpdss.gateway.domain.LoadableQuantityCargoDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

/**
 * DTO to transfer cargo to be loaded details
 *
 * @author sreemanikandan.k
 * @since 07/01/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargoApiTempDetails {

  private List<LoadableQuantityCargoDetails> loadableQuantityCargoDetails;
}
