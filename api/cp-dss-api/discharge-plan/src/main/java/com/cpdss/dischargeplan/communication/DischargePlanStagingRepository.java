/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.communication;

import com.cpdss.common.communication.repository.StagingRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DischargePlanStagingRepository extends StagingRepository {

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_information u where id=?1",
      nativeQuery = true)
  String getDischargeInformationJson(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cow_plan_details u where discharging_xid=?1",
      nativeQuery = true)
  String getCowPlanDetailWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cow_with_different_cargo u where discharging_xid=?1",
      nativeQuery = true)
  String getCowWithDifferentCargoWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cow_tank_details u where discharging_xid=?1",
      nativeQuery = true)
  String getCowTankDetailWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_delay u where discharging_xid=?1",
      nativeQuery = true)
  String getDischargingDelayWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_delay_reason u where discharging_delay_xid IN ?1",
      nativeQuery = true)
  String getDischargingDelayReasonWithDischargingDelayIds(List<Long> dischargingDelaysIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_machinary_in_use u where discharging_xid=?1",
      nativeQuery = true)
  String getDischargingMachineryInUseWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_port_tide_details u where discharging_xid=?1",
      nativeQuery = true)
  String getPortTideDetailWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_berth_details u where discharging_xid=?1",
      nativeQuery = true)
  String getDischargingBerthDetailWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_information_algo_status u where id in (SELECT id FROM discharging_information_algo_status WHERE discharging_information_xid=?1 ORDER BY last_modified_date_time DESC LIMIT 1)",
      nativeQuery = true)
  String getDischargingInformationAlgoStatusWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM algo_error_heading u where discharging_information_xid=?1",
      nativeQuery = true)
  String getAlgoErrorHeadingWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM algo_errors u where error_heading_xid IN ?1",
      nativeQuery = true)
  String getAlgoErrorsWithAlgoErrorHeadingIds(List<Long> algoErrorHeadingsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_sequence u where discharging_information_xid=?1",
      nativeQuery = true)
  String getDischargingSequenceWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM ballast_valves u where discharging_sequence_xid IN ?1",
      nativeQuery = true)
  String getBallastValveWithDischargingSequenceIds(List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cargo_valves u where discharging_sequence_xid IN ?1",
      nativeQuery = true)
  String getCargoValveWithDischargingSequenceIds(List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cargo_discharging_rate u where discharging_sequences_xid IN ?1",
      nativeQuery = true)
  String getCargoDischargingRateWithDischargingSequenceIds(List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM ballast_operation u where discharging_sequences_xid IN ?1",
      nativeQuery = true)
  String getBallastOperationWithDischargingSequenceIds(List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_plan_portwise_details u where discharging_sequences_xid IN ?1",
      nativeQuery = true)
  String getDischargingPlanPortWiseDetailsWithDischargingSequenceIds(
      List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM ballasting_rate u where discharging_sequences_xid IN ?1",
      nativeQuery = true)
  String getBallastingRateWithDischargingSequenceIds(List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM deballasting_rate u where discharging_sequences_xid IN ?1",
      nativeQuery = true)
  String getDeballastingRateWithDischargingSequenceIds(List<Long> dischargingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_plan_stowage_details u where discharging_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getDischargingPlanStowageDetaWithDischargingPlanPortWiseDeta(
      List<Long> dischargingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_plan_ballast_details u where discharging_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getDischargingPlanBallastDetaWithDischargingPlanPortWiseDeta(
      List<Long> dischargingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_plan_rob_details u where discharging_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getDischargingPlanRobDetailsWithDischargingPlanPortWiseDeta(
      List<Long> dischargingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_plan_stability_parameters u where discharging_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getDischargingPlanStabilityParametersWithDischargingPlanPortWiseDeta(
      List<Long> dischargingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_plan_commingle_details u where discharging_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getDischargingPlanCommingleDetailsWithDischargingPlanPortWiseDeta(
      List<Long> dischargingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharging_plan_stowage_ballast_details u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanBallastDetailsWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharging_sequence_stability_parameters u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortDischargingSequenceStabilityParamsWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharging_plan_rob_details u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortPortDischargingPlanRobDetailsWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharging_plan_stability_parameters u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanStabilityParametersWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharging_plan_stowage_details u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanStowageDetailsWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharge_plan_commingle_details u where discharging_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanCommingleDetailsWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharging_plan_stowage_details_temp u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanStowageDetailsTempWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharging_plan_stowage_ballast_details_temp u where discharging_information_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanStowageBallastDetailsTempWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_discharge_plan_commingle_details_temp u where discharging_xid=?1",
      nativeQuery = true)
  String getPortDischargingPlanCommingleDetailsTempWithDischargeId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM bill_of_ladding u where discharging_xid=?1",
      nativeQuery = true)
  String getBillOfLandingWithDischargeInfoId(Long id);
}
