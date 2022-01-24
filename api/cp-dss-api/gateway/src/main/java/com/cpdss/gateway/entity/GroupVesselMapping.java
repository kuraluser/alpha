/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_vessel_mapping")
public class GroupVesselMapping extends EntityDoc {
  @Column(name = "vessel_xid")
  private Long vesselXId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "group_xid")
  private Long groupXId;
}
