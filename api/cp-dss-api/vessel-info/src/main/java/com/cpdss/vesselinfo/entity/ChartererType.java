/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "charterer_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChartererType extends EntityDoc {

  @Column(name = "type_name")
  private String charterTypeName;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "charterType")
  private List<Charterer> charterDetails;
}
