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

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
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
  }


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
              .build();
        }
      }
    }
    return result;
  }
}
