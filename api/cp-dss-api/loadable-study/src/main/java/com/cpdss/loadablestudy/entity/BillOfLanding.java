/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author ravi.r */
@Entity
@Table(name = "bill_of_ladding")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillOfLanding extends EntityDoc {

  private Long id;

  @Column(name = "loading_xid")
  private Long dischargeStudyId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "cargo_nomination_xid")
  private Long cargoId;

  @Column(name = "bl_ref_number")
  private String blRefNumber;

  @Column(name = "bbl_at_60f")
  private BigDecimal bblAt60f;

  @Column(name = "quantity_lt")
  private BigDecimal quantityLt;

  @Column(name = "quantity_mt")
  private BigDecimal quantityMt;

  @Column(name = "kl_at_15c")
  private BigDecimal klAt15c;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temperature;

  @Column(name = "is_active")
  private BigDecimal isActive;

  @Column(name = "version")
  private Long version;
}
