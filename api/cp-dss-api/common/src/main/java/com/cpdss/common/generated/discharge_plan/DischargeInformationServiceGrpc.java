/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.discharge_plan;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: discharge_plan/discharge_plan_service.proto")
public final class DischargeInformationServiceGrpc {

  private DischargeInformationServiceGrpc() {}

  public static final String SERVICE_NAME = "DischargeInformationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargeInformationRequest,
          com.cpdss.common.generated.discharge_plan.DischargeInformation>
      getGetDischargeInformationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getDischargeInformation",
      requestType = com.cpdss.common.generated.discharge_plan.DischargeInformationRequest.class,
      responseType = com.cpdss.common.generated.discharge_plan.DischargeInformation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargeInformationRequest,
          com.cpdss.common.generated.discharge_plan.DischargeInformation>
      getGetDischargeInformationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargeInformationRequest,
            com.cpdss.common.generated.discharge_plan.DischargeInformation>
        getGetDischargeInformationMethod;
    if ((getGetDischargeInformationMethod =
            DischargeInformationServiceGrpc.getGetDischargeInformationMethod)
        == null) {
      synchronized (DischargeInformationServiceGrpc.class) {
        if ((getGetDischargeInformationMethod =
                DischargeInformationServiceGrpc.getGetDischargeInformationMethod)
            == null) {
          DischargeInformationServiceGrpc.getGetDischargeInformationMethod =
              getGetDischargeInformationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargeInformationRequest,
                          com.cpdss.common.generated.discharge_plan.DischargeInformation>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getDischargeInformation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargeInformationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargeInformation
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeInformationServiceMethodDescriptorSupplier(
                              "getDischargeInformation"))
                      .build();
        }
      }
    }
    return getGetDischargeInformationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargeRuleRequest,
          com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
      getGetOrSaveRulesForDischargingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getOrSaveRulesForDischarging",
      requestType = com.cpdss.common.generated.discharge_plan.DischargeRuleRequest.class,
      responseType = com.cpdss.common.generated.discharge_plan.DischargeRuleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.discharge_plan.DischargeRuleRequest,
          com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
      getGetOrSaveRulesForDischargingMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.discharge_plan.DischargeRuleRequest,
            com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
        getGetOrSaveRulesForDischargingMethod;
    if ((getGetOrSaveRulesForDischargingMethod =
            DischargeInformationServiceGrpc.getGetOrSaveRulesForDischargingMethod)
        == null) {
      synchronized (DischargeInformationServiceGrpc.class) {
        if ((getGetOrSaveRulesForDischargingMethod =
                DischargeInformationServiceGrpc.getGetOrSaveRulesForDischargingMethod)
            == null) {
          DischargeInformationServiceGrpc.getGetOrSaveRulesForDischargingMethod =
              getGetOrSaveRulesForDischargingMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.discharge_plan.DischargeRuleRequest,
                          com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getOrSaveRulesForDischarging"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargeRuleRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.discharge_plan.DischargeRuleReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeInformationServiceMethodDescriptorSupplier(
                              "getOrSaveRulesForDischarging"))
                      .build();
        }
      }
    }
    return getGetOrSaveRulesForDischargingMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static DischargeInformationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeInformationServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeInformationServiceStub>() {
          @java.lang.Override
          public DischargeInformationServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeInformationServiceStub(channel, callOptions);
          }
        };
    return DischargeInformationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DischargeInformationServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeInformationServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeInformationServiceBlockingStub>() {
          @java.lang.Override
          public DischargeInformationServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeInformationServiceBlockingStub(channel, callOptions);
          }
        };
    return DischargeInformationServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static DischargeInformationServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeInformationServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeInformationServiceFutureStub>() {
          @java.lang.Override
          public DischargeInformationServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeInformationServiceFutureStub(channel, callOptions);
          }
        };
    return DischargeInformationServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class DischargeInformationServiceImplBase
      implements io.grpc.BindableService {

    /** */
    public void getDischargeInformation(
        com.cpdss.common.generated.discharge_plan.DischargeInformationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.discharge_plan.DischargeInformation>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetDischargeInformationMethod(), responseObserver);
    }

    /** */
    public void getOrSaveRulesForDischarging(
        com.cpdss.common.generated.discharge_plan.DischargeRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetOrSaveRulesForDischargingMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetDischargeInformationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargeInformationRequest,
                      com.cpdss.common.generated.discharge_plan.DischargeInformation>(
                      this, METHODID_GET_DISCHARGE_INFORMATION)))
          .addMethod(
              getGetOrSaveRulesForDischargingMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.discharge_plan.DischargeRuleRequest,
                      com.cpdss.common.generated.discharge_plan.DischargeRuleReply>(
                      this, METHODID_GET_OR_SAVE_RULES_FOR_DISCHARGING)))
          .build();
    }
  }

  /** */
  public static final class DischargeInformationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DischargeInformationServiceStub> {
    private DischargeInformationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeInformationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeInformationServiceStub(channel, callOptions);
    }

    /** */
    public void getDischargeInformation(
        com.cpdss.common.generated.discharge_plan.DischargeInformationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.discharge_plan.DischargeInformation>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDischargeInformationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOrSaveRulesForDischarging(
        com.cpdss.common.generated.discharge_plan.DischargeRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOrSaveRulesForDischargingMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class DischargeInformationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DischargeInformationServiceBlockingStub> {
    private DischargeInformationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeInformationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeInformationServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.discharge_plan.DischargeInformation getDischargeInformation(
        com.cpdss.common.generated.discharge_plan.DischargeInformationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetDischargeInformationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.discharge_plan.DischargeRuleReply
        getOrSaveRulesForDischarging(
            com.cpdss.common.generated.discharge_plan.DischargeRuleRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOrSaveRulesForDischargingMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class DischargeInformationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DischargeInformationServiceFutureStub> {
    private DischargeInformationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeInformationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeInformationServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.discharge_plan.DischargeInformation>
        getDischargeInformation(
            com.cpdss.common.generated.discharge_plan.DischargeInformationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDischargeInformationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.discharge_plan.DischargeRuleReply>
        getOrSaveRulesForDischarging(
            com.cpdss.common.generated.discharge_plan.DischargeRuleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOrSaveRulesForDischargingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DISCHARGE_INFORMATION = 0;
  private static final int METHODID_GET_OR_SAVE_RULES_FOR_DISCHARGING = 1;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DischargeInformationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DischargeInformationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DISCHARGE_INFORMATION:
          serviceImpl.getDischargeInformation(
              (com.cpdss.common.generated.discharge_plan.DischargeInformationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.discharge_plan.DischargeInformation>)
                  responseObserver);
          break;
        case METHODID_GET_OR_SAVE_RULES_FOR_DISCHARGING:
          serviceImpl.getOrSaveRulesForDischarging(
              (com.cpdss.common.generated.discharge_plan.DischargeRuleRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.discharge_plan.DischargeRuleReply>)
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

  private abstract static class DischargeInformationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DischargeInformationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.discharge_plan.DischargePlanService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DischargeInformationService");
    }
  }

  private static final class DischargeInformationServiceFileDescriptorSupplier
      extends DischargeInformationServiceBaseDescriptorSupplier {
    DischargeInformationServiceFileDescriptorSupplier() {}
  }

  private static final class DischargeInformationServiceMethodDescriptorSupplier
      extends DischargeInformationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DischargeInformationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DischargeInformationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new DischargeInformationServiceFileDescriptorSupplier())
                      .addMethod(getGetDischargeInformationMethod())
                      .addMethod(getGetOrSaveRulesForDischargingMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
