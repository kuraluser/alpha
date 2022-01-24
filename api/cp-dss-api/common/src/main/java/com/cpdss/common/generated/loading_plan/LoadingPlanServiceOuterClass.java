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
          + "an_models.proto\032\024loadable_study.proto2\267\n"
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
          + "\"\000\022G\n\023GenerateLoadingPlan\022\027.LoadingInfoA"
          + "lgoRequest\032\025.LoadingInfoAlgoReply\"\000\022P\n\030G"
          + "etLoadingInfoAlgoStatus\022\031.LoadingInfoSta"
          + "tusRequest\032\027.LoadingInfoStatusReply\"\000\022@\n"
          + "\030GetLoadingInfoAlgoErrors\022\021.AlgoErrorReq"
          + "uest\032\017.AlgoErrorReply\"\000\022Q\n\025UploadPortTid"
          + "eDetails\022\030.UploadTideDetailRequest\032\034.Upl"
          + "oadTideDetailStatusReply\"\000\022Y\n\027DownloadPo"
          + "rtTideDetails\022\032.DownloadTideDetailReques"
          + "t\032\036.DownloadTideDetailStatusReply\"\0000\001\022F\n"
          + "\023SaveCargoToBeLoaded\022\023.LoadingInformatio"
          + "n\032\030.LoadingInfoSaveResponse\"\000\022@\n\022GetCarg"
          + "oToBeLoaded\022\023.LoadingInformation\032\023.Loadi"
          + "ngInformation\"\0002\216\003\n\031LoadingInstructionSe"
          + "rvice\022R\n\026GetLoadingInstructions\022\032.Loadin"
          + "gInstructionRequest\032\032.LoadingInstruction"
          + "Details\"\000\022D\n\025AddLoadingInstruction\022\030.Loa"
          + "dingInstructionsSave\032\017.ResponseStatus\"\000\022"
          + "J\n\031UpdateLoadingInstructions\022\032.LoadingIn"
          + "structionsUpdate\032\017.ResponseStatus\"\000\022I\n\031D"
          + "eleteLoadingInstructions\022\031.LoadingInstru"
          + "ctionStatus\032\017.ResponseStatus\"\000\022@\n\020EditIn"
          + "structions\022\031.LoadingInstructionStatus\032\017."
          + "ResponseStatus\"\0002\342\014\n\022LoadingPlanService\022"
          + "N\n\032LoadingPlanSynchronization\022\027.LoadingP"
          + "lanSyncRequest\032\025.LoadingPlanSyncReply\"\000\022"
          + "F\n\017SaveLoadingPlan\022\027.LoadingPlanSaveRequ"
          + "est\032\030.LoadingPlanSaveResponse\"\000\022A\n\016GetLo"
          + "adingPlan\022\032.LoadingInformationRequest\032\021."
          + "LoadingPlanReply\"\000\022P\n\034GetOrSaveRulesForL"
          + "oadingPlan\022\027.LoadingPlanRuleRequest\032\025.Lo"
          + "adingPlanRuleReply\"\000\022G\n\023GetLoadingSequen"
          + "ces\022\027.LoadingSequenceRequest\032\025.LoadingSe"
          + "quenceReply\"\000\022U\n\026GetUpdateUllageDetails\022"
          + "\033.UpdateUllageDetailsRequest\032\034.UpdateUll"
          + "ageDetailsResponse\"\000\022V\n\027GetBillOfLadding"
          + "Details\022\025.BillOfLaddingRequest\032\".Loading"
          + "InformationSynopticalReply\"\000\022L\n\035GetCargo"
          + "NominationMaxQuantity\022\023.MaxQuantityReque"
          + "st\032\024.MaxQuantityResponse\"\000\022Y\n\021GetLoadica"
          + "torData\022!.LoadingInfoLoadicatorDataReque"
          + "st\032\037.LoadingInfoLoadicatorDataReply\"\000\022B\n"
          + "\030GetLoadableStudyShoreTwo\022\022.UllageBillRe"
          + "quest\032\020.UllageBillReply\"\000\022_\n\037validateSto"
          + "wageAndBillOfLadding\022).StowageAndBillOfL"
          + "addingValidationRequest\032\017.ResponseStatus"
          + "\"\000\022q\n\036GetLoadingPlanCommingleDetails\022$.L"
          + "oadingInformationSynopticalRequest\032\'.Loa"
          + "dablePlanCommingleCargoDetailsReply\"\000\022N\n"
          + "\032getLoadingPlanCargoHistory\022\027.CargoHisto"
          + "ryOpsRequest\032\025.CargoHistoryResponse\"\000\022Y\n"
          + "\025checkDependentProcess\022!.DependentProces"
          + "sCheckRequestComm\032\033.CommunicationCheckRe"
          + "sponse\"\000\022T\n\021checkCommunicated\022 .Communic"
          + "ationStatusCheckRequest\032\033.CommunicationC"
          + "heckResponse\"\000\022_\n\031getPyUserForCommunicat"
          + "ion\022 .LoadingPlanCommunicationRequest\032\036."
          + "LoadingPlanCommunicationReply\"\000\022`\n\032saveP"
          + "yUserForCommunication\022 .LoadingPlanCommu"
          + "nicationRequest\032\036.LoadingPlanCommunicati"
          + "onReply\"\000\022U\n\024triggerCommunication\022\034.Comm"
          + "unicationTriggerRequest\032\035.CommunicationT"
          + "riggerResponse\"\000\022K\n\034getCargoQuantityLoad"
          + "ingRatio\022\023.MaxQuantityRequest\032\024.MaxQuant"
          + "ityResponse\"\000B+\n\'com.cpdss.common.genera"
          + "ted.loading_planP\000b\006proto3"
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
