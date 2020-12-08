/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "vessel_flag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselFlag extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "flag_image_path")
  private String flagImagePath;

  @Column(name = "name")
  private String name;
}
