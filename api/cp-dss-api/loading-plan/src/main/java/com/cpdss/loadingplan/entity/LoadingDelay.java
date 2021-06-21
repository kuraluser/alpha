/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loading_delay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingDelay extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @ManyToOne
  @JoinColumn(name = "reason_xid", referencedColumnName = "id")
  private ReasonForDelay reasonForDelay;

  @Column(name = "duration")
  private BigDecimal duration;

  @Column(name = "cargo_xid")
  private Long cargoXId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "is_active")
  private Boolean isActive;
}
