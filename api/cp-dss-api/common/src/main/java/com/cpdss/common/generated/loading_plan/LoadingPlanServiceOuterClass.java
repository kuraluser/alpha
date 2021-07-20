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
          + "an_models.proto\032\024loadable_study.proto2\345\006"
          + "\n\031LoadingInformationService\022J\n\025GetLoadin"
          + "gInformation\022\032.LoadingInformationRequest"
          + "\032\023.LoadingInformation\"\000\022I\n\026SaveLoadingIn"
          + "formation\022\023.LoadingInformation\032\030.Loading"
          + "InfoSaveResponse\"\000\022G\n\024SaveLoadingInfoRat"
          + "es\022\023.LoadingInformation\032\030.LoadingInfoSav"
          + "eResponse\"\000\022H\n\025SaveLoadingInfoBerths\022\023.L"
          + "oadingInformation\032\030.LoadingInfoSaveRespo"
          + "nse\"\000\022K\n\030SaveLoadingInfoMachinery\022\023.Load"
          + "ingInformation\032\030.LoadingInfoSaveResponse"
          + "\"\000\022H\n\025SaveLoadingInfoDelays\022\023.LoadingInf"
          + "ormation\032\030.LoadingInfoSaveResponse\"\000\022H\n\025"
          + "SaveLoadingInfoStages\022\023.LoadingInformati"
          + "on\032\030.LoadingInfoSaveResponse\"\000\022j\n\034GetLoa"
          + "digInformationByVoyage\022$.LoadingInformat"
          + "ionSynopticalRequest\032\".LoadingInformatio"
          + "nSynopticalReply\"\000\022I\n\014UpdateUllage\022\033.Upd"
          + "ateUllageLoadingRequest\032\032.UpdateUllageLo"
          + "adingReplay\"\000\022C\n\031SaveAlgoLoadingPlanStat"
          + "us\022\022.AlgoStatusRequest\032\020.AlgoStatusReply"
          + "\"\000\022A\n\023GenerateLoadingPlan\022\027.LoadingInfoA"
          + "lgoRequest\032\017.ResponseStatus\"\0002\033\n\031Loading"
          + "InstructionService2\307\002\n\022LoadingPlanServic"
          + "e\022N\n\032LoadingPlanSynchronization\022\027.Loadin"
          + "gPlanSyncDetails\032\025.LoadingPlanSyncReply\""
          + "\000\022F\n\017SaveLoadingPlan\022\027.LoadingPlanSaveRe"
          + "quest\032\030.LoadingPlanSaveResponse\"\000\022P\n\034Get"
          + "OrSaveRulesForLoadingPlan\022\027.LoadingPlanR"
          + "uleRequest\032\025.LoadingPlanRuleReply\"\000\022G\n\023G"
          + "etLoadingSequences\022\027.LoadingSequenceRequ"
          + "est\032\025.LoadingSequenceReply\"\000B+\n\'com.cpds"
          + "s.common.generated.loading_planP\000b\006proto"
          + "3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
              com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor(),
              com.cpdss.common.generated.LoadableStudy.getDescriptor(),
            });
    com.cpdss.common.generated.Common.getDescriptor();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.getDescriptor();
    com.cpdss.common.generated.LoadableStudy.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
