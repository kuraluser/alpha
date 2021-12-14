/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for the CargoPortMapping table */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo_port_mapping")
public class CargoPortMapping extends EntityDoc {

  @Column(name = "cargo_xid")
  private Long cargoXId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "port_xid")
  private PortInfo portInfo;

  @Column(name = "is_active")
  private Boolean isActive;
}
