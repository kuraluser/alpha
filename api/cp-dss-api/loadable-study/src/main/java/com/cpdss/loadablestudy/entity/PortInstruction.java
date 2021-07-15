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

/** @author arun.j */
@Entity
@Table(name = "port_instructions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortInstruction extends EntityDoc {

  private static final long serialVersionUID = 6722941798154545445L;
  private Long id;

  @Column(name = "port_instructions")
  private String portInstruction;

  @Column(name = "is_active")
  private Boolean isActive;
}
