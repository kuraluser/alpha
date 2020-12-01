/* Licensed under Apache-2.0 */
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
    comments = "Source: vessel_info.proto")
public final class VesselInfoServiceGrpc {

  private VesselInfoServiceGrpc() {}

  public static final String SERVICE_NAME = "VesselInfoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetAllVesselsByCompanyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllVesselsByCompany",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetAllVesselsByCompanyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetAllVesselsByCompanyMethod;
    if ((getGetAllVesselsByCompanyMethod = VesselInfoServiceGrpc.getGetAllVesselsByCompanyMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetAllVesselsByCompanyMethod =
                VesselInfoServiceGrpc.getGetAllVesselsByCompanyMethod)
            == null) {
          VesselInfoServiceGrpc.getGetAllVesselsByCompanyMethod =
              getGetAllVesselsByCompanyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetAllVesselsByCompany"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetAllVesselsByCompany"))
                      .build();
        }
      }
    }
    return getGetAllVesselsByCompanyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailsByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselDetailsById",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailsByIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselDetailsByIdMethod;
    if ((getGetVesselDetailsByIdMethod = VesselInfoServiceGrpc.getGetVesselDetailsByIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselDetailsByIdMethod = VesselInfoServiceGrpc.getGetVesselDetailsByIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselDetailsByIdMethod =
              getGetVesselDetailsByIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselDetailsById"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselDetailsById"))
                      .build();
        }
      }
    }
    return getGetVesselDetailsByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselFuelTanksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselFuelTanks",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselFuelTanksMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselFuelTanksMethod;
    if ((getGetVesselFuelTanksMethod = VesselInfoServiceGrpc.getGetVesselFuelTanksMethod) == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselFuelTanksMethod = VesselInfoServiceGrpc.getGetVesselFuelTanksMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselFuelTanksMethod =
              getGetVesselFuelTanksMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVesselFuelTanks"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselFuelTanks"))
                      .build();
        }
      }
    }
    return getGetVesselFuelTanksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselCargoTanksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselCargoTanks",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselCargoTanksMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselCargoTanksMethod;
    if ((getGetVesselCargoTanksMethod = VesselInfoServiceGrpc.getGetVesselCargoTanksMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselCargoTanksMethod = VesselInfoServiceGrpc.getGetVesselCargoTanksMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselCargoTanksMethod =
              getGetVesselCargoTanksMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselCargoTanks"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselCargoTanks"))
                      .build();
        }
      }
    }
    return getGetVesselCargoTanksMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static VesselInfoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceStub>() {
          @java.lang.Override
          public VesselInfoServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VesselInfoServiceStub(channel, callOptions);
          }
        };
    return VesselInfoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VesselInfoServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceBlockingStub>() {
          @java.lang.Override
          public VesselInfoServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VesselInfoServiceBlockingStub(channel, callOptions);
          }
        };
    return VesselInfoServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static VesselInfoServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceFutureStub>() {
          @java.lang.Override
          public VesselInfoServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VesselInfoServiceFutureStub(channel, callOptions);
          }
        };
    return VesselInfoServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class VesselInfoServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getAllVesselsByCompany(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllVesselsByCompanyMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselDetailsByIdMethod(), responseObserver);
    }

    /** */
    public void getVesselFuelTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselFuelTanksMethod(), responseObserver);
    }

    /** */
    public void getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselCargoTanksMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetAllVesselsByCompanyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_ALL_VESSELS_BY_COMPANY)))
          .addMethod(
              getGetVesselDetailsByIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAILS_BY_ID)))
          .addMethod(
              getGetVesselFuelTanksMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_FUEL_TANKS)))
          .addMethod(
              getGetVesselCargoTanksMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_CARGO_TANKS)))
          .build();
    }
  }

  /** */
  public static final class VesselInfoServiceStub
      extends io.grpc.stub.AbstractAsyncStub<VesselInfoServiceStub> {
    private VesselInfoServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VesselInfoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VesselInfoServiceStub(channel, callOptions);
    }

    /** */
    public void getAllVesselsByCompany(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAllVesselsByCompanyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailsByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselFuelTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselFuelTanksMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselCargoTanksMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class VesselInfoServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VesselInfoServiceBlockingStub> {
    private VesselInfoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VesselInfoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VesselInfoServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getAllVesselsByCompany(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAllVesselsByCompanyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselDetailsByIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselFuelTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselFuelTanksMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselCargoTanksMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class VesselInfoServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<VesselInfoServiceFutureStub> {
    private VesselInfoServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VesselInfoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VesselInfoServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getAllVesselsByCompany(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAllVesselsByCompanyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailsById(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselDetailsByIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselFuelTanks(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselFuelTanksMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselCargoTanks(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselCargoTanksMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_VESSELS_BY_COMPANY = 0;
  private static final int METHODID_GET_VESSEL_DETAILS_BY_ID = 1;
  private static final int METHODID_GET_VESSEL_FUEL_TANKS = 2;
  private static final int METHODID_GET_VESSEL_CARGO_TANKS = 3;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VesselInfoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VesselInfoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_VESSELS_BY_COMPANY:
          serviceImpl.getAllVesselsByCompany(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_DETAILS_BY_ID:
          serviceImpl.getVesselDetailsById(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_FUEL_TANKS:
          serviceImpl.getVesselFuelTanks(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_CARGO_TANKS:
          serviceImpl.getVesselCargoTanks(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
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

  private abstract static class VesselInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VesselInfoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.VesselInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VesselInfoService");
    }
  }

  private static final class VesselInfoServiceFileDescriptorSupplier
      extends VesselInfoServiceBaseDescriptorSupplier {
    VesselInfoServiceFileDescriptorSupplier() {}
  }

  private static final class VesselInfoServiceMethodDescriptorSupplier
      extends VesselInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VesselInfoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (VesselInfoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new VesselInfoServiceFileDescriptorSupplier())
                      .addMethod(getGetAllVesselsByCompanyMethod())
                      .addMethod(getGetVesselDetailsByIdMethod())
                      .addMethod(getGetVesselFuelTanksMethod())
                      .addMethod(getGetVesselCargoTanksMethod())
                      .build();
        }
      }
    }
    return result;
  }
}