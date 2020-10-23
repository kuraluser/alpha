/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Data;

/**
 * Vessel dto class
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class Vessel {

  private Long id;

  private String name;

  private String imoNumber;

  private String flagPath;

  private Long chiefOfficerId;

  private String chiefOfficerName;

  private Long captainId;

  private String captainName;

  private List<LoadLine> loadlines;
}
