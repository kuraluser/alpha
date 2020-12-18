/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Entity class for Synoptical Table */
@Entity
@Table(name = "synoptical_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SynopticalTable extends EntityDoc {

  /** */
  private static final long serialVersionUID = 1L;

  @Column(name = "loadable_study_xid")
  private Long loadableStudyXId;

  @Column(name = "operation_type")
  private String operationType;

  @Column(name = "distance")
  private Long distance;

  @Column(name = "speed")
  private Long speed;

  @Column(name = "port_xid")
  private Long portXid;

  @Column(name = "is_active")
  private Boolean isActive;

  @ManyToOne
  @JoinColumn(name = "port_rotation_xid")
  private LoadableStudyPortRotation loadableStudyPortRotation;
}
