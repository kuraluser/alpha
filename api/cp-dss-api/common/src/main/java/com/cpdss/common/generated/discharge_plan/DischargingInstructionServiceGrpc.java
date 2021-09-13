/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: discharge_plan/discharge_plan_service.proto")
public final class DischargingInstructionServiceGrpc {

  private DischargingInstructionServiceGrpc() {}

  public static final String SERVICE_NAME = "DischargingInstructionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest,
          com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
      getGetDischargingInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDischargingInstructions",
      requestType = com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest.class,
      responseType = com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest,
          com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
      getGetDischargingInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest,
            com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
        getGetDischargingInstructionsMethod;
    if ((getGetDischargingInstructionsMethod =
            DischargingInstructionServiceGrpc.getGetDischargingInstructionsMethod)
        == null) {
      synchronized (DischargingInstructionServiceGrpc.class) {
        if ((getGetDischargingInstructionsMethod =
                DischargingInstructionServiceGrpc.getGetDischargingInstructionsMethod)
            == null) {
          DischargingInstructionServiceGrpc.getGetDischargingInstructionsMethod =
              getGetDischargingInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest,
                          com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetDischargingInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan
                                  .DischargingInstructionRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan
                                  .DischargingInstructionDetails.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargingInstructionServiceMethodDescriptorSupplier(
                              "GetDischargingInstructions"))
                      .build();
        }
      }
    }
    return getGetDischargingInstructionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave,
          com.cpdss.common.generated.Common.ResponseStatus>
      getAddDischargingInstructionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddDischargingInstruction",
      requestType = com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave,
          com.cpdss.common.generated.Common.ResponseStatus>
      getAddDischargingInstructionMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave,
            com.cpdss.common.generated.Common.ResponseStatus>
        getAddDischargingInstructionMethod;
    if ((getAddDischargingInstructionMethod =
            DischargingInstructionServiceGrpc.getAddDischargingInstructionMethod)
        == null) {
      synchronized (DischargingInstructionServiceGrpc.class) {
        if ((getAddDischargingInstructionMethod =
                DischargingInstructionServiceGrpc.getAddDischargingInstructionMethod)
            == null) {
          DischargingInstructionServiceGrpc.getAddDischargingInstructionMethod =
              getAddDischargingInstructionMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "AddDischargingInstruction"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargingInstructionServiceMethodDescriptorSupplier(
                              "AddDischargingInstruction"))
                      .build();
        }
      }
    }
    return getAddDischargingInstructionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateDischargingInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateDischargingInstructions",
      requestType = com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateDischargingInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate,
            com.cpdss.common.generated.Common.ResponseStatus>
        getUpdateDischargingInstructionsMethod;
    if ((getUpdateDischargingInstructionsMethod =
            DischargingInstructionServiceGrpc.getUpdateDischargingInstructionsMethod)
        == null) {
      synchronized (DischargingInstructionServiceGrpc.class) {
        if ((getUpdateDischargingInstructionsMethod =
                DischargingInstructionServiceGrpc.getUpdateDischargingInstructionsMethod)
            == null) {
          DischargingInstructionServiceGrpc.getUpdateDischargingInstructionsMethod =
              getUpdateDischargingInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "UpdateDischargingInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan
                                  .DischargingInstructionsUpdate.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargingInstructionServiceMethodDescriptorSupplier(
                              "UpdateDischargingInstructions"))
                      .build();
        }
      }
    }
    return getUpdateDischargingInstructionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getDeleteDischargingInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteDischargingInstructions",
      requestType = com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getDeleteDischargingInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
            com.cpdss.common.generated.Common.ResponseStatus>
        getDeleteDischargingInstructionsMethod;
    if ((getDeleteDischargingInstructionsMethod =
            DischargingInstructionServiceGrpc.getDeleteDischargingInstructionsMethod)
        == null) {
      synchronized (DischargingInstructionServiceGrpc.class) {
        if ((getDeleteDischargingInstructionsMethod =
                DischargingInstructionServiceGrpc.getDeleteDischargingInstructionsMethod)
            == null) {
          DischargingInstructionServiceGrpc.getDeleteDischargingInstructionsMethod =
              getDeleteDischargingInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "DeleteDischargingInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargingInstructionServiceMethodDescriptorSupplier(
                              "DeleteDischargingInstructions"))
                      .build();
        }
      }
    }
    return getDeleteDischargingInstructionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getEditInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EditInstructions",
      requestType = com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
          com.cpdss.common.generated.Common.ResponseStatus>
      getEditInstructionsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
            com.cpdss.common.generated.Common.ResponseStatus>
        getEditInstructionsMethod;
    if ((getEditInstructionsMethod = DischargingInstructionServiceGrpc.getEditInstructionsMethod)
        == null) {
      synchronized (DischargingInstructionServiceGrpc.class) {
        if ((getEditInstructionsMethod =
                DischargingInstructionServiceGrpc.getEditInstructionsMethod)
            == null) {
          DischargingInstructionServiceGrpc.getEditInstructionsMethod =
              getEditInstructionsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EditInstructions"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargingInstructionServiceMethodDescriptorSupplier(
                              "EditInstructions"))
                      .build();
        }
      }
    }
    return getEditInstructionsMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static DischargingInstructionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargingInstructionServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargingInstructionServiceStub>() {
          @java.lang.Override
          public DischargingInstructionServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargingInstructionServiceStub(channel, callOptions);
          }
        };
    return DischargingInstructionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DischargingInstructionServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargingInstructionServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargingInstructionServiceBlockingStub>() {
          @java.lang.Override
          public DischargingInstructionServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargingInstructionServiceBlockingStub(channel, callOptions);
          }
        };
    return DischargingInstructionServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static DischargingInstructionServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargingInstructionServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargingInstructionServiceFutureStub>() {
          @java.lang.Override
          public DischargingInstructionServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargingInstructionServiceFutureStub(channel, callOptions);
          }
        };
    return DischargingInstructionServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class DischargingInstructionServiceImplBase
      implements io.grpc.BindableService {

    /** */
    public void getDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetDischargingInstructionsMethod(), responseObserver);
    }

    /** */
    public void addDischargingInstruction(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getAddDischargingInstructionMethod(), responseObserver);
    }

    /** */
    public void updateDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getUpdateDischargingInstructionsMethod(), responseObserver);
    }

    /** */
    public void deleteDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getDeleteDischargingInstructionsMethod(), responseObserver);
    }

    /** */
    public void editInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getEditInstructionsMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetDischargingInstructionsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest,
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>(
                      this, METHODID_GET_DISCHARGING_INSTRUCTIONS)))
          .addMethod(
              getAddDischargingInstructionMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_ADD_DISCHARGING_INSTRUCTION)))
          .addMethod(
              getUpdateDischargingInstructionsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_UPDATE_DISCHARGING_INSTRUCTIONS)))
          .addMethod(
              getDeleteDischargingInstructionsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_DELETE_DISCHARGING_INSTRUCTIONS)))
          .addMethod(
              getEditInstructionsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_EDIT_INSTRUCTIONS)))
          .build();
    }
  }

  /** */
  public static final class DischargingInstructionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DischargingInstructionServiceStub> {
    private DischargingInstructionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargingInstructionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargingInstructionServiceStub(channel, callOptions);
    }

    /** */
    public void getDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDischargingInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void addDischargingInstruction(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddDischargingInstructionMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void updateDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateDischargingInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deleteDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteDischargingInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void editInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEditInstructionsMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class DischargingInstructionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DischargingInstructionServiceBlockingStub> {
    private DischargingInstructionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargingInstructionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargingInstructionServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails
        getDischargingInstructions(
            com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDischargingInstructionsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus addDischargingInstruction(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddDischargingInstructionMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus updateDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateDischargingInstructionsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus deleteDischargingInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteDischargingInstructionsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus editInstructions(
        com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEditInstructionsMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class DischargingInstructionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DischargingInstructionServiceFutureStub> {
    private DischargingInstructionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargingInstructionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargingInstructionServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>
        getDischargingInstructions(
            com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDischargingInstructionsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        addDischargingInstruction(
            com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddDischargingInstructionMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        updateDischargingInstructions(
            com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateDischargingInstructionsMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        deleteDischargingInstructions(
            com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteDischargingInstructionsMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        editInstructions(
            com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEditInstructionsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DISCHARGING_INSTRUCTIONS = 0;
  private static final int METHODID_ADD_DISCHARGING_INSTRUCTION = 1;
  private static final int METHODID_UPDATE_DISCHARGING_INSTRUCTIONS = 2;
  private static final int METHODID_DELETE_DISCHARGING_INSTRUCTIONS = 3;
  private static final int METHODID_EDIT_INSTRUCTIONS = 4;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DischargingInstructionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DischargingInstructionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DISCHARGING_INSTRUCTIONS:
          serviceImpl.getDischargingInstructions(
              (com.cpdss.common.generated.discharge_plan.DischargingInstructionRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.discharge_plan.DischargingInstructionDetails>)
                  responseObserver);
          break;
        case METHODID_ADD_DISCHARGING_INSTRUCTION:
          serviceImpl.addDischargingInstruction(
              (com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_UPDATE_DISCHARGING_INSTRUCTIONS:
          serviceImpl.updateDischargingInstructions(
              (com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_DELETE_DISCHARGING_INSTRUCTIONS:
          serviceImpl.deleteDischargingInstructions(
              (com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_EDIT_INSTRUCTIONS:
          serviceImpl.editInstructions(
              (com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus) request,
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

  private abstract static class DischargingInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DischargingInstructionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanServiceOuterClass
          .getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DischargingInstructionService");
    }
  }

  private static final class DischargingInstructionServiceFileDescriptorSupplier
      extends DischargingInstructionServiceBaseDescriptorSupplier {
    DischargingInstructionServiceFileDescriptorSupplier() {}
  }

  private static final class DischargingInstructionServiceMethodDescriptorSupplier
      extends DischargingInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DischargingInstructionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DischargingInstructionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(
                          new DischargingInstructionServiceFileDescriptorSupplier())
                      .addMethod(getGetDischargingInstructionsMethod())
                      .addMethod(getAddDischargingInstructionMethod())
                      .addMethod(getUpdateDischargingInstructionsMethod())
                      .addMethod(getDeleteDischargingInstructionsMethod())
                      .addMethod(getEditInstructionsMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
