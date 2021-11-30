/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The persistent class for the loading_port_tide_details database table. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loading_port_tide_details")
public class PortTideDetail extends EntityDoc {
  private static final long serialVersionUID = 1L;

  @Column(name = "cargo_xid")
  private Long cargoXid;

  /*  @Column(name = "created_by")
  private String createdBy;*/

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "loading_xid")
  private Long loadingXid;

  @Column(name = "port_xid")
  private Long portXid;

  private BigDecimal quantity;

  @Temporal(TemporalType.DATE)
  @Column(name = "tide_date")
  private Date tideDate;

  @Column(name = "tide_height")
  private BigDecimal tideHeight;

  @Column(name = "tide_time")
  private LocalTime tideTime;
}
