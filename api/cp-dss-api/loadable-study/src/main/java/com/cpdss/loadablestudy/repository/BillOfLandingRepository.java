/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.BillOfLanding;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BillOfLandingRepository extends CommonCrudRepository<BillOfLanding, Long> {

  @Transactional
  @Modifying
  @Query(
      "Update BillOfLanding set bl_ref_number = ?1, bbl_at_60f = ?2, quantity_lt = ?3, quantity_mt = ?4, kl_at_15c = ?5, api = ?6,"
          + "temperature = ?7 where cargo_nomination_xid = ?8 and port_xid = ?9")
  public void updateBillOfLandingRepository(
      @Param("bl_ref_number") Long bl_ref_number,
      @Param("bbl_at_60f") Long bbl_at_60f,
      @Param("quantity_lt") Long quantity_lt,
      @Param("quantity_mt") Long quantity_mt,
      @Param("kl_at_15c") Long kl_at_15c,
      @Param("api") Long api,
      @Param("temperature") Long temperature,
      @Param("cargo_nomination_xid") Long cargo_nomination_xid,
      @Param("port_xid") Long port_xid);

  @Query("FROM BillOfLanding WHERE isActive = ?2")
  public List<BillOfLanding> findByBillOfLandingAndIsActive(
      List<BillOfLanding> billOfLandings, Boolean isActive);
}
