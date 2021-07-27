/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The persistent class for the vessel_bottomline database table. */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vessel_bottomline")
public class VesselBottomLine extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "bottom_line_code")
  private String bottomLineCode;

  @Column(name = "bottom_line_name")
  private String bottomLineName;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "vessel_xid")
  private Long vesselXid;
}
