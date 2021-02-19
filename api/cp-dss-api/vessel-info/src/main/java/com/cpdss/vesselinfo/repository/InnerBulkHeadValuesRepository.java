/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.InnerBulkHeadValues;
import java.util.List;

/** @Author jerin.g */
public interface InnerBulkHeadValuesRepository
    extends CommonCrudRepository<InnerBulkHeadValues, Long> {
  public List<InnerBulkHeadValues> findByVesselId(Long vesselId);
}
