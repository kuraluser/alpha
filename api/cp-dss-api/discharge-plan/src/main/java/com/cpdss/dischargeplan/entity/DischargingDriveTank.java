/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "discharging_drive_tanks")
public class DischargingDriveTank extends EntityDoc {

  @Column(name = "tank_xid")
  private Long tankId;

  @Column(name = "short_name")
  private String tankShortName;

  @Column(name = "start_time")
  private Integer startTime;

  @Column(name = "end_time")
  private Integer endTime;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "discharging_xid", referencedColumnName = "id")
  private DischargeInformation dischargingInformation;
}
