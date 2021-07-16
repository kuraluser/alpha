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
public final class LoadingPlanServiceGrpc {

  private LoadingPlanServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadingPlanService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>
          getLoadingPlanSynchronizationMethod;

  @io.grpc.stub.annotations.RpcMethod(
          fullMethodName = SERVICE_NAME + '/' + "LoadingPlanSynchronization",
          requestType =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails.class,
          responseType =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply.class,
          methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>
  getLoadingPlanSynchronizationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>
            getLoadingPlanSynchronizationMethod;
    if ((getLoadingPlanSynchronizationMethod =
            LoadingPlanServiceGrpc.getLoadingPlanSynchronizationMethod)
            == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getLoadingPlanSynchronizationMethod =
                LoadingPlanServiceGrpc.getLoadingPlanSynchronizationMethod)
                == null) {
          LoadingPlanServiceGrpc.getLoadingPlanSynchronizationMethod =
                  getLoadingPlanSynchronizationMethod =
                          io.grpc.MethodDescriptor
                                  .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                          .LoadingPlanSyncDetails,
                                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                                  .LoadingPlanSyncReply>
                                          newBuilder()
                                  .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                  .setFullMethodName(
                                          generateFullMethodName(SERVICE_NAME, "LoadingPlanSynchronization"))
                                  .setSampledToLocalTracing(true)
                                  .setRequestMarshaller(
                                          io.grpc.protobuf.ProtoUtils.marshaller(
                                                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                                          .LoadingPlanSyncDetails.getDefaultInstance()))
                                  .setResponseMarshaller(
                                          io.grpc.protobuf.ProtoUtils.marshaller(
                                                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                                          .LoadingPlanSyncReply.getDefaultInstance()))
                                  .setSchemaDescriptor(
                                          new LoadingPlanServiceMethodDescriptorSupplier(
                                                  "LoadingPlanSynchronization"))
                                  .build();
        }
      }
    }
    return getLoadingPlanSynchronizationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>
          getGetOrSaveRulesForLoadingPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
          fullMethodName = SERVICE_NAME + '/' + "GetOrSaveRulesForLoadingPlan",
          requestType =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest.class,
          responseType =
                  com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply.class,
          methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>
  getGetOrSaveRulesForLoadingPlanMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>
            getGetOrSaveRulesForLoadingPlanMethod;
    if ((getGetOrSaveRulesForLoadingPlanMethod =
            LoadingPlanServiceGrpc.getGetOrSaveRulesForLoadingPlanMethod)
            == null) {
      synchronized (LoadingPlanServiceGrpc.class) {
        if ((getGetOrSaveRulesForLoadingPlanMethod =
                LoadingPlanServiceGrpc.getGetOrSaveRulesForLoadingPlanMethod)
                == null) {
          LoadingPlanServiceGrpc.getGetOrSaveRulesForLoadingPlanMethod =
                  getGetOrSaveRulesForLoadingPlanMethod =
                          io.grpc.MethodDescriptor
                                  .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                          .LoadingPlanRuleRequest,
                                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                                  .LoadingPlanRuleReply>
                                          newBuilder()
                                  .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                  .setFullMethodName(
                                          generateFullMethodName(SERVICE_NAME, "GetOrSaveRulesForLoadingPlan"))
                                  .setSampledToLocalTracing(true)
                                  .setRequestMarshaller(
                                          io.grpc.protobuf.ProtoUtils.marshaller(
                                                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                                          .LoadingPlanRuleRequest.getDefaultInstance()))
                                  .setResponseMarshaller(
                                          io.grpc.protobuf.ProtoUtils.marshaller(
                                                  com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                                          .LoadingPlanRuleReply.getDefaultInstance()))
                                  .setSchemaDescriptor(
                                          new LoadingPlanServiceMethodDescriptorSupplier(
                                                  "GetOrSaveRulesForLoadingPlan"))
                                  .build();
        }
      }
    }
    return getGetOrSaveRulesForLoadingPlanMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static LoadingPlanServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceStub> factory =
            new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceStub>() {
              @java.lang.Override
              public LoadingPlanServiceStub newStub(
                      io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new LoadingPlanServiceStub(channel, callOptions);
              }
            };
    return LoadingPlanServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadingPlanServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceBlockingStub> factory =
            new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceBlockingStub>() {
              @java.lang.Override
              public LoadingPlanServiceBlockingStub newStub(
                      io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new LoadingPlanServiceBlockingStub(channel, callOptions);
              }
            };
    return LoadingPlanServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadingPlanServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceFutureStub> factory =
            new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceFutureStub>() {
              @java.lang.Override
              public LoadingPlanServiceFutureStub newStub(
                      io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                return new LoadingPlanServiceFutureStub(channel, callOptions);
              }
            };
    return LoadingPlanServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadingPlanServiceImplBase implements io.grpc.BindableService {

    /** */
    public void loadingPlanSynchronization(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails request,
            io.grpc.stub.StreamObserver<
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>
                    responseObserver) {
      asyncUnimplementedUnaryCall(getLoadingPlanSynchronizationMethod(), responseObserver);
    }

    /** */
    public void getOrSaveRulesForLoadingPlan(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest request,
            io.grpc.stub.StreamObserver<
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>
                    responseObserver) {
      asyncUnimplementedUnaryCall(getGetOrSaveRulesForLoadingPlanMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
              .addMethod(
                      getLoadingPlanSynchronizationMethod(),
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                              .LoadingPlanSyncDetails,
                                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                              .LoadingPlanSyncReply>(this, METHODID_LOADING_PLAN_SYNCHRONIZATION)))
              .addMethod(
                      getGetOrSaveRulesForLoadingPlanMethod(),
                      asyncUnaryCall(
                              new MethodHandlers<
                                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                              .LoadingPlanRuleRequest,
                                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                              .LoadingPlanRuleReply>(
                                      this, METHODID_GET_OR_SAVE_RULES_FOR_LOADING_PLAN)))
              .build();
    }
  }

  /** */
  public static final class LoadingPlanServiceStub
          extends io.grpc.stub.AbstractAsyncStub<LoadingPlanServiceStub> {
    private LoadingPlanServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceStub build(
            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceStub(channel, callOptions);
    }

    /** */
    public void loadingPlanSynchronization(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails request,
            io.grpc.stub.StreamObserver<
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>
                    responseObserver) {
      asyncUnaryCall(
              getChannel().newCall(getLoadingPlanSynchronizationMethod(), getCallOptions()),
              request,
              responseObserver);
    }

    /** */
    public void getOrSaveRulesForLoadingPlan(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest request,
            io.grpc.stub.StreamObserver<
                    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>
                    responseObserver) {
      asyncUnaryCall(
              getChannel().newCall(getGetOrSaveRulesForLoadingPlanMethod(), getCallOptions()),
              request,
              responseObserver);
    }
  }

  /** */
  public static final class LoadingPlanServiceBlockingStub
          extends io.grpc.stub.AbstractBlockingStub<LoadingPlanServiceBlockingStub> {
    private LoadingPlanServiceBlockingStub(
            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceBlockingStub build(
            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply
    loadingPlanSynchronization(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                    request) {
      return blockingUnaryCall(
              getChannel(), getLoadingPlanSynchronizationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply
    getOrSaveRulesForLoadingPlan(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest
                    request) {
      return blockingUnaryCall(
              getChannel(), getGetOrSaveRulesForLoadingPlanMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class LoadingPlanServiceFutureStub
          extends io.grpc.stub.AbstractFutureStub<LoadingPlanServiceFutureStub> {
    private LoadingPlanServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceFutureStub build(
            io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply>
    loadingPlanSynchronization(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails
                    request) {
      return futureUnaryCall(
              getChannel().newCall(getLoadingPlanSynchronizationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleReply>
    getOrSaveRulesForLoadingPlan(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest
                    request) {
      return futureUnaryCall(
              getChannel().newCall(getGetOrSaveRulesForLoadingPlanMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOADING_PLAN_SYNCHRONIZATION = 0;
  private static final int METHODID_GET_OR_SAVE_RULES_FOR_LOADING_PLAN = 1;

  private static final class MethodHandlers<Req, Resp>
          implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
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
          serviceImpl.loadingPlanSynchronization(
                  (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails)
                          request,
                  (io.grpc.stub.StreamObserver<
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingPlanSyncReply>)
                          responseObserver);
          break;
        case METHODID_GET_OR_SAVE_RULES_FOR_LOADING_PLAN:
          serviceImpl.getOrSaveRulesForLoadingPlan(
                  (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanRuleRequest)
                          request,
                  (io.grpc.stub.StreamObserver<
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingPlanRuleReply>)
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

  private abstract static class LoadingPlanServiceBaseDescriptorSupplier
          implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
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
          serviceDescriptor =
                  result =
                          io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                                  .setSchemaDescriptor(new LoadingPlanServiceFileDescriptorSupplier())
                                  .addMethod(getLoadingPlanSynchronizationMethod())
                                  .addMethod(getGetOrSaveRulesForLoadingPlanMethod())
                                  .build();
        }
      }
    }
    return result;
  }
}