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
          + "an_models.proto2\344\002\n\031LoadingInformationSe"
          + "rvice\022J\n\025GetLoadingInformation\022\032.Loading"
          + "InformationRequest\032\023.LoadingInformation\""
          + "\000\022@\n\026SaveLoadingInformation\022\023.LoadingInf"
          + "ormation\032\017.ResponseStatus\"\000\022n\n GetLoadig"
          + "InformationBySynoptical\022$.LoadingInforma"
          + "tionSynopticalRequest\032\".LoadingInformati"
          + "onSynopticalReply\"\000\022I\n\014UpdateUllage\022\033.Up"
          + "dateUllageLoadingRequest\032\032.UpdateUllageL"
          + "oadingReplay\"\0002\033\n\031LoadingInstructionServ"
          + "ice2d\n\022LoadingPlanService\022N\n\032LoadingPlan"
          + "Synchronization\022\027.LoadingPlanSyncDetails"
          + "\032\025.LoadingPlanSyncReply\"\000B+\n\'com.cpdss.c"
          + "ommon.generated.loading_planP\000b\006proto3"
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
