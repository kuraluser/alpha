/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain;

import com.cpdss.common.utils.Doc;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Port Details To Redis Cache
 *
 * @author johnsoorajxavier
 * @since 15-03-2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortInfo implements Doc {

  private List<PortInfoDomain> ports;

  @Data
  public static class PortInfoDomain {
    private Long id;
    private String name;
  }
}
