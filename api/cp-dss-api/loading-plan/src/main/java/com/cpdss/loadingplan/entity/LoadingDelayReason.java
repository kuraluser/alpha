/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loading_delay_reason")
public class LoadingDelayReason extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_delay_xid")
  private LoadingDelay loadingDelay;

  @OneToOne
  @JoinColumn(name = "reason_xid", referencedColumnName = "id")
  private ReasonForDelay reasonForDelay;

  @Column(name = "is_active")
  private Boolean isActive;
}
