/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated.loading_plan;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: loading_plan/loading_plan_service.proto")
public final class LoadingPlanServiceGrpc {

  private LoadingPlanServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadingPlanService";

  // Static method descriptors that strictly reflect the proto.
  /** Creates a new async stub that supports all call types for the service */
  public static LoadingPlanServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceStub>() {
          @java.lang.Override
          public LoadingPlanServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingPlanServiceStub(channel, callOptions);
          }
        };
    return LoadingPlanServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadingPlanServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceBlockingStub>() {
          @java.lang.Override
          public LoadingPlanServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingPlanServiceBlockingStub(channel, callOptions);
          }
        };
    return LoadingPlanServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadingPlanServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadingPlanServiceFutureStub>() {
          @java.lang.Override
          public LoadingPlanServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadingPlanServiceFutureStub(channel, callOptions);
          }
        };
    return LoadingPlanServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadingPlanServiceImplBase implements io.grpc.BindableService {

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).build();
    }
  }

  /** */
  public static final class LoadingPlanServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LoadingPlanServiceStub> {
    private LoadingPlanServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceStub(channel, callOptions);
    }
  }

  /** */
  public static final class LoadingPlanServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LoadingPlanServiceBlockingStub> {
    private LoadingPlanServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceBlockingStub(channel, callOptions);
    }
  }

  /** */
  public static final class LoadingPlanServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LoadingPlanServiceFutureStub> {
    private LoadingPlanServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadingPlanServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadingPlanServiceFutureStub(channel, callOptions);
    }
  }

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadingPlanServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadingPlanServiceImplBase serviceImpl, int methodId) {
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

  private abstract static class LoadingPlanServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadingPlanServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.loading_plan.LoadingPlanServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadingPlanService");
    }
  }

  private static final class LoadingPlanServiceFileDescriptorSupplier
      extends LoadingPlanServiceBaseDescriptorSupplier {
    LoadingPlanServiceFileDescriptorSupplier() {}
  }

  private static final class LoadingPlanServiceMethodDescriptorSupplier
      extends LoadingPlanServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadingPlanServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoadingPlanServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new LoadingPlanServiceFileDescriptorSupplier())
                      .build();
        }
      }
    }
    return result;
  }
}
