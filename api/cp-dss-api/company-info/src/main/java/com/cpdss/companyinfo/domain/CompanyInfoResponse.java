/* Licensed under Apache-2.0 */
package com.cpdss.companyinfo.domain;

import java.util.List;
import lombok.Data;

/**
 * DTO for company related public data
 *
 * @author suhail.k
 */
@Data
public class CompanyInfoResponse {

  private String realm;

  private List<String> providers;
}
