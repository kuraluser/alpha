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
          + "an_models.proto2\254\003\n\031LoadingInformationSe"
          + "rvice\022J\n\025GetLoadingInformation\022\032.Loading"
          + "InformationRequest\032\023.LoadingInformation\""
          + "\000\022I\n\026SaveLoadingInformation\022\023.LoadingInf"
          + "ormation\032\030.LoadingInfoSaveResponse\"\000\022j\n\034"
          + "GetLoadigInformationByVoyage\022$.LoadingIn"
          + "formationSynopticalRequest\032\".LoadingInfo"
          + "rmationSynopticalReply\"\000\022I\n\014UpdateUllage"
          + "\022\033.UpdateUllageLoadingRequest\032\032.UpdateUl"
          + "lageLoadingReplay\"\000\022A\n\023GenerateLoadingPl"
          + "an\022\027.LoadingInfoAlgoRequest\032\017.ResponseSt"
          + "atus\"\0002o\n\031LoadingInstructionService\022R\n\026G"
          + "etLoadingInstructions\022\032.LoadingInstructi"
          + "onRequest\032\032.LoadingInstructionDetails\"\0002"
          + "d\n\022LoadingPlanService\022N\n\032LoadingPlanSync"
          + "hronization\022\027.LoadingPlanSyncDetails\032\025.L"
          + "oadingPlanSyncReply\"\000B+\n\'com.cpdss.commo"
          + "n.generated.loading_planP\000b\006proto3"
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
