/* Licensed under Apache-2.0 */
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
@Table(name = "cargoportmapping")
public class CargoPortMapping extends EntityDoc {

  @Column(name = "cargoxid")
  private Long cargoxid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "portxid")
  private PortInfo portInfo;
}
