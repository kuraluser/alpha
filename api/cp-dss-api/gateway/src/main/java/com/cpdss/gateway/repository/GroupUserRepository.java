/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.gateway.entity.GroupUserMapping;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface GroupUserRepository extends CommonCrudRepository<GroupUserMapping, Long> {

  @Query(
      value =
          "select distinct gvm.vessel_xid from group_user_mapping gum join group_vessel_mapping gvm  on "
              + "gum.group_xid = gvm.group_xid where gum.user_xid =?1 and gum.is_active = true "
              + " and gvm.is_active =true",
      nativeQuery = true)
  List<Long> getAllVesselXId(Long id);
}
