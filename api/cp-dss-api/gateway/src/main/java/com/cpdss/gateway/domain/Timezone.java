/* Licensed under Apache-2.0 */
package com.cpdss.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Model class for table 'Timezone' in 'cpdss-port' */
@Data
@AllArgsConstructor
public class Timezone {
  Long id;
  String timezone;
  String offsetValue;
}
