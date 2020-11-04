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
@Table(name = "berthinfo")
public class BerthInfo extends EntityDoc {

  @Column(name = "berthname")
  private String berthName;

  @Column(name = "maximumdraft")
  private BigDecimal maximumDraft;

  @Column(name = "airdraft")
  private BigDecimal airDraft;

  @ManyToOne
  @JoinColumn(name = "portxid")
  private PortInfo portInfo;
}
