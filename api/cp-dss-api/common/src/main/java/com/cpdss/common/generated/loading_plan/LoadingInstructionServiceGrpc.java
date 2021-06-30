package com.cpdss.common.generated.loading_plan;

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
    comments = "Source: loading_plan/loading_plan_service.proto")
public final class LoadingInstructionServiceGrpc {

  private LoadingInstructionServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadingInstructionService";

  // Static method descriptors that strictly reflect the proto.
  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LoadingInstructionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceStub>() {
        @java.lang.Override
        public LoadingInstructionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadingInstructionServiceStub(channel, callOptions);
        }
      };
    return LoadingInstructionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadingInstructionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceBlockingStub>() {
        @java.lang.Override
        public LoadingInstructionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadingInstructionServiceBlockingStub(channel, callOptions);
        }
      };
    return LoadingInstructionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LoadingInstructionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadingInstructionServiceFutureStub>() {
        @java.lang.Override
        public LoadingInstructionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadingInstructionServiceFutureStub(channel, callOptions);
        }
      };
    return LoadingInstructionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LoadingInstructionServiceImplBase implements io.grpc.BindableService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .build();
    }
  }

  /**
   */
  public static final class LoadingInstructionServiceStub extends io.grpc.stub.AbstractAsyncStub<LoadingInstructionServiceStub> {
    private LoadingInstructionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInstructionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInstructionServiceStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class LoadingInstructionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LoadingInstructionServiceBlockingStub> {
    private LoadingInstructionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInstructionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInstructionServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class LoadingInstructionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LoadingInstructionServiceFutureStub> {
    private LoadingInstructionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingInstructionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingInstructionServiceFutureStub(channel, callOptions);
    }
  }


  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
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

  private static abstract class LoadingInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
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
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LoadingInstructionServiceFileDescriptorSupplier())
              .build();
        }
      }
    }
    return result;
  }
}
