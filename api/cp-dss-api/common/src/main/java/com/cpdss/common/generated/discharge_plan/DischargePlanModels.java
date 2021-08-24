/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

public final class DischargePlanModels {
  private DischargePlanModels() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeInformationRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeInformationRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_DischargeInformation_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_DischargeInformation_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n*discharge_plan/discharge_plan_models.p"
          + "roto\032\014common.proto\"\270\001\n\033DischargeInformat"
          + "ionRequest\022\021\n\tcompanyId\030\001 \001(\003\022\020\n\010vesselI"
          + "d\030\002 \001(\003\022\020\n\010voyageId\030\003 \001(\003\022\027\n\017dischargeIn"
          + "foId\030\004 \001(\003\022\030\n\020loadingPatternId\030\005 \001(\003\022\026\n\016"
          + "portRotationId\030\006 \001(\003\022\027\n\017synopticTableId\030"
          + "\007 \001(\003\"?\n\024DischargeInformation\022\'\n\016respons"
          + "eStatus\030\001 \001(\0132\017.ResponseStatusB-\n)com.cp"
          + "dss.common.generated.discharge_planP\001b\006p"
          + "roto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.cpdss.common.generated.Common.getDescriptor(),
            });
    internal_static_DischargeInformationRequest_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_DischargeInformationRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeInformationRequest_descriptor,
            new java.lang.String[] {
              "CompanyId",
              "VesselId",
              "VoyageId",
              "DischargeInfoId",
              "LoadingPatternId",
              "PortRotationId",
              "SynopticTableId",
            });
    internal_static_DischargeInformation_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_DischargeInformation_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_DischargeInformation_descriptor,
            new java.lang.String[] {
              "ResponseStatus",
            });
    com.cpdss.common.generated.Common.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
