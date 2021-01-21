/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "on_board_quantity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnBoardQuantity extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "cargo_xid")
  private Long cargoId;

  @Column(name = "sounding")
  private BigDecimal sounding;

  @Column(name = "volume")
  private BigDecimal volume;

  @Column(name = "loadable_study_status")
  private Integer loadableStudyStatus;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "loadable_study_xid", referencedColumnName = "id")
  @ManyToOne
  private LoadableStudy loadableStudy;

  @Column(name = "planned_arrival_weight")
  private BigDecimal plannedArrivalWeight;

  @Column(name = "actual_arrival_weight")
  private BigDecimal actualArrivalWeight;

  @Column(name = "planned_departure_weight")
  private BigDecimal plannedDepartureWeight;

  @Column(name = "actual_departure_weight")
  private BigDecimal actualDepartureWeight;

  @Column(name = "color_code")
  private String colorCode;

  @Column(name = "abbreviation")
  private String abbreviation;
}
