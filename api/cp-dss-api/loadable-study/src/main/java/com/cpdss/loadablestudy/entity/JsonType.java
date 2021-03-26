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

/** JSON type entity */
@Entity
@Table(name = "json_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsonType extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "type_name")
  private String typeName;

  @Column(name = "is_active")
  private Boolean isActive;
}
