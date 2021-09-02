/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_stages_duration database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_stages_duration")
public class DischargingStagesDuration extends EntityDoc {

  private Integer duration;

  @Column(name = "is_active")
  private Boolean isActive;

  // bi-directional many-to-one association to DischargingInformation
  @OneToMany(mappedBy = "dischargingStagesDuration")
  private List<DischargeInformation> dischargingInformation;
}
