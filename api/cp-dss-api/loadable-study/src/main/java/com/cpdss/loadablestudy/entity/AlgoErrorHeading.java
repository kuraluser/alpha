/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.entity;

import com.cpdss.common.utils.EntityDoc;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "algo_error_heading")
public class AlgoErrorHeading extends EntityDoc {

  @Column(name = "error_heading")
  private String errorHeading;

  // bi-directional many-to-one association to AlgoError
  @OneToMany(mappedBy = "algoErrorHeading")
  private List<AlgoErrors> algoErrors;
}
