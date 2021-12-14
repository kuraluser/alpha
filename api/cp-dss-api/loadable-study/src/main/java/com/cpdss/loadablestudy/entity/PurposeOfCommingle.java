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

/** Entity class for purpose_of_comingle */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purpose_of_comingle")
public class PurposeOfCommingle extends EntityDoc {

  @Column(name = "purpose")
  private String purpose;

  @Column(name = "is_active")
  private Boolean isActive;
}
