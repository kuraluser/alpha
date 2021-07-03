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
          + ".proto\032\014common.proto\032\024loadable_study.pro"
          + "to\032&loading_plan/loading_plan_models.pro"
          + "to\032*dischargestudy/discharge_study_model"
          + ".proto2\200\001\n\025DischargeStudyService\022g\n\031getD"
          + "ischargeStudyByVoyage\022$.LoadingInformati"
          + "onSynopticalRequest\032\".LoadingInformation"
          + "SynopticalReply\"\000B-\n)com.cpdss.common.ge"
          + "nerated.dischargestudyP\000b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.LoadableStudy.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
              com.cpdss.common.generated.dischargestudy.DischargeStudyModel.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.LoadableStudy.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
    com.cpdss.common.generated.dischargestudy.DischargeStudyModel.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
