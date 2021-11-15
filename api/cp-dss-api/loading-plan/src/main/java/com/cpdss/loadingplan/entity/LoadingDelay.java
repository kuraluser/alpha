/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "loading_delay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingDelay extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @OneToMany(mappedBy = "loadingDelay", cascade = CascadeType.ALL)
  private List<LoadingDelayReason> loadingDelayReasons;

  @Column(name = "duration")
  private BigDecimal duration;

  @Column(name = "cargo_xid")
  private Long cargoXId;

  @Column(name = "quantity")
  private BigDecimal quantity;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "cargo_nomination_xid")
  private Long cargoNominationId;

  @Transient private Long communicationRelatedEntityId;
}
