/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The persistent class for the discharging_information_status database table. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_information_status")
public class DischargingInformationStatus extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "is_active")
  private Boolean isActive;

  private String name;

  // bi-directional many-to-one association to DischargingInformation
  @OneToMany(mappedBy = "dischargingInformationStatus")
  private List<DischargeInformation> dischargingInformations;
}
