/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.communication;

import com.cpdss.common.communication.repository.StagingRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadingPlanStagingRepository extends StagingRepository {

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_information u where id=?1",
      nativeQuery = true)
  String getLoadingInformationJson(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_sequence u where loading_information_xid=?1",
      nativeQuery = true)
  String getLoadingSequenceByLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_plan_portwise_details u where loading_sequences_xid IN ?1",
      nativeQuery = true)
  String getLoadingPlanPortWiseDetailsWithLoadingSeqIds(List<Long> loadingSequenceIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loading_plan_stability_parameters u where loading_information_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanStabilityParamWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loading_plan_rob_details u where loading_information_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanRobDetailsWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_plan_ballast_details u where loading_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getLoadingPlanBallastDetailsWithPortIds(List<Long> loadingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_plan_rob_details u where loading_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getLoadingPlanRobDetailsWithPortIds(List<Long> loadingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loading_plan_stowage_ballast_details u where loading_information_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanBallastDetailsWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loading_plan_stowage_details u where loading_information_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanStowageDetailsWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loading_plan_stowage_details_temp u where loading_information_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanStowageTempDetailsWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_plan_stowage_details u where loading_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getLoadingPlanStowageDetailsWithPortIds(List<Long> loadingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_sequence_stability_parameters u where loading_information_xid=?1",
      nativeQuery = true)
  String getLoadingSequenceStabilityParametersWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_plan_stability_parameters u where loading_plan_portwise_details_xid IN ?1",
      nativeQuery = true)
  String getLoadingPlanStabilityParametersWithPortIds(List<Long> loadingPlanPortWiseDetailsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cargo_topping_off_sequence u where loading_xid=?1",
      nativeQuery = true)
  String getCargoToppingOffSequenceWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_berth_details u where loading_xid=?1",
      nativeQuery = true)
  String getLoadingBerthDetailWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_delay u where loading_xid=?1",
      nativeQuery = true)
  String getLoadingDelayWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loading_machinary_in_use u where loading_xid=?1",
      nativeQuery = true)
  String getLoadingMachineryInUseWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loading_plan_stowage_ballast_details_temp u where loading_information_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanBallastTempDetailsWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loadable_plan_commingle_details_temp u where loading_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanCommingleTempDetailsWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM bill_of_ladding u where loading_xid=?1",
      nativeQuery = true)
  String getBillOfLandingWithLoadingId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM port_loadable_plan_commingle_details u where loading_xid=?1",
      nativeQuery = true)
  String getPortLoadingPlanCommingleDetailsWithLoadingId(Long id);
}
