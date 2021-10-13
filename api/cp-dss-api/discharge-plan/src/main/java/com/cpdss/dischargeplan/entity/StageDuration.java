/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stages_duration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StageDuration extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "is_active")
  private Boolean isActive;
}
