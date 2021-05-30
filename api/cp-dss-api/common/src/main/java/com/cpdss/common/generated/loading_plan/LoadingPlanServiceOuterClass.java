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
          + "o\032&loading_plan/loading_plan_models.prot"
          + "o2g\n\031LoadingInformationService\022J\n\025GetLoa"
          + "dingInformation\022\032.LoadingInformationRequ"
          + "est\032\023.LoadingInformation\"\0002\033\n\031LoadingIns"
          + "tructionService2d\n\022LoadingPlanService\022N\n"
          + "\032LoadingPlanSynchronization\022\027.LoadingPla"
          + "nSyncDetails\032\025.LoadingPlanSyncReply\"\000B+\n"
          + "\'com.cpdss.common.generated.loading_plan"
          + "P\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
            });
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
