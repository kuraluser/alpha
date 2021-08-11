/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: loadable_study.proto")
public final class DischargeStudyOperationServiceGrpc {

  private DischargeStudyOperationServiceGrpc() {}

  public static final String SERVICE_NAME = "DischargeStudyOperationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
      getDeleteDischargeStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteDischargeStudy",
      requestType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest.class,
      responseType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
      getDeleteDischargeStudyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest,
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
        getDeleteDischargeStudyMethod;
    if ((getDeleteDischargeStudyMethod =
            DischargeStudyOperationServiceGrpc.getDeleteDischargeStudyMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getDeleteDischargeStudyMethod =
                DischargeStudyOperationServiceGrpc.getDeleteDischargeStudyMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getDeleteDischargeStudyMethod =
              getDeleteDischargeStudyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyRequest,
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "deleteDischargeStudy"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "deleteDischargeStudy"))
                      .build();
        }
      }
    }
    return getDeleteDischargeStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
      getSaveDischargeStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveDischargeStudy",
      requestType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail.class,
      responseType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
      getSaveDischargeStudyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
        getSaveDischargeStudyMethod;
    if ((getSaveDischargeStudyMethod =
            DischargeStudyOperationServiceGrpc.getSaveDischargeStudyMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getSaveDischargeStudyMethod =
                DischargeStudyOperationServiceGrpc.getSaveDischargeStudyMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getSaveDischargeStudyMethod =
              getSaveDischargeStudyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyDetail,
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveDischargeStudy"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyDetail.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "SaveDischargeStudy"))
                      .build();
        }
      }
    }
    return getSaveDischargeStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply>
      getUpdateDischargeStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateDischargeStudy",
      requestType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail.class,
      responseType =
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .UpdateDischargeStudyReply
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply>
      getUpdateDischargeStudyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail,
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply>
        getUpdateDischargeStudyMethod;
    if ((getUpdateDischargeStudyMethod =
            DischargeStudyOperationServiceGrpc.getUpdateDischargeStudyMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getUpdateDischargeStudyMethod =
                DischargeStudyOperationServiceGrpc.getUpdateDischargeStudyMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getUpdateDischargeStudyMethod =
              getUpdateDischargeStudyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyDetail,
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .UpdateDischargeStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "UpdateDischargeStudy"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyDetail.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .UpdateDischargeStudyReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "UpdateDischargeStudy"))
                      .build();
        }
      }
    }
    return getUpdateDischargeStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply>
      getGetDischargeStudyPortWiseCargosMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getDischargeStudyPortWiseCargos",
      requestType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest.class,
      responseType =
          com.cpdss
              .common
              .generated
              .loadableStudy
              .LoadableStudyModels
              .DishargeStudyCargoReply
              .class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply>
      getGetDischargeStudyPortWiseCargosMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest,
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply>
        getGetDischargeStudyPortWiseCargosMethod;
    if ((getGetDischargeStudyPortWiseCargosMethod =
            DischargeStudyOperationServiceGrpc.getGetDischargeStudyPortWiseCargosMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getGetDischargeStudyPortWiseCargosMethod =
                DischargeStudyOperationServiceGrpc.getGetDischargeStudyPortWiseCargosMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getGetDischargeStudyPortWiseCargosMethod =
              getGetDischargeStudyPortWiseCargosMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyRequest,
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DishargeStudyCargoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getDischargeStudyPortWiseCargos"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DishargeStudyCargoReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "getDischargeStudyPortWiseCargos"))
                      .build();
        }
      }
    }
    return getGetDischargeStudyPortWiseCargosMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
      getSaveDischargeStudyBackLoadingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveDischargeStudyBackLoading",
      requestType =
          com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest.class,
      responseType =
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest,
          com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
      getSaveDischargeStudyBackLoadingMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest,
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
        getSaveDischargeStudyBackLoadingMethod;
    if ((getSaveDischargeStudyBackLoadingMethod =
            DischargeStudyOperationServiceGrpc.getSaveDischargeStudyBackLoadingMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getSaveDischargeStudyBackLoadingMethod =
                DischargeStudyOperationServiceGrpc.getSaveDischargeStudyBackLoadingMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getSaveDischargeStudyBackLoadingMethod =
              getSaveDischargeStudyBackLoadingMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy
                              .DishargeStudyBackLoadingSaveRequest,
                          com.cpdss
                              .common
                              .generated
                              .loadableStudy
                              .LoadableStudyModels
                              .DischargeStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveDischargeStudyBackLoading"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .DishargeStudyBackLoadingSaveRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loadableStudy.LoadableStudyModels
                                  .DischargeStudyReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "SaveDischargeStudyBackLoading"))
                      .build();
        }
      }
    }
    return getSaveDischargeStudyBackLoadingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getGenerateDischargePatternsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateDischargePatterns",
      requestType = com.cpdss.common.generated.LoadableStudy.AlgoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getGenerateDischargePatternsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.AlgoRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getGenerateDischargePatternsMethod;
    if ((getGenerateDischargePatternsMethod =
            DischargeStudyOperationServiceGrpc.getGenerateDischargePatternsMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getGenerateDischargePatternsMethod =
                DischargeStudyOperationServiceGrpc.getGenerateDischargePatternsMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getGenerateDischargePatternsMethod =
              getGenerateDischargePatternsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.AlgoRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GenerateDischargePatterns"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "GenerateDischargePatterns"))
                      .build();
        }
      }
    }
    return getGenerateDischargePatternsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getGetDischargePlanDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDischargePlanDetails",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getGetDischargePlanDetailsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getGetDischargePlanDetailsMethod;
    if ((getGetDischargePlanDetailsMethod =
            DischargeStudyOperationServiceGrpc.getGetDischargePlanDetailsMethod)
        == null) {
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        if ((getGetDischargePlanDetailsMethod =
                DischargeStudyOperationServiceGrpc.getGetDischargePlanDetailsMethod)
            == null) {
          DischargeStudyOperationServiceGrpc.getGetDischargePlanDetailsMethod =
              getGetDischargePlanDetailsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetDischargePlanDetails"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceMethodDescriptorSupplier(
                              "GetDischargePlanDetails"))
                      .build();
        }
      }
    }
    return getGetDischargePlanDetailsMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static DischargeStudyOperationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeStudyOperationServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeStudyOperationServiceStub>() {
          @java.lang.Override
          public DischargeStudyOperationServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeStudyOperationServiceStub(channel, callOptions);
          }
        };
    return DischargeStudyOperationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DischargeStudyOperationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeStudyOperationServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeStudyOperationServiceBlockingStub>() {
          @java.lang.Override
          public DischargeStudyOperationServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeStudyOperationServiceBlockingStub(channel, callOptions);
          }
        };
    return DischargeStudyOperationServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static DischargeStudyOperationServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeStudyOperationServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeStudyOperationServiceFutureStub>() {
          @java.lang.Override
          public DischargeStudyOperationServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeStudyOperationServiceFutureStub(channel, callOptions);
          }
        };
    return DischargeStudyOperationServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class DischargeStudyOperationServiceImplBase
      implements io.grpc.BindableService {

    /** */
    public void deleteDischargeStudy(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteDischargeStudyMethod(), responseObserver);
    }

    /** */
    public void saveDischargeStudy(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveDischargeStudyMethod(), responseObserver);
    }

    /** */
    public void updateDischargeStudy(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail request,
        io.grpc.stub.StreamObserver<
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateDischargeStudyMethod(), responseObserver);
    }

    /** */
    public void getDischargeStudyPortWiseCargos(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetDischargeStudyPortWiseCargosMethod(), responseObserver);
    }

    /** */
    public void saveDischargeStudyBackLoading(
        com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveDischargeStudyBackLoadingMethod(), responseObserver);
    }

    /** */
    public void generateDischargePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGenerateDischargePatternsMethod(), responseObserver);
    }

    /** */
    public void getDischargePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetDischargePlanDetailsMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getDeleteDischargeStudyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyRequest,
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyReply>(this, METHODID_DELETE_DISCHARGE_STUDY)))
          .addMethod(
              getSaveDischargeStudyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyDetail,
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyReply>(this, METHODID_SAVE_DISCHARGE_STUDY)))
          .addMethod(
              getUpdateDischargeStudyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyDetail,
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .UpdateDischargeStudyReply>(this, METHODID_UPDATE_DISCHARGE_STUDY)))
          .addMethod(
              getGetDischargeStudyPortWiseCargosMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyRequest,
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DishargeStudyCargoReply>(
                      this, METHODID_GET_DISCHARGE_STUDY_PORT_WISE_CARGOS)))
          .addMethod(
              getSaveDischargeStudyBackLoadingMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest,
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyReply>(this, METHODID_SAVE_DISCHARGE_STUDY_BACK_LOADING)))
          .addMethod(
              getGenerateDischargePatternsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_GENERATE_DISCHARGE_PATTERNS)))
          .addMethod(
              getGetDischargePlanDetailsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_GET_DISCHARGE_PLAN_DETAILS)))
          .build();
    }
  }

  /** */
  public static final class DischargeStudyOperationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DischargeStudyOperationServiceStub> {
    private DischargeStudyOperationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeStudyOperationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeStudyOperationServiceStub(channel, callOptions);
    }

    /** */
    public void deleteDischargeStudy(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteDischargeStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveDischargeStudy(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveDischargeStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void updateDischargeStudy(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail request,
        io.grpc.stub.StreamObserver<
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .UpdateDischargeStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateDischargeStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getDischargeStudyPortWiseCargos(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss
                    .common
                    .generated
                    .loadableStudy
                    .LoadableStudyModels
                    .DishargeStudyCargoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDischargeStudyPortWiseCargosMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveDischargeStudyBackLoading(
        com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveDischargeStudyBackLoadingMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void generateDischargePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGenerateDischargePatternsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getDischargePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDischargePlanDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class DischargeStudyOperationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DischargeStudyOperationServiceBlockingStub> {
    private DischargeStudyOperationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeStudyOperationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeStudyOperationServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        deleteDischargeStudy(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getDeleteDischargeStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        saveDischargeStudy(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                request) {
      return blockingUnaryCall(
          getChannel(), getSaveDischargeStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply
        updateDischargeStudy(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                request) {
      return blockingUnaryCall(
          getChannel(), getUpdateDischargeStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply
        getDischargeStudyPortWiseCargos(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetDischargeStudyPortWiseCargosMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply
        saveDischargeStudyBackLoading(
            com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveDischargeStudyBackLoadingMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply generateDischargePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGenerateDischargePatternsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply getDischargePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetDischargePlanDetailsMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class DischargeStudyOperationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DischargeStudyOperationServiceFutureStub> {
    private DischargeStudyOperationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeStudyOperationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeStudyOperationServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
        deleteDischargeStudy(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteDischargeStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
        saveDischargeStudy(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveDischargeStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.UpdateDischargeStudyReply>
        updateDischargeStudy(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail
                request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateDischargeStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DishargeStudyCargoReply>
        getDischargeStudyPortWiseCargos(
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDischargeStudyPortWiseCargosMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyReply>
        saveDischargeStudyBackLoading(
            com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveDischargeStudyBackLoadingMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        generateDischargePatterns(com.cpdss.common.generated.LoadableStudy.AlgoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGenerateDischargePatternsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getDischargePlanDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDischargePlanDetailsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_DELETE_DISCHARGE_STUDY = 0;
  private static final int METHODID_SAVE_DISCHARGE_STUDY = 1;
  private static final int METHODID_UPDATE_DISCHARGE_STUDY = 2;
  private static final int METHODID_GET_DISCHARGE_STUDY_PORT_WISE_CARGOS = 3;
  private static final int METHODID_SAVE_DISCHARGE_STUDY_BACK_LOADING = 4;
  private static final int METHODID_GENERATE_DISCHARGE_PATTERNS = 5;
  private static final int METHODID_GET_DISCHARGE_PLAN_DETAILS = 6;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DischargeStudyOperationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DischargeStudyOperationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DELETE_DISCHARGE_STUDY:
          serviceImpl.deleteDischargeStudy(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_DISCHARGE_STUDY:
          serviceImpl.saveDischargeStudy(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyReply>)
                  responseObserver);
          break;
        case METHODID_UPDATE_DISCHARGE_STUDY:
          serviceImpl.updateDischargeStudy(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyDetail)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .UpdateDischargeStudyReply>)
                  responseObserver);
          break;
        case METHODID_GET_DISCHARGE_STUDY_PORT_WISE_CARGOS:
          serviceImpl.getDischargeStudyPortWiseCargos(
              (com.cpdss.common.generated.loadableStudy.LoadableStudyModels.DischargeStudyRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DishargeStudyCargoReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_DISCHARGE_STUDY_BACK_LOADING:
          serviceImpl.saveDischargeStudyBackLoading(
              (com.cpdss.common.generated.LoadableStudy.DishargeStudyBackLoadingSaveRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss
                          .common
                          .generated
                          .loadableStudy
                          .LoadableStudyModels
                          .DischargeStudyReply>)
                  responseObserver);
          break;
        case METHODID_GENERATE_DISCHARGE_PATTERNS:
          serviceImpl.generateDischargePatterns(
              (com.cpdss.common.generated.LoadableStudy.AlgoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>)
                  responseObserver);
          break;
        case METHODID_GET_DISCHARGE_PLAN_DETAILS:
          serviceImpl.getDischargePlanDetails(
              (com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
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

  private abstract static class DischargeStudyOperationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DischargeStudyOperationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.LoadableStudy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DischargeStudyOperationService");
    }
  }

  private static final class DischargeStudyOperationServiceFileDescriptorSupplier
      extends DischargeStudyOperationServiceBaseDescriptorSupplier {
    DischargeStudyOperationServiceFileDescriptorSupplier() {}
  }

  private static final class DischargeStudyOperationServiceMethodDescriptorSupplier
      extends DischargeStudyOperationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DischargeStudyOperationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DischargeStudyOperationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(
                          new DischargeStudyOperationServiceFileDescriptorSupplier())
                      .addMethod(getDeleteDischargeStudyMethod())
                      .addMethod(getSaveDischargeStudyMethod())
                      .addMethod(getUpdateDischargeStudyMethod())
                      .addMethod(getGetDischargeStudyPortWiseCargosMethod())
                      .addMethod(getSaveDischargeStudyBackLoadingMethod())
                      .addMethod(getGenerateDischargePatternsMethod())
                      .addMethod(getGetDischargePlanDetailsMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
