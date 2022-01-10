/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;

/**
 * Class for LoadingCargoApiTemperature entity
 *
 * @author sreemanikandan.k
 * @since 07/01/2022
 */
@Entity
@Table(name = "loading_cargo_api_temperature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingCargoApiTemperature extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loading_information_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "max_loading_rate")
  private BigDecimal maxLoadingRate;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;
}
