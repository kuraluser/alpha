/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @Author jerin.g */
@Entity
@Table(name = "bill_of_ladding")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillOfLadding extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discharging_xid", referencedColumnName = "id")
  private DischargeInformation dischargeInformation;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "bbl_at_60f")
  private BigDecimal quantityBbls;

  @Column(name = "quantity_mt")
  private BigDecimal quantityMt;

  @Column(name = "quantity_lt")
  private BigDecimal quantityLT;

  @Column(name = "kl_at_15c")
  private BigDecimal quantityKl;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "bl_ref_number")
  private String blRefNo;

  @Transient private Long communicationRelatedEntityId;
}
