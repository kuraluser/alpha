/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.loadingplan.entity.PyUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PyUserRepository extends CrudRepository<PyUser, String> {

  @Query(
      value = "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM pyuser u where id=?1",
      nativeQuery = true)
  String getPyUserWithId(String id);
}
