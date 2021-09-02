/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: envoy_writer.proto")
public final class EnvoyWriterServiceGrpc {

  private EnvoyWriterServiceGrpc() {}

  public static final String SERVICE_NAME = "EnvoyWriterService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
          com.cpdss.common.generated.EnvoyWriter.WriterReply>
      getGetCommunicationServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCommunicationServer",
      requestType = com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.class,
      responseType = com.cpdss.common.generated.EnvoyWriter.WriterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
          com.cpdss.common.generated.EnvoyWriter.WriterReply>
      getGetCommunicationServerMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
            com.cpdss.common.generated.EnvoyWriter.WriterReply>
        getGetCommunicationServerMethod;
    if ((getGetCommunicationServerMethod = EnvoyWriterServiceGrpc.getGetCommunicationServerMethod)
        == null) {
      synchronized (EnvoyWriterServiceGrpc.class) {
        if ((getGetCommunicationServerMethod =
                EnvoyWriterServiceGrpc.getGetCommunicationServerMethod)
            == null) {
          EnvoyWriterServiceGrpc.getGetCommunicationServerMethod =
              getGetCommunicationServerMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
                          com.cpdss.common.generated.EnvoyWriter.WriterReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetCommunicationServer"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyWriter.WriterReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new EnvoyWriterServiceMethodDescriptorSupplier("GetCommunicationServer"))
                      .build();
        }
      }
    }
    return getGetCommunicationServerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
          com.cpdss.common.generated.EnvoyWriter.WriterReply>
      getStatusCheckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StatusCheck",
      requestType = com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest.class,
      responseType = com.cpdss.common.generated.EnvoyWriter.WriterReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
          com.cpdss.common.generated.EnvoyWriter.WriterReply>
      getStatusCheckMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
            com.cpdss.common.generated.EnvoyWriter.WriterReply>
        getStatusCheckMethod;
    if ((getStatusCheckMethod = EnvoyWriterServiceGrpc.getStatusCheckMethod) == null) {
      synchronized (EnvoyWriterServiceGrpc.class) {
        if ((getStatusCheckMethod = EnvoyWriterServiceGrpc.getStatusCheckMethod) == null) {
          EnvoyWriterServiceGrpc.getStatusCheckMethod =
              getStatusCheckMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
                          com.cpdss.common.generated.EnvoyWriter.WriterReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StatusCheck"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.EnvoyWriter.WriterReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new EnvoyWriterServiceMethodDescriptorSupplier("StatusCheck"))
                      .build();
        }
      }
    }
    return getStatusCheckMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static EnvoyWriterServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyWriterServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<EnvoyWriterServiceStub>() {
          @java.lang.Override
          public EnvoyWriterServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new EnvoyWriterServiceStub(channel, callOptions);
          }
        };
    return EnvoyWriterServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EnvoyWriterServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyWriterServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<EnvoyWriterServiceBlockingStub>() {
          @java.lang.Override
          public EnvoyWriterServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new EnvoyWriterServiceBlockingStub(channel, callOptions);
          }
        };
    return EnvoyWriterServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static EnvoyWriterServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyWriterServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<EnvoyWriterServiceFutureStub>() {
          @java.lang.Override
          public EnvoyWriterServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new EnvoyWriterServiceFutureStub(channel, callOptions);
          }
        };
    return EnvoyWriterServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class EnvoyWriterServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getCommunicationServer(
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyWriter.WriterReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetCommunicationServerMethod(), responseObserver);
    }

    /** */
    public void statusCheck(
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyWriter.WriterReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getStatusCheckMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetCommunicationServerMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
                      com.cpdss.common.generated.EnvoyWriter.WriterReply>(
                      this, METHODID_GET_COMMUNICATION_SERVER)))
          .addMethod(
              getStatusCheckMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest,
                      com.cpdss.common.generated.EnvoyWriter.WriterReply>(
                      this, METHODID_STATUS_CHECK)))
          .build();
    }
  }

  /** */
  public static final class EnvoyWriterServiceStub
      extends io.grpc.stub.AbstractAsyncStub<EnvoyWriterServiceStub> {
    private EnvoyWriterServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyWriterServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyWriterServiceStub(channel, callOptions);
    }

    /** */
    public void getCommunicationServer(
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyWriter.WriterReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCommunicationServerMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void statusCheck(
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyWriter.WriterReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStatusCheckMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class EnvoyWriterServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EnvoyWriterServiceBlockingStub> {
    private EnvoyWriterServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyWriterServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyWriterServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.EnvoyWriter.WriterReply getCommunicationServer(
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCommunicationServerMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.EnvoyWriter.WriterReply statusCheck(
        com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStatusCheckMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class EnvoyWriterServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<EnvoyWriterServiceFutureStub> {
    private EnvoyWriterServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyWriterServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyWriterServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.EnvoyWriter.WriterReply>
        getCommunicationServer(com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCommunicationServerMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.EnvoyWriter.WriterReply>
        statusCheck(com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStatusCheckMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_COMMUNICATION_SERVER = 0;
  private static final int METHODID_STATUS_CHECK = 1;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EnvoyWriterServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EnvoyWriterServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_COMMUNICATION_SERVER:
          serviceImpl.getCommunicationServer(
              (com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyWriter.WriterReply>)
                  responseObserver);
          break;
        case METHODID_STATUS_CHECK:
          serviceImpl.statusCheck(
              (com.cpdss.common.generated.EnvoyWriter.EnvoyWriterRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyWriter.WriterReply>)
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

  private abstract static class EnvoyWriterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EnvoyWriterServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.EnvoyWriter.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EnvoyWriterService");
    }
  }

  private static final class EnvoyWriterServiceFileDescriptorSupplier
      extends EnvoyWriterServiceBaseDescriptorSupplier {
    EnvoyWriterServiceFileDescriptorSupplier() {}
  }

  private static final class EnvoyWriterServiceMethodDescriptorSupplier
      extends EnvoyWriterServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EnvoyWriterServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (EnvoyWriterServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new EnvoyWriterServiceFileDescriptorSupplier())
                      .addMethod(getGetCommunicationServerMethod())
                      .addMethod(getStatusCheckMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
