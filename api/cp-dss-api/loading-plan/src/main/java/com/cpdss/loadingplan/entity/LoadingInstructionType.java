/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loading_instructions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingInstructionType extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "type_name")
  private String typeName;

  @Column(name = "is_active")
  private Boolean isActive;
}
