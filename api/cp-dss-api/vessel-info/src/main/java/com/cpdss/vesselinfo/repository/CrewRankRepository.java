/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CrewRank;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** CrewRank Repository for crew rank related query Author: Athul c.p */
public interface CrewRankRepository extends CommonCrudRepository<CrewRank, Long> {

  final String FIND_ID_AND_NAME_OF_ACTIVE_CREW_RANK =
      "select cr.id, cr.rank_name,cr.rank_short_name "
          + "from crew_rank cr where cr.is_active = true";

  @Query(value = FIND_ID_AND_NAME_OF_ACTIVE_CREW_RANK, nativeQuery = true)
  List<Object[]> findActiveCrewRank();
}
