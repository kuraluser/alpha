/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reason_for_delay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReasonForDelay extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "reason")
  private String reason;

  @Column(name = "is_active")
  private Boolean isActive;
}
