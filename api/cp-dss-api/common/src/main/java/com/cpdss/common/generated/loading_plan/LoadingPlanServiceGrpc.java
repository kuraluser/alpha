package com.cpdss.common.generated.loading_plan;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: loading_plan/loading_plan_service.proto")
public final class LoadingPlanServiceGrpc {

  private LoadingPlanServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadingPlanService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply> getLoadingPlanSynchronizationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LoadingPlanSynchronization",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply> getLoadingPlanSynchronizationMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply> getLoadingPlanSynchronizationMethod;
    if ((getLoadingPlanSynchronizationMethod = LoadingPlanServiceGrpc.getLoadingPlanSynchronizationMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getLoadingPlanSynchronizationMethod = LoadingPlanServiceGrpc.getLoadingPlanSynchronizationMethod) == null) {
          LoadingPlanServiceGrpc.getLoadingPlanSynchronizationMethod = getLoadingPlanSynchronizationMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LoadingPlanSynchronization"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("LoadingPlanSynchronization"))
              .build();
        }
      }
    }
    return getLoadingPlanSynchronizationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse> getSaveLoadingPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadingPlan",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse> getSaveLoadingPlanMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse> getSaveLoadingPlanMethod;
    if ((getSaveLoadingPlanMethod = LoadingPlanServiceGrpc.getSaveLoadingPlanMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getSaveLoadingPlanMethod = LoadingPlanServiceGrpc.getSaveLoadingPlanMethod) == null) {
          LoadingPlanServiceGrpc.getSaveLoadingPlanMethod = getSaveLoadingPlanMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveLoadingPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("SaveLoadingPlan"))
              .build();
        }
      }
    }
    return getSaveLoadingPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply> getGetLoadingPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingPlan",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply> getGetLoadingPlanMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply> getGetLoadingPlanMethod;
    if ((getGetLoadingPlanMethod = LoadingPlanServiceGrpc.getGetLoadingPlanMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetLoadingPlanMethod = LoadingPlanServiceGrpc.getGetLoadingPlanMethod) == null) {
          LoadingPlanServiceGrpc.getGetLoadingPlanMethod = getGetLoadingPlanMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadingPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetLoadingPlan"))
              .build();
        }
      }
    }
    return getGetLoadingPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply> getGetOrSaveRulesForLoadingPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOrSaveRulesForLoadingPlan",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply> getGetOrSaveRulesForLoadingPlanMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply> getGetOrSaveRulesForLoadingPlanMethod;
    if ((getGetOrSaveRulesForLoadingPlanMethod = LoadingPlanServiceGrpc.getGetOrSaveRulesForLoadingPlanMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetOrSaveRulesForLoadingPlanMethod = LoadingPlanServiceGrpc.getGetOrSaveRulesForLoadingPlanMethod) == null) {
          LoadingPlanServiceGrpc.getGetOrSaveRulesForLoadingPlanMethod = getGetOrSaveRulesForLoadingPlanMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOrSaveRulesForLoadingPlan"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetOrSaveRulesForLoadingPlan"))
              .build();
        }
      }
    }
    return getGetOrSaveRulesForLoadingPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply> getGetLoadingSequencesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingSequences",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply> getGetLoadingSequencesMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply> getGetLoadingSequencesMethod;
    if ((getGetLoadingSequencesMethod = LoadingPlanServiceGrpc.getGetLoadingSequencesMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetLoadingSequencesMethod = LoadingPlanServiceGrpc.getGetLoadingSequencesMethod) == null) {
          LoadingPlanServiceGrpc.getGetLoadingSequencesMethod = getGetLoadingSequencesMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadingSequences"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetLoadingSequences"))
              .build();
        }
      }
    }
    return getGetLoadingSequencesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse> getGetUpdateUllageDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUpdateUllageDetails",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse> getGetUpdateUllageDetailsMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse> getGetUpdateUllageDetailsMethod;
    if ((getGetUpdateUllageDetailsMethod = LoadingPlanServiceGrpc.getGetUpdateUllageDetailsMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetUpdateUllageDetailsMethod = LoadingPlanServiceGrpc.getGetUpdateUllageDetailsMethod) == null) {
          LoadingPlanServiceGrpc.getGetUpdateUllageDetailsMethod = getGetUpdateUllageDetailsMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUpdateUllageDetails"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetUpdateUllageDetails"))
              .build();
        }
      }
    }
    return getGetUpdateUllageDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply> getGetBillOfLaddingDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBillOfLaddingDetails",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply> getGetBillOfLaddingDetailsMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply> getGetBillOfLaddingDetailsMethod;
    if ((getGetBillOfLaddingDetailsMethod = LoadingPlanServiceGrpc.getGetBillOfLaddingDetailsMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetBillOfLaddingDetailsMethod = LoadingPlanServiceGrpc.getGetBillOfLaddingDetailsMethod) == null) {
          LoadingPlanServiceGrpc.getGetBillOfLaddingDetailsMethod = getGetBillOfLaddingDetailsMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetBillOfLaddingDetails"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetBillOfLaddingDetails"))
              .build();
        }
      }
    }
    return getGetBillOfLaddingDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse> getGetCargoNominationMaxQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoNominationMaxQuantity",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse> getGetCargoNominationMaxQuantityMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse> getGetCargoNominationMaxQuantityMethod;
    if ((getGetCargoNominationMaxQuantityMethod = LoadingPlanServiceGrpc.getGetCargoNominationMaxQuantityMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetCargoNominationMaxQuantityMethod = LoadingPlanServiceGrpc.getGetCargoNominationMaxQuantityMethod) == null) {
          LoadingPlanServiceGrpc.getGetCargoNominationMaxQuantityMethod = getGetCargoNominationMaxQuantityMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCargoNominationMaxQuantity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetCargoNominationMaxQuantity"))
              .build();
        }
      }
    }
    return getGetCargoNominationMaxQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply> getGetLoadicatorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadicatorData",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply> getGetLoadicatorDataMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply> getGetLoadicatorDataMethod;
    if ((getGetLoadicatorDataMethod = LoadingPlanServiceGrpc.getGetLoadicatorDataMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetLoadicatorDataMethod = LoadingPlanServiceGrpc.getGetLoadicatorDataMethod) == null) {
          LoadingPlanServiceGrpc.getGetLoadicatorDataMethod = getGetLoadicatorDataMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadicatorData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetLoadicatorData"))
              .build();
        }
      }
    }
    return getGetLoadicatorDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply> getGetLoadableStudyShoreTwoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudyShoreTwo",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply> getGetLoadableStudyShoreTwoMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply> getGetLoadableStudyShoreTwoMethod;
    if ((getGetLoadableStudyShoreTwoMethod = LoadingPlanServiceGrpc.getGetLoadableStudyShoreTwoMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetLoadableStudyShoreTwoMethod = LoadingPlanServiceGrpc.getGetLoadableStudyShoreTwoMethod) == null) {
          LoadingPlanServiceGrpc.getGetLoadableStudyShoreTwoMethod = getGetLoadableStudyShoreTwoMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadableStudyShoreTwo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetLoadableStudyShoreTwo"))
              .build();
        }
      }
    }
    return getGetLoadableStudyShoreTwoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest,
      com.cpdss.common.generated.Common.ResponseStatus> getValidateStowageAndBillOfLaddingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "validateStowageAndBillOfLadding",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest,
      com.cpdss.common.generated.Common.ResponseStatus> getValidateStowageAndBillOfLaddingMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest, com.cpdss.common.generated.Common.ResponseStatus> getValidateStowageAndBillOfLaddingMethod;
    if ((getValidateStowageAndBillOfLaddingMethod = LoadingPlanServiceGrpc.getValidateStowageAndBillOfLaddingMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getValidateStowageAndBillOfLaddingMethod = LoadingPlanServiceGrpc.getValidateStowageAndBillOfLaddingMethod) == null) {
          LoadingPlanServiceGrpc.getValidateStowageAndBillOfLaddingMethod = getValidateStowageAndBillOfLaddingMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest, com.cpdss.common.generated.Common.ResponseStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "validateStowageAndBillOfLadding"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.Common.ResponseStatus.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("validateStowageAndBillOfLadding"))
              .build();
        }
      }
    }
    return getValidateStowageAndBillOfLaddingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> getGetLoadingPlanCommingleDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingPlanCommingleDetails",
      requestType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest.class,
      responseType = com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> getGetLoadingPlanCommingleDetailsMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> getGetLoadingPlanCommingleDetailsMethod;
    if ((getGetLoadingPlanCommingleDetailsMethod = LoadingPlanServiceGrpc.getGetLoadingPlanCommingleDetailsMethod) == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetLoadingPlanCommingleDetailsMethod = LoadingPlanServiceGrpc.getGetLoadingPlanCommingleDetailsMethod) == null) {
          LoadingPlanServiceGrpc.getGetLoadingPlanCommingleDetailsMethod = getGetLoadingPlanCommingleDetailsMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest, com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadingPlanCommingleDetails"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadingPlanServiceMethodDescriptorSupplier("GetLoadingPlanCommingleDetails"))
              .build();
        }
      }
    }
    return getGetLoadingPlanCommingleDetailsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LoadingPlanServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceStub>() {
        @java.lang.Override
        public LoadingPlanServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadingPlanServiceStub(channel, callOptions);
        }
      };
    return LoadingPlanServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadingPlanServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceBlockingStub>() {
        @java.lang.Override
        public LoadingPlanServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadingPlanServiceBlockingStub(channel, callOptions);
        }
      };
    return LoadingPlanServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LoadingPlanServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceFutureStub>() {
        @java.lang.Override
        public LoadingPlanServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadingPlanServiceFutureStub(channel, callOptions);
        }
      };
    return LoadingPlanServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LoadingPlanServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void loadingPlanSynchronization(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoadingPlanSynchronizationMethod(), responseObserver);
    }

    /**
     */
    public void saveLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveLoadingPlanMethod(), responseObserver);
    }

    /**
     */
    public void getLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLoadingPlanMethod(), responseObserver);
    }

    /**
     */
    public void getOrSaveRulesForLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetOrSaveRulesForLoadingPlanMethod(), responseObserver);
    }

    /**
     */
    public void getLoadingSequences(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLoadingSequencesMethod(), responseObserver);
    }

    /**
     */
    public void getUpdateUllageDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUpdateUllageDetailsMethod(), responseObserver);
    }

    /**
     */
    public void getBillOfLaddingDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetBillOfLaddingDetailsMethod(), responseObserver);
    }

    /**
     */
    public void getCargoNominationMaxQuantity(com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCargoNominationMaxQuantityMethod(), responseObserver);
    }

    /**
     */
    public void getLoadicatorData(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLoadicatorDataMethod(), responseObserver);
    }

    /**
     */
    public void getLoadableStudyShoreTwo(com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLoadableStudyShoreTwoMethod(), responseObserver);
    }

    /**
     */
    public void validateStowageAndBillOfLadding(com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getValidateStowageAndBillOfLaddingMethod(), responseObserver);
    }

    /**
     */
    public void getLoadingPlanCommingleDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLoadingPlanCommingleDetailsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoadingPlanSynchronizationMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>(
                  this, METHODID_LOADING_PLAN_SYNCHRONIZATION)))
          .addMethod(
            getSaveLoadingPlanMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse>(
                  this, METHODID_SAVE_LOADING_PLAN)))
          .addMethod(
            getGetLoadingPlanMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply>(
                  this, METHODID_GET_LOADING_PLAN)))
          .addMethod(
            getGetOrSaveRulesForLoadingPlanMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>(
                  this, METHODID_GET_OR_SAVE_RULES_FOR_LOADING_PLAN)))
          .addMethod(
            getGetLoadingSequencesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply>(
                  this, METHODID_GET_LOADING_SEQUENCES)))
          .addMethod(
            getGetUpdateUllageDetailsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse>(
                  this, METHODID_GET_UPDATE_ULLAGE_DETAILS)))
          .addMethod(
            getGetBillOfLaddingDetailsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply>(
                  this, METHODID_GET_BILL_OF_LADDING_DETAILS)))
          .addMethod(
            getGetCargoNominationMaxQuantityMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse>(
                  this, METHODID_GET_CARGO_NOMINATION_MAX_QUANTITY)))
          .addMethod(
            getGetLoadicatorDataMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply>(
                  this, METHODID_GET_LOADICATOR_DATA)))
          .addMethod(
            getGetLoadableStudyShoreTwoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply>(
                  this, METHODID_GET_LOADABLE_STUDY_SHORE_TWO)))
          .addMethod(
            getValidateStowageAndBillOfLaddingMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest,
                com.cpdss.common.generated.Common.ResponseStatus>(
                  this, METHODID_VALIDATE_STOWAGE_AND_BILL_OF_LADDING)))
          .addMethod(
            getGetLoadingPlanCommingleDetailsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest,
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply>(
                  this, METHODID_GET_LOADING_PLAN_COMMINGLE_DETAILS)))
          .build();
    }
  }

  /**
   */
  public static final class LoadingPlanServiceStub extends io.grpc.stub.AbstractAsyncStub<LoadingPlanServiceStub> {
    private LoadingPlanServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceStub(channel, callOptions);
    }

    /**
     */
    public void loadingPlanSynchronization(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoadingPlanSynchronizationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadingPlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadingPlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getOrSaveRulesForLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOrSaveRulesForLoadingPlanMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLoadingSequences(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadingSequencesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUpdateUllageDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUpdateUllageDetailsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getBillOfLaddingDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetBillOfLaddingDetailsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCargoNominationMaxQuantity(com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCargoNominationMaxQuantityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLoadicatorData(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadicatorDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLoadableStudyShoreTwo(com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudyShoreTwoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validateStowageAndBillOfLadding(com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateStowageAndBillOfLaddingMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLoadingPlanCommingleDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadingPlanCommingleDetailsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LoadingPlanServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LoadingPlanServiceBlockingStub> {
    private LoadingPlanServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply loadingPlanSynchronization(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoadingPlanSynchronizationMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse saveLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadingPlanMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply getLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadingPlanMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply getOrSaveRulesForLoadingPlan(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOrSaveRulesForLoadingPlanMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply getLoadingSequences(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadingSequencesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse getUpdateUllageDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUpdateUllageDetailsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply getBillOfLaddingDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetBillOfLaddingDetailsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse getCargoNominationMaxQuantity(com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCargoNominationMaxQuantityMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply getLoadicatorData(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadicatorDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply getLoadableStudyShoreTwo(com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableStudyShoreTwoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.Common.ResponseStatus validateStowageAndBillOfLadding(com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateStowageAndBillOfLaddingMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply getLoadingPlanCommingleDetails(com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadingPlanCommingleDetailsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LoadingPlanServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LoadingPlanServiceFutureStub> {
    private LoadingPlanServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply> loadingPlanSynchronization(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoadingPlanSynchronizationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse> saveLoadingPlan(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadingPlanMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply> getLoadingPlan(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadingPlanMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply> getOrSaveRulesForLoadingPlan(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOrSaveRulesForLoadingPlanMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply> getLoadingSequences(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadingSequencesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse> getUpdateUllageDetails(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUpdateUllageDetailsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply> getBillOfLaddingDetails(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetBillOfLaddingDetailsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse> getCargoNominationMaxQuantity(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCargoNominationMaxQuantityMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply> getLoadicatorData(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadicatorDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply> getLoadableStudyShoreTwo(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyShoreTwoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.Common.ResponseStatus> validateStowageAndBillOfLadding(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateStowageAndBillOfLaddingMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply> getLoadingPlanCommingleDetails(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadingPlanCommingleDetailsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOADING_PLAN_SYNCHRONIZATION = 0;
  private static final int METHODID_SAVE_LOADING_PLAN = 1;
  private static final int METHODID_GET_LOADING_PLAN = 2;
  private static final int METHODID_GET_OR_SAVE_RULES_FOR_LOADING_PLAN = 3;
  private static final int METHODID_GET_LOADING_SEQUENCES = 4;
  private static final int METHODID_GET_UPDATE_ULLAGE_DETAILS = 5;
  private static final int METHODID_GET_BILL_OF_LADDING_DETAILS = 6;
  private static final int METHODID_GET_CARGO_NOMINATION_MAX_QUANTITY = 7;
  private static final int METHODID_GET_LOADICATOR_DATA = 8;
  private static final int METHODID_GET_LOADABLE_STUDY_SHORE_TWO = 9;
  private static final int METHODID_VALIDATE_STOWAGE_AND_BILL_OF_LADDING = 10;
  private static final int METHODID_GET_LOADING_PLAN_COMMINGLE_DETAILS = 11;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadingPlanServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadingPlanServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOADING_PLAN_SYNCHRONIZATION:
          serviceImpl.loadingPlanSynchronization((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>) responseObserver);
          break;
        case METHODID_SAVE_LOADING_PLAN:
          serviceImpl.saveLoadingPlan((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSaveResponse>) responseObserver);
          break;
        case METHODID_GET_LOADING_PLAN:
          serviceImpl.getLoadingPlan((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanReply>) responseObserver);
          break;
        case METHODID_GET_OR_SAVE_RULES_FOR_LOADING_PLAN:
          serviceImpl.getOrSaveRulesForLoadingPlan((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>) responseObserver);
          break;
        case METHODID_GET_LOADING_SEQUENCES:
          serviceImpl.getLoadingSequences((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply>) responseObserver);
          break;
        case METHODID_GET_UPDATE_ULLAGE_DETAILS:
          serviceImpl.getUpdateUllageDetails((com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageDetailsResponse>) responseObserver);
          break;
        case METHODID_GET_BILL_OF_LADDING_DETAILS:
          serviceImpl.getBillOfLaddingDetails((com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLaddingRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalReply>) responseObserver);
          break;
        case METHODID_GET_CARGO_NOMINATION_MAX_QUANTITY:
          serviceImpl.getCargoNominationMaxQuantity((com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.MaxQuantityResponse>) responseObserver);
          break;
        case METHODID_GET_LOADICATOR_DATA:
          serviceImpl.getLoadicatorData((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInfoLoadicatorDataReply>) responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_SHORE_TWO:
          serviceImpl.getLoadableStudyShoreTwo((com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillReply>) responseObserver);
          break;
        case METHODID_VALIDATE_STOWAGE_AND_BILL_OF_LADDING:
          serviceImpl.validateStowageAndBillOfLadding((com.cpdss.common.generated.loading_plan.LoadingPlanModels.StowageAndBillOfLaddingValidationRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>) responseObserver);
          break;
        case METHODID_GET_LOADING_PLAN_COMMINGLE_DETAILS:
          serviceImpl.getLoadingPlanCommingleDetails((com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationSynopticalRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class LoadingPlanServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadingPlanServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadingPlanService");
    }
  }

  private static final class LoadingPlanServiceFileDescriptorSupplier
      extends LoadingPlanServiceBaseDescriptorSupplier {
    LoadingPlanServiceFileDescriptorSupplier() {}
  }

  private static final class LoadingPlanServiceMethodDescriptorSupplier
      extends LoadingPlanServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadingPlanServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LoadingPlanServiceFileDescriptorSupplier())
              .addMethod(getLoadingPlanSynchronizationMethod())
              .addMethod(getSaveLoadingPlanMethod())
              .addMethod(getGetLoadingPlanMethod())
              .addMethod(getGetOrSaveRulesForLoadingPlanMethod())
              .addMethod(getGetLoadingSequencesMethod())
              .addMethod(getGetUpdateUllageDetailsMethod())
              .addMethod(getGetBillOfLaddingDetailsMethod())
              .addMethod(getGetCargoNominationMaxQuantityMethod())
              .addMethod(getGetLoadicatorDataMethod())
              .addMethod(getGetLoadableStudyShoreTwoMethod())
              .addMethod(getValidateStowageAndBillOfLaddingMethod())
              .addMethod(getGetLoadingPlanCommingleDetailsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
