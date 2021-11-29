/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author jerin.g */
@Entity
@Table(name = "voyage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voyage extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "vessel_xid")
  private Long vesselXId;

  @Column(name = "voyage_no")
  private String voyageNo;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "company_xid")
  private Long companyXId;

  @Column(name = "captain_xid")
  private Long captainXId;

  @Column(name = "chiefofficer_xid")
  private Long chiefOfficerXId;

  @Column(name = "voyage_start_date")
  private LocalDateTime voyageStartDate;

  @Column(name = "voyage_end_date")
  private LocalDateTime voyageEndDate;

  @Column(name = "actual_start_date")
  private LocalDateTime actualStartDate;

  @Column(name = "actual_end_date")
  private LocalDateTime actualEndDate;

  @ManyToOne
  @JoinColumn(name = "voyage_status")
  private VoyageStatus voyageStatus;

  @OneToMany(mappedBy = "voyage")
  private Set<LoadableStudy> loadableStudies;

  @Column(name = "start_timezone_xid")
  private Long startTimezoneId;

  @Column(name = "end_timezone_xid")
  private Long endTimezoneId;

  @Transient private String communicationRelatedEntityId;
}
