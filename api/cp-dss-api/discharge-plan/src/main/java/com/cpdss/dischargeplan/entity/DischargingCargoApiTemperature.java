/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

/**
 * Class for DischargingCargoApiTemperature entity
 *
 * @author sreemanikandan.k
 * @since 10/01/2022
 */
@Entity
@Table(name = "discharging_cargo_api_temperature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DischargingCargoApiTemperature extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discharging_information_xid", referencedColumnName = "id")
  private DischargeInformation dischargeInformation;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "max_discharging_rate")
  private BigDecimal maxDischargingRate;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;
}
