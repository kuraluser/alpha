/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: envoy_reader.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EnvoyReaderServiceGrpc {

  private EnvoyReaderServiceGrpc() {}

  public static final String SERVICE_NAME = "EnvoyReaderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
      getGetResultFromCommServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getResultFromCommServer",
      requestType = com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest.class,
      responseType = com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
      getGetResultFromCommServerMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
        getGetResultFromCommServerMethod;
    if ((getGetResultFromCommServerMethod = EnvoyReaderServiceGrpc.getGetResultFromCommServerMethod)
        == null) {
      synchronized (EnvoyReaderServiceGrpc.class) {
        if ((getGetResultFromCommServerMethod =
                EnvoyReaderServiceGrpc.getGetResultFromCommServerMethod)
            == null) {
          EnvoyReaderServiceGrpc.getGetResultFromCommServerMethod =
              getGetResultFromCommServerMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
                          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getResultFromCommServer"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new EnvoyReaderServiceMethodDescriptorSupplier("getResultFromCommServer"))
                      .build();
        }
      }
    }
    return getGetResultFromCommServerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
      getGetStatusFromCommServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getStatusFromCommServer",
      requestType = com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest.class,
      responseType = com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
      getGetStatusFromCommServerMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
        getGetStatusFromCommServerMethod;
    if ((getGetStatusFromCommServerMethod = EnvoyReaderServiceGrpc.getGetStatusFromCommServerMethod)
        == null) {
      synchronized (EnvoyReaderServiceGrpc.class) {
        if ((getGetStatusFromCommServerMethod =
                EnvoyReaderServiceGrpc.getGetStatusFromCommServerMethod)
            == null) {
          EnvoyReaderServiceGrpc.getGetStatusFromCommServerMethod =
              getGetStatusFromCommServerMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
                          com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getStatusFromCommServer"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new EnvoyReaderServiceMethodDescriptorSupplier("getStatusFromCommServer"))
                      .build();
        }
      }
    }
    return getGetStatusFromCommServerMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static EnvoyReaderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceStub>() {
          @java.lang.Override
          public EnvoyReaderServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new EnvoyReaderServiceStub(channel, callOptions);
          }
        };
    return EnvoyReaderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EnvoyReaderServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceBlockingStub>() {
          @java.lang.Override
          public EnvoyReaderServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new EnvoyReaderServiceBlockingStub(channel, callOptions);
          }
        };
    return EnvoyReaderServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static EnvoyReaderServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceFutureStub>() {
          @java.lang.Override
          public EnvoyReaderServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new EnvoyReaderServiceFutureStub(channel, callOptions);
          }
        };
    return EnvoyReaderServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class EnvoyReaderServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getResultFromCommServer(
        com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetResultFromCommServerMethod(), responseObserver);
    }

    /** */
    public void getStatusFromCommServer(
        com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetStatusFromCommServerMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetResultFromCommServerMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
                      com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>(
                      this, METHODID_GET_RESULT_FROM_COMM_SERVER)))
          .addMethod(
              getGetStatusFromCommServerMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest,
                      com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>(
                      this, METHODID_GET_STATUS_FROM_COMM_SERVER)))
          .build();
    }
  }

  /** */
  public static final class EnvoyReaderServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EnvoyReaderServiceStub> {
    private EnvoyReaderServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyReaderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyReaderServiceStub(channel, callOptions);
    }

    /** */
    public void getResultFromCommServer(
        com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetResultFromCommServerMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getStatusFromCommServer(
        com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStatusFromCommServerMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class EnvoyReaderServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EnvoyReaderServiceBlockingStub> {
    private EnvoyReaderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyReaderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyReaderServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply getResultFromCommServer(
        com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetResultFromCommServerMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply getStatusFromCommServer(
        com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStatusFromCommServerMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class EnvoyReaderServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EnvoyReaderServiceFutureStub> {
    private EnvoyReaderServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyReaderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyReaderServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
        getResultFromCommServer(
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetResultFromCommServerMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>
        getStatusFromCommServer(
            com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStatusFromCommServerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_RESULT_FROM_COMM_SERVER = 0;
  private static final int METHODID_GET_STATUS_FROM_COMM_SERVER = 1;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EnvoyReaderServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EnvoyReaderServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_RESULT_FROM_COMM_SERVER:
          serviceImpl.getResultFromCommServer(
              (com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>)
                  responseObserver);
          break;
        case METHODID_GET_STATUS_FROM_COMM_SERVER:
          serviceImpl.getStatusFromCommServer(
              (com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.EnvoyReader.EnvoyReaderResultReply>)
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

  private abstract static class EnvoyReaderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EnvoyReaderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.EnvoyReader.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EnvoyReaderService");
    }
  }

  private static final class EnvoyReaderServiceFileDescriptorSupplier
      extends EnvoyReaderServiceBaseDescriptorSupplier {
    EnvoyReaderServiceFileDescriptorSupplier() {}
  }

  private static final class EnvoyReaderServiceMethodDescriptorSupplier
      extends EnvoyReaderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EnvoyReaderServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (EnvoyReaderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new EnvoyReaderServiceFileDescriptorSupplier())
                      .addMethod(getGetResultFromCommServerMethod())
                      .addMethod(getGetStatusFromCommServerMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
