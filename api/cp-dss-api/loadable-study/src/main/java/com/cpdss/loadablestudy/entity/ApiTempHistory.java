/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Api and Temp history entity */
@Entity
@Table(name = "api_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiTempHistory extends EntityDoc {

  @Column(name = "vessel_xid")
  private Long vesselId;

  @Column(name = "cargo_xid")
  private Long cargoId;

  @Column(name = "load_port_xid")
  private Long loadingPortId;

  @Column(name = "loaded_date", columnDefinition = "TIMESTAMP")
  private LocalDateTime loadedDate;

  @Column(name = "loaded_year")
  private Integer year;

  @Column(name = "loaded_month")
  private Integer month;

  @Column(name = "loaded_day")
  private Integer date;

  @Column(name = "api")
  private BigDecimal api;

  @Column(name = "temperature")
  private BigDecimal temp;

  @Column(name = "is_active")
  private Boolean isActive;
}
