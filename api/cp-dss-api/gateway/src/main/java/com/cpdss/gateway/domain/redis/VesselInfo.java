/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.domain.redis;

import com.cpdss.common.utils.Doc;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Vessel Details To Redis Cache
 *
 * @author johnsoorajxavier
 * @since 15-03-2021
 */
@Data
@AllArgsConstructor
public class VesselInfo implements Doc {

  private List<VesselInfoDomain> vessels;

  public static class VesselInfoDomain {
    private Long id;
    private String name;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
