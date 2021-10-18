/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "loading_machinary_in_use")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoadingMachineryInUse extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loading_xid", referencedColumnName = "id")
  private LoadingInformation loadingInformation;

  @Column(name = "machine_xid")
  private Long machineXId;

  @Column(name = "is_using")
  private Boolean isUsing;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "capacity")
  private BigDecimal capacity;

  @Column(name = "machine_type_xid")
  private Integer machineTypeXid;
}
