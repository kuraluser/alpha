/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.loading_plan;

public final class LoadingPlanServiceOuterClass {
  private LoadingPlanServiceOuterClass() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\'loading_plan/loading_plan_service.prot"
          + "o\032\014common.proto\032&loading_plan/loading_pl"
          + "an_models.proto2\240\006\n\031LoadingInformationSe"
          + "rvice\022J\n\025GetLoadingInformation\022\032.Loading"
          + "InformationRequest\032\023.LoadingInformation\""
          + "\000\022I\n\026SaveLoadingInformation\022\023.LoadingInf"
          + "ormation\032\030.LoadingInfoSaveResponse\"\000\022G\n\024"
          + "SaveLoadingInfoRates\022\023.LoadingInformatio"
          + "n\032\030.LoadingInfoSaveResponse\"\000\022H\n\025SaveLoa"
          + "dingInfoBerths\022\023.LoadingInformation\032\030.Lo"
          + "adingInfoSaveResponse\"\000\022K\n\030SaveLoadingIn"
          + "foMachinery\022\023.LoadingInformation\032\030.Loadi"
          + "ngInfoSaveResponse\"\000\022H\n\025SaveLoadingInfoD"
          + "elays\022\023.LoadingInformation\032\030.LoadingInfo"
          + "SaveResponse\"\000\022H\n\025SaveLoadingInfoStages\022"
          + "\023.LoadingInformation\032\030.LoadingInfoSaveRe"
          + "sponse\"\000\022j\n\034GetLoadigInformationByVoyage"
          + "\022$.LoadingInformationSynopticalRequest\032\""
          + ".LoadingInformationSynopticalReply\"\000\022I\n\014"
          + "UpdateUllage\022\033.UpdateUllageLoadingReques"
          + "t\032\032.UpdateUllageLoadingReplay\"\000\022A\n\023Gener"
          + "ateLoadingPlan\022\027.LoadingInfoAlgoRequest\032"
          + "\017.ResponseStatus\"\0002\033\n\031LoadingInstruction"
          + "Service2d\n\022LoadingPlanService\022N\n\032Loading"
          + "PlanSynchronization\022\027.LoadingPlanSyncDet"
          + "ails\032\025.LoadingPlanSyncReply\"\000B+\n\'com.cpd"
          + "ss.common.generated.loading_planP\000b\006prot"
          + "o3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
