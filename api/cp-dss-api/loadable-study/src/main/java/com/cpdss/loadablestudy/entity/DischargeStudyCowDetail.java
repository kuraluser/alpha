/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discharge_cow_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DischargeStudyCowDetail extends EntityDoc {
  private static final long serialVersionUID = 6722941798154545447L;

  @Column(name = "discharge_study_xid")
  private Long dischargeStudyStudyId;

  @Column(name = "port_xid")
  private Long portId;

  @Column(name = "cow_type")
  private Long cowType;

  @Column(name = "percentage")
  private Long percentage;

  @Column(name = "is_active")
  private Boolean isActive=true;

  @Column(name = "tank_xid")
  private String tankIds;
}
