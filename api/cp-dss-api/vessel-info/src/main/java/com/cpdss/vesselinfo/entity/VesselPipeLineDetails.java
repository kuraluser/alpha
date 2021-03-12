/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.entity;

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

/** @author jerin.g */
@Entity
@Table(name = "vessel_pipe_line_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VesselPipeLineDetails extends EntityDoc {

  private static final long serialVersionUID = 1L;

  @Column(name = "pipline_diameter")
  private Long piplineDiameter;

  @Column(name = "connected_to")
  private Integer connectedTo;

  @Column(name = "is_active")
  private Boolean isActive;

  @JoinColumn(name = "vessel_xid", referencedColumnName = "id")
  @ManyToOne
  private Vessel vessel;

  @JoinColumn(name = "pipelinetype_xid", referencedColumnName = "id")
  @ManyToOne
  private VesselPipelineType vesselPipelineType;
}
