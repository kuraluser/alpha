/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 * Loadline dto class
 *
 * @author suhail.k
 */
@Data
@JsonInclude(Include.NON_EMPTY)
public class LoadLine {

  private Long id;

  private String name;

  private List<BigDecimal> draftMarks;
}
