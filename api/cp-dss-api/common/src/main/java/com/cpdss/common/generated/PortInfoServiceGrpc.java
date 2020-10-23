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
    comments = "Source: port_info.proto")
public final class PortInfoServiceGrpc {

  private PortInfoServiceGrpc() {}

  public static final String SERVICE_NAME = "PortInfoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfo",
      requestType = com.cpdss.common.generated.PortInfo.PortRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.PortReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.PortRequest,
            com.cpdss.common.generated.PortInfo.PortReply>
        getGetPortInfoMethod;
    if ((getGetPortInfoMethod = PortInfoServiceGrpc.getGetPortInfoMethod) == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoMethod = PortInfoServiceGrpc.getGetPortInfoMethod) == null) {
          PortInfoServiceGrpc.getGetPortInfoMethod =
              getGetPortInfoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.PortRequest,
                          com.cpdss.common.generated.PortInfo.PortReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPortInfo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfo"))
                      .build();
        }
      }
    }
    return getGetPortInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
      getGetPortInfoByCargoIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfoByCargoId",
      requestType = com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
      getGetPortInfoByCargoIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
        getGetPortInfoByCargoIdMethod;
    if ((getGetPortInfoByCargoIdMethod = PortInfoServiceGrpc.getGetPortInfoByCargoIdMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoByCargoIdMethod = PortInfoServiceGrpc.getGetPortInfoByCargoIdMethod)
            == null) {
          PortInfoServiceGrpc.getGetPortInfoByCargoIdMethod =
              getGetPortInfoByCargoIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
                          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPortInfoByCargoId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfoByCargoId"))
                      .build();
        }
      }
    }
    return getGetPortInfoByCargoIdMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static PortInfoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceStub>() {
          @java.lang.Override
          public PortInfoServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new PortInfoServiceStub(channel, callOptions);
          }
        };
    return PortInfoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PortInfoServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceBlockingStub>() {
          @java.lang.Override
          public PortInfoServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new PortInfoServiceBlockingStub(channel, callOptions);
          }
        };
    return PortInfoServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static PortInfoServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceFutureStub>() {
          @java.lang.Override
          public PortInfoServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new PortInfoServiceFutureStub(channel, callOptions);
          }
        };
    return PortInfoServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class PortInfoServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getPortInfo(
        com.cpdss.common.generated.PortInfo.PortRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoMethod(), responseObserver);
    }

    /** */
    public void getPortInfoByCargoId(
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoByCargoIdMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetPortInfoMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.PortRequest,
                      com.cpdss.common.generated.PortInfo.PortReply>(this, METHODID_GET_PORT_INFO)))
          .addMethod(
              getGetPortInfoByCargoIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
                      com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>(
                      this, METHODID_GET_PORT_INFO_BY_CARGO_ID)))
          .build();
    }
  }

  /** */
  public static final class PortInfoServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PortInfoServiceStub> {
    private PortInfoServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInfoServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInfoServiceStub(channel, callOptions);
    }

    /** */
    public void getPortInfo(
        com.cpdss.common.generated.PortInfo.PortRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortInfoByCargoId(
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoByCargoIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class PortInfoServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PortInfoServiceBlockingStub> {
    private PortInfoServiceBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInfoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInfoServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.PortReply getPortInfo(
        com.cpdss.common.generated.PortInfo.PortRequest request) {
      return blockingUnaryCall(getChannel(), getGetPortInfoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply getPortInfoByCargoId(
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPortInfoByCargoIdMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class PortInfoServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PortInfoServiceFutureStub> {
    private PortInfoServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInfoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInfoServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.PortReply>
        getPortInfo(com.cpdss.common.generated.PortInfo.PortRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
        getPortInfoByCargoId(
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoByCargoIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PORT_INFO = 0;
  private static final int METHODID_GET_PORT_INFO_BY_CARGO_ID = 1;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PortInfoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PortInfoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PORT_INFO:
          serviceImpl.getPortInfo(
              (com.cpdss.common.generated.PortInfo.PortRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>)
                  responseObserver);
          break;
        case METHODID_GET_PORT_INFO_BY_CARGO_ID:
          serviceImpl.getPortInfoByCargoId(
              (com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>)
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

  private abstract static class PortInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PortInfoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.PortInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PortInfoService");
    }
  }

  private static final class PortInfoServiceFileDescriptorSupplier
      extends PortInfoServiceBaseDescriptorSupplier {
    PortInfoServiceFileDescriptorSupplier() {}
  }

  private static final class PortInfoServiceMethodDescriptorSupplier
      extends PortInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PortInfoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PortInfoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new PortInfoServiceFileDescriptorSupplier())
                      .addMethod(getGetPortInfoMethod())
                      .addMethod(getGetPortInfoByCargoIdMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
