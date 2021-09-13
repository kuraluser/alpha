/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.DischargingInstruction;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargingInstructionRepository
    extends CommonCrudRepository<DischargingInstruction, Long> {

  @Query(
      value =
          "select * from discharging_instructions LI "
              + " join discharging_information LIF on LI.discharging_xid = LIF.id"
              + " where LIF.vessel_xid=:vesselId and LI.discharging_xid= :dischargingInfoId and LIF.port_rotation_xid =:portRotationId "
              + "and LI.is_active = true and LIF.is_active =true order by LI.created_date",
      nativeQuery = true)
  public List<DischargingInstruction> getAllDischargingInstructions(
      @Param("vesselId") long vesselId,
      @Param("dischargingInfoId") long dischargingInfoId,
      @Param("portRotationId") long portRotationId);

  @Query(
      value =
          "SELECT CASE WHEN count(*)>0 then true else false END FROM DischargingInstruction "
              + " where discharging_xid = ?1 and is_active = true")
  public Boolean findAny(Long dischargingInfoId);

  @Modifying
  @Query("update DischargingInstruction set isActive = false where id=?1")
  public void deleteInstruction(long instructionId);

  @Modifying
  @Query("update DischargingInstruction set isChecked = ?2 where id=?1")
  public void updateInstructionStatus(long instructionId, boolean isChecked);

  @Modifying
  @Query("update DischargingInstruction set dischargingInstruction = ?2 where id=?1")
  public void editInstruction(long instructionId, String instruction);
}
