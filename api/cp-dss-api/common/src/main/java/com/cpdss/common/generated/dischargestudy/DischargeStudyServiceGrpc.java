/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.dischargestudy;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: dischargestudy/discharge_study_service.proto")
public final class DischargeStudyServiceGrpc {

  private DischargeStudyServiceGrpc() {}

  public static final String SERVICE_NAME = "DischargeStudyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply>
      getGetDischargeStudyByVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getDischargeStudyByVoyage",
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
      getGetDischargeStudyByVoyageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalReply>
        getGetDischargeStudyByVoyageMethod;
    if ((getGetDischargeStudyByVoyageMethod =
            DischargeStudyServiceGrpc.getGetDischargeStudyByVoyageMethod)
        == null) {
      synchronized (DischargeStudyServiceGrpc.class) {
        if ((getGetDischargeStudyByVoyageMethod =
                DischargeStudyServiceGrpc.getGetDischargeStudyByVoyageMethod)
            == null) {
          DischargeStudyServiceGrpc.getGetDischargeStudyByVoyageMethod =
              getGetDischargeStudyByVoyageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationSynopticalRequest,
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationSynopticalReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getDischargeStudyByVoyage"))
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
                          new DischargeStudyServiceMethodDescriptorSupplier(
                              "getDischargeStudyByVoyage"))
                      .build();
        }
      }
    }
    return getGetDischargeStudyByVoyageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply>
      getGetDischargeStudyCargoByVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getDischargeStudyCargoByVoyage",
      requestType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest.class,
      responseType =
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
          com.cpdss.common.generated.loading_plan.LoadingPlanModels
              .LoadingInformationSynopticalReply>
      getGetDischargeStudyCargoByVoyageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest,
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalReply>
        getGetDischargeStudyCargoByVoyageMethod;
    if ((getGetDischargeStudyCargoByVoyageMethod =
            DischargeStudyServiceGrpc.getGetDischargeStudyCargoByVoyageMethod)
        == null) {
      synchronized (DischargeStudyServiceGrpc.class) {
        if ((getGetDischargeStudyCargoByVoyageMethod =
                DischargeStudyServiceGrpc.getGetDischargeStudyCargoByVoyageMethod)
            == null) {
          DischargeStudyServiceGrpc.getGetDischargeStudyCargoByVoyageMethod =
              getGetDischargeStudyCargoByVoyageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationRequest,
                          com.cpdss.common.generated.loading_plan.LoadingPlanModels
                              .LoadingInformationSynopticalReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getDischargeStudyCargoByVoyage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.loading_plan.LoadingPlanModels
                                  .LoadingInformationSynopticalReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new DischargeStudyServiceMethodDescriptorSupplier(
                              "getDischargeStudyCargoByVoyage"))
                      .build();
        }
      }
    }
    return getGetDischargeStudyCargoByVoyageMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static DischargeStudyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeStudyServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeStudyServiceStub>() {
          @java.lang.Override
          public DischargeStudyServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeStudyServiceStub(channel, callOptions);
          }
        };
    return DischargeStudyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DischargeStudyServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeStudyServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeStudyServiceBlockingStub>() {
          @java.lang.Override
          public DischargeStudyServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeStudyServiceBlockingStub(channel, callOptions);
          }
        };
    return DischargeStudyServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static DischargeStudyServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DischargeStudyServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<DischargeStudyServiceFutureStub>() {
          @java.lang.Override
          public DischargeStudyServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new DischargeStudyServiceFutureStub(channel, callOptions);
          }
        };
    return DischargeStudyServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class DischargeStudyServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getDischargeStudyByVoyage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalRequest
            request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetDischargeStudyByVoyageMethod(), responseObserver);
    }

    /** */
    public void getDischargeStudyCargoByVoyage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetDischargeStudyCargoByVoyageMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetDischargeStudyByVoyageMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalRequest,
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalReply>(
                      this, METHODID_GET_DISCHARGE_STUDY_BY_VOYAGE)))
          .addMethod(
              getGetDischargeStudyCargoByVoyageMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationRequest,
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalReply>(
                      this, METHODID_GET_DISCHARGE_STUDY_CARGO_BY_VOYAGE)))
          .build();
    }
  }

  /** */
  public static final class DischargeStudyServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DischargeStudyServiceStub> {
    private DischargeStudyServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeStudyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeStudyServiceStub(channel, callOptions);
    }

    /** */
    public void getDischargeStudyByVoyage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalRequest
            request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDischargeStudyByVoyageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getDischargeStudyCargoByVoyage(
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDischargeStudyCargoByVoyageMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class DischargeStudyServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DischargeStudyServiceBlockingStub> {
    private DischargeStudyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeStudyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeStudyServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingInformationSynopticalReply
        getDischargeStudyByVoyage(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetDischargeStudyByVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.loading_plan.LoadingPlanModels
            .LoadingInformationSynopticalReply
        getDischargeStudyCargoByVoyage(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetDischargeStudyCargoByVoyageMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class DischargeStudyServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DischargeStudyServiceFutureStub> {
    private DischargeStudyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DischargeStudyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DischargeStudyServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalReply>
        getDischargeStudyByVoyage(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                    .LoadingInformationSynopticalRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDischargeStudyByVoyageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.loading_plan.LoadingPlanModels
                .LoadingInformationSynopticalReply>
        getDischargeStudyCargoByVoyage(
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDischargeStudyCargoByVoyageMethod(), getCallOptions()),
          request);
    }
  }

  private static final int METHODID_GET_DISCHARGE_STUDY_BY_VOYAGE = 0;
  private static final int METHODID_GET_DISCHARGE_STUDY_CARGO_BY_VOYAGE = 1;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DischargeStudyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DischargeStudyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DISCHARGE_STUDY_BY_VOYAGE:
          serviceImpl.getDischargeStudyByVoyage(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels
                      .LoadingInformationSynopticalRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalReply>)
                  responseObserver);
          break;
        case METHODID_GET_DISCHARGE_STUDY_CARGO_BY_VOYAGE:
          serviceImpl.getDischargeStudyCargoByVoyage(
              (com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.loading_plan.LoadingPlanModels
                          .LoadingInformationSynopticalReply>)
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

  private abstract static class DischargeStudyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DischargeStudyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.dischargestudy.DischargeStudyServiceOuterClass
          .getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DischargeStudyService");
    }
  }

  private static final class DischargeStudyServiceFileDescriptorSupplier
      extends DischargeStudyServiceBaseDescriptorSupplier {
    DischargeStudyServiceFileDescriptorSupplier() {}
  }

  private static final class DischargeStudyServiceMethodDescriptorSupplier
      extends DischargeStudyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DischargeStudyServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DischargeStudyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new DischargeStudyServiceFileDescriptorSupplier())
                      .addMethod(getGetDischargeStudyByVoyageMethod())
                      .addMethod(getGetDischargeStudyCargoByVoyageMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
