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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commingle_colour")
public class CommingleColour extends EntityDoc {

  @Column private String abbreviation;

  @Column(name = "commingle_colour")
  private String commingleColour;

  @Column(name = "is_active")
  private Boolean isActive;
}
