/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.loading_plan;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: loading_plan/loading_plan_service.proto")
public final class LoadingInformationServiceGrpc {

  private LoadingInformationServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadingInformationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>
      getGetLoadingInformationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingInformation",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest.class,
      responseType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>
      getGetLoadingInformationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>
        getGetLoadingInformationMethod;
    if ((getGetLoadingInformationMethod =
            LoadingInformationServiceGrpc.getGetLoadingInformationMethod)
        == null) {
      synchronized (LoadingInformationServiceGrpc.class) {
        if ((getGetLoadingInformationMethod =
                LoadingInformationServiceGrpc.getGetLoadingInformationMethod)
            == null) {
          LoadingInformationServiceGrpc.getGetLoadingInformationMethod =
              getGetLoadingInformationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationRequest,
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformation>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadingInformation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformation.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInformationServiceMethodDescriptorSupplier(
                              "GetLoadingInformation"))
                      .build();
        }
      }
    }
    return getGetLoadingInformationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation,
          com.cpdss.common.generated.Common.ResponseStatus>
      getSaveLoadingInformationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadingInformation",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation,
          com.cpdss.common.generated.Common.ResponseStatus>
      getSaveLoadingInformationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation,
            com.cpdss.common.generated.Common.ResponseStatus>
        getSaveLoadingInformationMethod;
    if ((getSaveLoadingInformationMethod =
            LoadingInformationServiceGrpc.getSaveLoadingInformationMethod)
        == null) {
      synchronized (LoadingInformationServiceGrpc.class) {
        if ((getSaveLoadingInformationMethod =
                LoadingInformationServiceGrpc.getSaveLoadingInformationMethod)
            == null) {
          LoadingInformationServiceGrpc.getSaveLoadingInformationMethod =
              getSaveLoadingInformationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformation,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadingInformation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformation.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInformationServiceMethodDescriptorSupplier(
                              "SaveLoadingInformation"))
                      .build();
        }
      }
    }
    return getSaveLoadingInformationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply>
      getGetLoadigInformationBySynopticalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadigInformationBySynoptical",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalRequest.class,
      responseType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply>
      getGetLoadigInformationBySynopticalMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalReply>
        getGetLoadigInformationBySynopticalMethod;
    if ((getGetLoadigInformationBySynopticalMethod =
            LoadingInformationServiceGrpc.getGetLoadigInformationBySynopticalMethod)
        == null) {
      synchronized (LoadingInformationServiceGrpc.class) {
        if ((getGetLoadigInformationBySynopticalMethod =
                LoadingInformationServiceGrpc.getGetLoadigInformationBySynopticalMethod)
            == null) {
          LoadingInformationServiceGrpc.getGetLoadigInformationBySynopticalMethod =
              getGetLoadigInformationBySynopticalMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationSynopticalRequest,
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationSynopticalReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadigInformationBySynoptical"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformationSynopticalRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformationSynopticalReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInformationServiceMethodDescriptorSupplier(
                              "GetLoadigInformationBySynoptical"))
                      .build();
        }
      }
    }
    return getGetLoadigInformationBySynopticalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay>
      getUpdateUllageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateUllage",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest
              .class,
      responseType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay>
      getUpdateUllageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay>
        getUpdateUllageMethod;
    if ((getUpdateUllageMethod = LoadingInformationServiceGrpc.getUpdateUllageMethod) == null) {
      synchronized (LoadingInformationServiceGrpc.class) {
        if ((getUpdateUllageMethod = LoadingInformationServiceGrpc.getUpdateUllageMethod) == null) {
          LoadingInformationServiceGrpc.getUpdateUllageMethod =
              getUpdateUllageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .UpdateUllageLoadingRequest,
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .UpdateUllageLoadingReplay>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateUllage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .UpdateUllageLoadingRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .UpdateUllageLoadingReplay.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInformationServiceMethodDescriptorSupplier("UpdateUllage"))
                      .build();
        }
      }
    }
    return getUpdateUllageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getGenerateLoadingPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateLoadingPlan",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getGenerateLoadingPlanMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
            com.cpdss.common.generated.Common.ResponseStatus>
        getGenerateLoadingPlanMethod;
    if ((getGenerateLoadingPlanMethod = LoadingInformationServiceGrpc.getGenerateLoadingPlanMethod)
        == null) {
      synchronized (LoadingInformationServiceGrpc.class) {
        if ((getGenerateLoadingPlanMethod =
                LoadingInformationServiceGrpc.getGenerateLoadingPlanMethod)
            == null) {
          LoadingInformationServiceGrpc.getGenerateLoadingPlanMethod =
              getGenerateLoadingPlanMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationRequest,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GenerateLoadingPlan"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInformationServiceMethodDescriptorSupplier(
                              "GenerateLoadingPlan"))
                      .build();
        }
      }
    }
    return getGenerateLoadingPlanMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static LoadingInformationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInformationServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingInformationServiceStub>() {
          @java.lang.Override
          public LoadingInformationServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingInformationServiceStub(channel, callOptions);
          }
        };
    return LoadingInformationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadingInformationServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInformationServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingInformationServiceBlockingStub>() {
          @java.lang.Override
          public LoadingInformationServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingInformationServiceBlockingStub(channel, callOptions);
          }
        };
    return LoadingInformationServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadingInformationServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInformationServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingInformationServiceFutureStub>() {
          @java.lang.Override
          public LoadingInformationServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingInformationServiceFutureStub(channel, callOptions);
          }
        };
    return LoadingInformationServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadingInformationServiceImplBase
      implements io.grpc.BindableService {

    /** */
    public void getLoadingInformation(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadingInformationMethod(), responseObserver);
    }

    /** */
    public void saveLoadingInformation(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadingInformationMethod(), responseObserver);
    }

    /** */
    public void getLoadigInformationBySynoptical(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalRequest
            request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadigInformationBySynopticalMethod(), responseObserver);
    }

    /** */
    public void updateUllage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest
            request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay>
            responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateUllageMethod(), responseObserver);
    }

    /** */
    public void generateLoadingPlan(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGenerateLoadingPlanMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetLoadingInformationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationRequest,
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>(
                      this, METHODID_GET_LOADING_INFORMATION)))
          .addMethod(
              getSaveLoadingInformationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_SAVE_LOADING_INFORMATION)))
          .addMethod(
              getGetLoadigInformationBySynopticalMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalRequest,
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalReply>(
                      this, METHODID_GET_LOADIG_INFORMATION_BY_SYNOPTICAL)))
          .addMethod(
              getUpdateUllageMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .UpdateUllageLoadingRequest,
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .UpdateUllageLoadingReplay>(this, METHODID_UPDATE_ULLAGE)))
          .addMethod(
              getGenerateLoadingPlanMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationRequest,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_GENERATE_LOADING_PLAN)))
          .build();
    }
  }

  /** */
  public static final class LoadingInformationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LoadingInformationServiceStub> {
    private LoadingInformationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInformationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInformationServiceStub(channel, callOptions);
    }

    /** */
    public void getLoadingInformation(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadingInformationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadingInformation(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadingInformationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadigInformationBySynoptical(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalRequest
            request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadigInformationBySynopticalMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void updateUllage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest
            request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateUllageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void generateLoadingPlan(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGenerateLoadingPlanMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class LoadingInformationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LoadingInformationServiceBlockingStub> {
    private LoadingInformationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInformationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInformationServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation
        getLoadingInformation(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadingInformationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus saveLoadingInformation(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadingInformationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingInformationSynopticalReply
        getLoadigInformationBySynoptical(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadigInformationBySynopticalMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay
        updateUllage(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest
                request) {
      return blockingUnaryCall(getChannel(), getUpdateUllageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus generateLoadingPlan(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
            request) {
      return blockingUnaryCall(
          getChannel(), getGenerateLoadingPlanMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class LoadingInformationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LoadingInformationServiceFutureStub> {
    private LoadingInformationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInformationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInformationServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>
        getLoadingInformation(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadingInformationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        saveLoadingInformation(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadingInformationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalReply>
        getLoadigInformationBySynoptical(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadigInformationBySynopticalMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingReplay>
        updateUllage(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateUllageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        generateLoadingPlan(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGenerateLoadingPlanMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LOADING_INFORMATION = 0;
  private static final int METHODID_SAVE_LOADING_INFORMATION = 1;
  private static final int METHODID_GET_LOADIG_INFORMATION_BY_SYNOPTICAL = 2;
  private static final int METHODID_UPDATE_ULLAGE = 3;
  private static final int METHODID_GENERATE_LOADING_PLAN = 4;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadingInformationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadingInformationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LOADING_INFORMATION:
          serviceImpl.getLoadingInformation(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADING_INFORMATION:
          serviceImpl.saveLoadingInformation(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation)
                  request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_GET_LOADIG_INFORMATION_BY_SYNOPTICAL:
          serviceImpl.getLoadigInformationBySynoptical(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingInformationSynopticalRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalReply>)
                  responseObserver);
          break;
        case METHODID_UPDATE_ULLAGE:
          serviceImpl.updateUllage(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllageLoadingRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .UpdateUllageLoadingReplay>)
                  responseObserver);
          break;
        case METHODID_GENERATE_LOADING_PLAN:
          serviceImpl.generateLoadingPlan(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest)
                  request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
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

  private abstract static class LoadingInformationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadingInformationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadingInformationService");
    }
  }

  private static final class LoadingInformationServiceFileDescriptorSupplier
      extends LoadingInformationServiceBaseDescriptorSupplier {
    LoadingInformationServiceFileDescriptorSupplier() {}
  }

  private static final class LoadingInformationServiceMethodDescriptorSupplier
      extends LoadingInformationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadingInformationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoadingInformationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new LoadingInformationServiceFileDescriptorSupplier())
                      .addMethod(getGetLoadingInformationMethod())
                      .addMethod(getSaveLoadingInformationMethod())
                      .addMethod(getGetLoadigInformationBySynopticalMethod())
                      .addMethod(getUpdateUllageMethod())
                      .addMethod(getGenerateLoadingPlanMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
