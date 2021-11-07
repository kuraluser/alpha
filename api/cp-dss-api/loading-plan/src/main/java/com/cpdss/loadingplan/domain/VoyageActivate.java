/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.domain;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoyageActivate {
  private Long id;
  private Long voyageStatus;
}
