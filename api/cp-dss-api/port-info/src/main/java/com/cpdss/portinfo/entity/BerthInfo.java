/* Licensed under Apache-2.0 */
package com.cpdss.portinfo.entity;

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

/** Entity class for berthinfo table */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "berth_info")
public class BerthInfo extends EntityDoc {

  @Column(name = "berth_name")
  private String berthName;

  @Column(name = "maximum_draft")
  private BigDecimal maximumDraft;

  @Column(name = "air_draft")
  private BigDecimal airDraft;

  @ManyToOne
  @JoinColumn(name = "port_xid")
  private PortInfo portInfo;
}