package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: envoy_reader.proto")
public final class EnvoyReaderServiceGrpc {

  private EnvoyReaderServiceGrpc() {}

  public static final String SERVICE_NAME = "EnvoyReaderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.EnvoyReader.ResultJson,
      com.cpdss.common.generated.EnvoyReader.ReaderReply> getGetResultsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetResults",
      requestType = com.cpdss.common.generated.EnvoyReader.ResultJson.class,
      responseType = com.cpdss.common.generated.EnvoyReader.ReaderReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.EnvoyReader.ResultJson,
      com.cpdss.common.generated.EnvoyReader.ReaderReply> getGetResultsMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.EnvoyReader.ResultJson, com.cpdss.common.generated.EnvoyReader.ReaderReply> getGetResultsMethod;
    if ((getGetResultsMethod = EnvoyReaderServiceGrpc.getGetResultsMethod) == null) {
      synchronized (EnvoyReaderServiceGrpc.class) {
        if ((getGetResultsMethod = EnvoyReaderServiceGrpc.getGetResultsMethod) == null) {
          EnvoyReaderServiceGrpc.getGetResultsMethod = getGetResultsMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.EnvoyReader.ResultJson, com.cpdss.common.generated.EnvoyReader.ReaderReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetResults"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.EnvoyReader.ResultJson.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.EnvoyReader.ReaderReply.getDefaultInstance()))
              .setSchemaDescriptor(new EnvoyReaderServiceMethodDescriptorSupplier("GetResults"))
              .build();
        }
      }
    }
    return getGetResultsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EnvoyReaderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceStub>() {
        @java.lang.Override
        public EnvoyReaderServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EnvoyReaderServiceStub(channel, callOptions);
        }
      };
    return EnvoyReaderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EnvoyReaderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceBlockingStub>() {
        @java.lang.Override
        public EnvoyReaderServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EnvoyReaderServiceBlockingStub(channel, callOptions);
        }
      };
    return EnvoyReaderServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EnvoyReaderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EnvoyReaderServiceFutureStub>() {
        @java.lang.Override
        public EnvoyReaderServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EnvoyReaderServiceFutureStub(channel, callOptions);
        }
      };
    return EnvoyReaderServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class EnvoyReaderServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getResults(com.cpdss.common.generated.EnvoyReader.ResultJson request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.ReaderReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetResultsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetResultsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.EnvoyReader.ResultJson,
                com.cpdss.common.generated.EnvoyReader.ReaderReply>(
                  this, METHODID_GET_RESULTS)))
          .build();
    }
  }

  /**
   */
  public static final class EnvoyReaderServiceStub extends io.grpc.stub.AbstractAsyncStub<EnvoyReaderServiceStub> {
    private EnvoyReaderServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyReaderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyReaderServiceStub(channel, callOptions);
    }

    /**
     */
    public void getResults(com.cpdss.common.generated.EnvoyReader.ResultJson request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.ReaderReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetResultsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class EnvoyReaderServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<EnvoyReaderServiceBlockingStub> {
    private EnvoyReaderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyReaderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyReaderServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cpdss.common.generated.EnvoyReader.ReaderReply getResults(com.cpdss.common.generated.EnvoyReader.ResultJson request) {
      return blockingUnaryCall(
          getChannel(), getGetResultsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class EnvoyReaderServiceFutureStub extends io.grpc.stub.AbstractFutureStub<EnvoyReaderServiceFutureStub> {
    private EnvoyReaderServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EnvoyReaderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EnvoyReaderServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.EnvoyReader.ReaderReply> getResults(
        com.cpdss.common.generated.EnvoyReader.ResultJson request) {
      return futureUnaryCall(
          getChannel().newCall(getGetResultsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_RESULTS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
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
        case METHODID_GET_RESULTS:
          serviceImpl.getResults((com.cpdss.common.generated.EnvoyReader.ResultJson) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.EnvoyReader.ReaderReply>) responseObserver);
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

  private static abstract class EnvoyReaderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
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
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EnvoyReaderServiceFileDescriptorSupplier())
              .addMethod(getGetResultsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
