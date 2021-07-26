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
public final class LoadingInstructionServiceGrpc {

  private LoadingInstructionServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadingInstructionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails>
      getGetLoadingInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingInstructions",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest.class,
      responseType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails>
      getGetLoadingInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails>
        getGetLoadingInstructionsMethod;
    if ((getGetLoadingInstructionsMethod =
            LoadingInstructionServiceGrpc.getGetLoadingInstructionsMethod)
        == null) {
      synchronized (LoadingInstructionServiceGrpc.class) {
        if ((getGetLoadingInstructionsMethod =
                LoadingInstructionServiceGrpc.getGetLoadingInstructionsMethod)
            == null) {
          LoadingInstructionServiceGrpc.getGetLoadingInstructionsMethod =
              getGetLoadingInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInstructionRequest,
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInstructionDetails>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadingInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInstructionRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInstructionDetails.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInstructionServiceMethodDescriptorSupplier(
                              "GetLoadingInstructions"))
                      .build();
        }
      }
    }
    return getGetLoadingInstructionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave,
          com.cpdss.common.generated.Common.ResponseStatus>
      getAddLoadingInstructionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddLoadingInstruction",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave,
          com.cpdss.common.generated.Common.ResponseStatus>
      getAddLoadingInstructionMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave,
            com.cpdss.common.generated.Common.ResponseStatus>
        getAddLoadingInstructionMethod;
    if ((getAddLoadingInstructionMethod =
            LoadingInstructionServiceGrpc.getAddLoadingInstructionMethod)
        == null) {
      synchronized (LoadingInstructionServiceGrpc.class) {
        if ((getAddLoadingInstructionMethod =
                LoadingInstructionServiceGrpc.getAddLoadingInstructionMethod)
            == null) {
          LoadingInstructionServiceGrpc.getAddLoadingInstructionMethod =
              getAddLoadingInstructionMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInstructionsSave,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "AddLoadingInstruction"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInstructionsSave.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInstructionServiceMethodDescriptorSupplier(
                              "AddLoadingInstruction"))
                      .build();
        }
      }
    }
    return getAddLoadingInstructionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateLoadingInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateLoadingInstructions",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateLoadingInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate,
            com.cpdss.common.generated.Common.ResponseStatus>
        getUpdateLoadingInstructionsMethod;
    if ((getUpdateLoadingInstructionsMethod =
            LoadingInstructionServiceGrpc.getUpdateLoadingInstructionsMethod)
        == null) {
      synchronized (LoadingInstructionServiceGrpc.class) {
        if ((getUpdateLoadingInstructionsMethod =
                LoadingInstructionServiceGrpc.getUpdateLoadingInstructionsMethod)
            == null) {
          LoadingInstructionServiceGrpc.getUpdateLoadingInstructionsMethod =
              getUpdateLoadingInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInstructionsUpdate,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "UpdateLoadingInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInstructionsUpdate.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInstructionServiceMethodDescriptorSupplier(
                              "UpdateLoadingInstructions"))
                      .build();
        }
      }
    }
    return getUpdateLoadingInstructionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getDeleteLoadingInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteLoadingInstructions",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getDeleteLoadingInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus,
            com.cpdss.common.generated.Common.ResponseStatus>
        getDeleteLoadingInstructionsMethod;
    if ((getDeleteLoadingInstructionsMethod =
            LoadingInstructionServiceGrpc.getDeleteLoadingInstructionsMethod)
        == null) {
      synchronized (LoadingInstructionServiceGrpc.class) {
        if ((getDeleteLoadingInstructionsMethod =
                LoadingInstructionServiceGrpc.getDeleteLoadingInstructionsMethod)
            == null) {
          LoadingInstructionServiceGrpc.getDeleteLoadingInstructionsMethod =
              getDeleteLoadingInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInstructionStatus,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "DeleteLoadingInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInstructionStatus.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInstructionServiceMethodDescriptorSupplier(
                              "DeleteLoadingInstructions"))
                      .build();
        }
      }
    }
    return getDeleteLoadingInstructionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getEditInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EditInstructions",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getEditInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus,
            com.cpdss.common.generated.Common.ResponseStatus>
        getEditInstructionsMethod;
    if ((getEditInstructionsMethod = LoadingInstructionServiceGrpc.getEditInstructionsMethod)
        == null) {
      synchronized (LoadingInstructionServiceGrpc.class) {
        if ((getEditInstructionsMethod = LoadingInstructionServiceGrpc.getEditInstructionsMethod)
            == null) {
          LoadingInstructionServiceGrpc.getEditInstructionsMethod =
              getEditInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInstructionStatus,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EditInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInstructionStatus.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadingInstructionServiceMethodDescriptorSupplier("EditInstructions"))
                      .build();
        }
      }
    }
    return getEditInstructionsMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static LoadingInstructionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceStub>() {
          @java.lang.Override
          public LoadingInstructionServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingInstructionServiceStub(channel, callOptions);
          }
        };
    return LoadingInstructionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadingInstructionServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceBlockingStub>() {
          @java.lang.Override
          public LoadingInstructionServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingInstructionServiceBlockingStub(channel, callOptions);
          }
        };
    return LoadingInstructionServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadingInstructionServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceFutureStub>() {
          @java.lang.Override
          public LoadingInstructionServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingInstructionServiceFutureStub(channel, callOptions);
          }
        };
    return LoadingInstructionServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadingInstructionServiceImplBase
      implements io.grpc.BindableService {

    /** */
    public void getLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadingInstructionsMethod(), responseObserver);
    }

    /** */
    public void addLoadingInstruction(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnimplementedUnaryCall(getAddLoadingInstructionMethod(), responseObserver);
    }

    /** */
    public void updateLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateLoadingInstructionsMethod(), responseObserver);
    }

    /** */
    public void deleteLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteLoadingInstructionsMethod(), responseObserver);
    }

    /** */
    public void editInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnimplementedUnaryCall(getEditInstructionsMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetLoadingInstructionsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionRequest,
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionDetails>(this, METHODID_GET_LOADING_INSTRUCTIONS)))
          .addMethod(
              getAddLoadingInstructionMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionsSave,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_ADD_LOADING_INSTRUCTION)))
          .addMethod(
              getUpdateLoadingInstructionsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionsUpdate,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_UPDATE_LOADING_INSTRUCTIONS)))
          .addMethod(
              getDeleteLoadingInstructionsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionStatus,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_DELETE_LOADING_INSTRUCTIONS)))
          .addMethod(
              getEditInstructionsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionStatus,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_EDIT_INSTRUCTIONS)))
          .build();
    }
  }

  /** */
  public static final class LoadingInstructionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LoadingInstructionServiceStub> {
    private LoadingInstructionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInstructionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInstructionServiceStub(channel, callOptions);
    }

    /** */
    public void getLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadingInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void addLoadingInstruction(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddLoadingInstructionMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void updateLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateLoadingInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deleteLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteLoadingInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void editInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getEditInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class LoadingInstructionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LoadingInstructionServiceBlockingStub> {
    private LoadingInstructionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInstructionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInstructionServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails
        getLoadingInstructions(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadingInstructionsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus addLoadingInstruction(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave request) {
      return blockingUnaryCall(
          getChannel(), getAddLoadingInstructionMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus updateLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate
            request) {
      return blockingUnaryCall(
          getChannel(), getUpdateLoadingInstructionsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus deleteLoadingInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus
            request) {
      return blockingUnaryCall(
          getChannel(), getDeleteLoadingInstructionsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus editInstructions(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus
            request) {
      return blockingUnaryCall(
          getChannel(), getEditInstructionsMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class LoadingInstructionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LoadingInstructionServiceFutureStub> {
    private LoadingInstructionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInstructionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInstructionServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionDetails>
        getLoadingInstructions(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadingInstructionsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        addLoadingInstruction(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave
                request) {
      return futureUnaryCall(
          getChannel().newCall(getAddLoadingInstructionMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        updateLoadingInstructions(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate
                request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateLoadingInstructionsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        deleteLoadingInstructions(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus
                request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteLoadingInstructionsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        editInstructions(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus
                request) {
      return futureUnaryCall(
          getChannel().newCall(getEditInstructionsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LOADING_INSTRUCTIONS = 0;
  private static final int METHODID_ADD_LOADING_INSTRUCTION = 1;
  private static final int METHODID_UPDATE_LOADING_INSTRUCTIONS = 2;
  private static final int METHODID_DELETE_LOADING_INSTRUCTIONS = 3;
  private static final int METHODID_EDIT_INSTRUCTIONS = 4;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadingInstructionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadingInstructionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LOADING_INSTRUCTIONS:
          serviceImpl.getLoadingInstructions(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInstructionDetails>)
                  responseObserver);
          break;
        case METHODID_ADD_LOADING_INSTRUCTION:
          serviceImpl.addLoadingInstruction(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave)
                  request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_UPDATE_LOADING_INSTRUCTIONS:
          serviceImpl.updateLoadingInstructions(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate)
                  request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_DELETE_LOADING_INSTRUCTIONS:
          serviceImpl.deleteLoadingInstructions(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus)
                  request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_EDIT_INSTRUCTIONS:
          serviceImpl.editInstructions(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus)
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

  private abstract static class LoadingInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadingInstructionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadingInstructionService");
    }
  }

  private static final class LoadingInstructionServiceFileDescriptorSupplier
      extends LoadingInstructionServiceBaseDescriptorSupplier {
    LoadingInstructionServiceFileDescriptorSupplier() {}
  }

  private static final class LoadingInstructionServiceMethodDescriptorSupplier
      extends LoadingInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadingInstructionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoadingInstructionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new LoadingInstructionServiceFileDescriptorSupplier())
                      .addMethod(getGetLoadingInstructionsMethod())
                      .addMethod(getAddLoadingInstructionMethod())
                      .addMethod(getUpdateLoadingInstructionsMethod())
                      .addMethod(getDeleteLoadingInstructionsMethod())
                      .addMethod(getEditInstructionsMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
