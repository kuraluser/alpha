/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.communication;

import com.cpdss.common.communication.repository.StagingRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadableStudyStagingRepository extends StagingRepository {

  @Query(
      value = "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM voyage u where id=?1",
      nativeQuery = true)
  String getVoyageWithId(Long id);

  @Query(
      value = "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_study u where id=?1",
      nativeQuery = true)
  String getLoadableStudyWithId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cargo_nomination u where loadable_study_xid=?1",
      nativeQuery = true)
  String getCargoNominationWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_study_port_rotation u where loadable_study_xid=?1",
      nativeQuery = true)
  String getLoadableStudyPortRotationWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM on_hand_quantity u where loadable_study_xid=?1",
      nativeQuery = true)
  String getOnHandQuantityWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM on_board_quantity u where loadable_study_xid=?1",
      nativeQuery = true)
  String getOnBoardQuantityWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_quantity u where loadable_study_xid=?1",
      nativeQuery = true)
  String getLoadableQuantityWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM comingle_cargo u where loadable_study_xid=?1",
      nativeQuery = true)
  String getCommingleCargoWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM synoptical_table u where loadable_study_xid=?1",
      nativeQuery = true)
  String getSynopticalTableWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_study_algo_status u where loadabale_studyxid=?1",
      nativeQuery = true)
  String getLoadableStudyAlgoStatusWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM json_data u where reference_xid=?1",
      nativeQuery = true)
  String getJsonDataWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_pattern u where loadablestudy_xid=?1",
      nativeQuery = true)
  String getLoadablePatternWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM algo_error_heading u where loadable_study_xid=?1",
      nativeQuery = true)
  String getAlgoErrorHeadingWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM algo_errors u where error_heading_xid IN ?1",
      nativeQuery = true)
  String getAlgoErrorsWithAlgoErrorHeadingIds(List<Long> algoErrorHeadingsIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_constraints u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanConstraintsWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_quantity u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanQuantityWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_commingle_details u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanCommingleDetailsWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_pattern_cargo_topping_off_sequence u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePatternCargoToppingOffSequenceWithLoadablePatternId(
      List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_stowage_details u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanStowageDetailsWithLoadablePatternIds(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_ballast_details u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanBallastDetailsWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_commingle_details_portwise u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanComminglePortwiseDetailsWithLoadablePatternId(
      List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM stability_parameters u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getStabilityParametersWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_pattern_cargo_details u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePatternCargoDetailsWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_stowage_ballast_details u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanStowageBallastDetailsWithLoadablePatternId(List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan u where loadable_study_xid=?1",
      nativeQuery = true)
  String getLoadablePlanWithLoadableStudyId(Long id);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_plan_stowage_ballast_details u where loadable_plan_xid IN ?1",
      nativeQuery = true)
  String getLoadablePlanStowageBallastDetailsWithLoadablePlanId(List<Long> loadablePlanIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadicator_data_for_synoptical_table u where synoptical_table_xid IN ?1",
      nativeQuery = true)
  String getSynopticalTblLoadicatorDataWithSynopticalTblId(List<Long> synopticalTableIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cargo_nomination_operation_details u where cargo_nomination_xid IN ?1",
      nativeQuery = true)
  String getCargoNominationOperationDetailsWithCargoNominationId(List<Long> cargoNominationIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM communication_status_update u where reference_id=?1",
      nativeQuery = true)
  String getCommunicationStatusUpdateWithLoadableStudyId(long loadableStudyId);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM cow_history u where voyage_xid=?1",
      nativeQuery = true)
  String getCowHistoryWithVoyageId(Long voyageId);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM discharge_quantity_cargo_details u where loadable_pattern_xid IN ?1",
      nativeQuery = true)
  String getDischargePatternQuantityCargoPortwiseDetailsWithLoadablePatternId(
      List<Long> loadablePatternIds);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_study_rules u where loadable_study_xid=?1",
      nativeQuery = true)
  String getLoadableStudyRules(long loadableStudyId);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM loadable_study_rule_input u where loadable_study_rule_xid IN (SELECT  id FROM loadable_study_rules where loadable_study_xid=?1)",
      nativeQuery = true)
  String getLoadableStudyRuleInput(long loadableStudyId);
}
