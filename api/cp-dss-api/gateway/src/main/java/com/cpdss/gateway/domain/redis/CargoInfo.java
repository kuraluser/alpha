/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain.redis;

import com.cpdss.common.utils.Doc;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cargo Details To Redis Cache
 *
 * @author johnsoorajxavier
 * @since 15-03-2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoInfo implements Doc {

  private List<CargoInfoDomain> cargos;

  public static class CargoInfoDomain {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
