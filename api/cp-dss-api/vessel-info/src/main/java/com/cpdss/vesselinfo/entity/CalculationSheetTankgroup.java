/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

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
@Table(name = "calculation_sheet_tankgroup")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalculationSheetTankgroup extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "tank_group")
  private Integer tankGroup;

  @Column(name = "lcg")
  private BigDecimal lcg;

  @Column(name = "frame_number")
  private BigDecimal frameNumber;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;
}
