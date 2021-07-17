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
          + "atus\"\0002\314\002\n\031LoadingInstructionService\022R\n\026"
          + "GetLoadingInstructions\022\032.LoadingInstruct"
          + "ionRequest\032\032.LoadingInstructionDetails\"\000"
          + "\022D\n\025AddLoadingInstruction\022\030.LoadingInstr"
          + "uctionsSave\032\017.ResponseStatus\"\000\022J\n\031Update"
          + "LoadingInstructions\022\032.LoadingInstruction"
          + "sUpdate\032\017.ResponseStatus\"\000\022I\n\031DeleteLoad"
          + "ingInstructions\022\031.LoadingInstructionStat"
          + "us\032\017.ResponseStatus\"\0002d\n\022LoadingPlanServ"
          + "ice\022N\n\032LoadingPlanSynchronization\022\027.Load"
          + "ingPlanSyncDetails\032\025.LoadingPlanSyncRepl"
          + "y\"\000B+\n\'com.cpdss.common.generated.loadin"
          + "g_planP\000b\006proto3"
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
