/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargo_loading_rate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoLoadingRate extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "loading_sequences_xid", referencedColumnName = "id")
  private LoadingSequence loadingSequence;

  @Column(name = "tank_xid")
  private Long tankXId;

  @Column(name = "loading_rate")
  private BigDecimal loadingRate;

  @Column(name = "is_active")
  private Boolean isActive;

  @Transient private String communicationRelatedEntityId;
}
