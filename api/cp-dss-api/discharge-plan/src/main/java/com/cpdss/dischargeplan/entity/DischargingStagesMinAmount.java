/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_stages_min_amount database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_stages_min_amount")
public class DischargingStagesMinAmount extends EntityDoc {

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "min_amount")
  private Integer minAmount;

  // bi-directional many-to-one association to DischargingInformation
  @OneToMany(mappedBy = "dischargingStagesMinAmount")
  private List<DischargeInformation> dischargeInformation;
}
