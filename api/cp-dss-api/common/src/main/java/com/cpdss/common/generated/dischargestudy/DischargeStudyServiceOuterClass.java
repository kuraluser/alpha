/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.dischargestudy;

public final class DischargeStudyServiceOuterClass {
  private DischargeStudyServiceOuterClass() {}

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
      "\n,dischargestudy/discharge_study_service"
          + ".proto\032\014common.proto\032&loading_plan/loadi"
          + "ng_plan_models.proto2\344\001\n\025DischargeStudyS"
          + "ervice\022g\n\031getDischargeStudyByVoyage\022$.Lo"
          + "adingInformationSynopticalRequest\032\".Load"
          + "ingInformationSynopticalReply\"\000\022b\n\036getDi"
          + "schargeStudyCargoByVoyage\022\032.LoadingInfor"
          + "mationRequest\032\".LoadingInformationSynopt"
          + "icalReply\"\000B-\n)com.cpdss.common.generate"
          + "d.dischargestudyP\000b\006proto3"
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
