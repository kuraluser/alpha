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
          + "an_models.proto2\251\001\n\031LoadingInformationSe"
          + "rvice\022J\n\025GetLoadingInformation\022\032.Loading"
          + "InformationRequest\032\023.LoadingInformation\""
          + "\000\022@\n\026SaveLoadingInformation\022\023.LoadingInf"
          + "ormation\032\017.ResponseStatus\"\0002\033\n\031LoadingIn"
          + "structionService2d\n\022LoadingPlanService\022N"
          + "\n\032LoadingPlanSynchronization\022\027.LoadingPl"
          + "anSyncDetails\032\025.LoadingPlanSyncReply\"\000B+"
          + "\n\'com.cpdss.common.generated.loading_pla"
          + "nP\000b\006proto3"
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
