/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_berth_details database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_berth_details")
public class DischargingBerthDetail extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "air_draft_limitation")
  private BigDecimal airDraftLimitation;

  @Column(name = "berth_xid")
  private Long berthXid;

  private BigDecimal depth;

  @Column(name = "hose_connections")
  private String hoseConnections;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_air_purge")
  private Boolean isAirPurge;

  @Column(name = "is_cargo_circulation")
  private Boolean isCargoCirculation;

  @Column(name = "item_to_be_agreed")
  private String itemToBeAgreed;

  @Column(name = "line_content_displacement")
  private BigDecimal lineContentDisplacement;

  @Column(name = "max_manifold_height")
  private BigDecimal maxManifoldHeight;

  @Column(name = "sea_draft_limitation")
  private BigDecimal seaDraftLimitation;

  @Column(name = "special_regulation_restriction")
  private String specialRegulationRestriction;

  // bi-directional many-to-one association to DischargingInformation
  @ManyToOne
  @JoinColumn(name = "discharging_xid")
  private DischargeInformation dischargingInformation;
}
