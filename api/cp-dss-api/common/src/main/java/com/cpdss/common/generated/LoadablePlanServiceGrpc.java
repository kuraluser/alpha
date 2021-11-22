/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: loadable_plan.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LoadablePlanServiceGrpc {

  private LoadablePlanServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadablePlanService";

  // Static method descriptors that strictly reflect the proto.
  /** Creates a new async stub that supports all call types for the service */
  public static LoadablePlanServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadablePlanServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadablePlanServiceStub>() {
          @java.lang.Override
          public LoadablePlanServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadablePlanServiceStub(channel, callOptions);
          }
        };
    return LoadablePlanServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadablePlanServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadablePlanServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadablePlanServiceBlockingStub>() {
          @java.lang.Override
          public LoadablePlanServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadablePlanServiceBlockingStub(channel, callOptions);
          }
        };
    return LoadablePlanServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadablePlanServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadablePlanServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadablePlanServiceFutureStub>() {
          @java.lang.Override
          public LoadablePlanServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadablePlanServiceFutureStub(channel, callOptions);
          }
        };
    return LoadablePlanServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadablePlanServiceImplBase implements io.grpc.BindableService {

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor()).build();
    }
  }

  /** */
  public static final class LoadablePlanServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LoadablePlanServiceStub> {
    private LoadablePlanServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadablePlanServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadablePlanServiceStub(channel, callOptions);
    }
  }

  /** */
  public static final class LoadablePlanServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LoadablePlanServiceBlockingStub> {
    private LoadablePlanServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadablePlanServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadablePlanServiceBlockingStub(channel, callOptions);
    }
  }

  /** */
  public static final class LoadablePlanServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LoadablePlanServiceFutureStub> {
    private LoadablePlanServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadablePlanServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadablePlanServiceFutureStub(channel, callOptions);
    }
  }

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadablePlanServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadablePlanServiceImplBase serviceImpl, int methodId) {
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

  private abstract static class LoadablePlanServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadablePlanServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.LoadablePlan.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadablePlanService");
    }
  }

  private static final class LoadablePlanServiceFileDescriptorSupplier
      extends LoadablePlanServiceBaseDescriptorSupplier {
    LoadablePlanServiceFileDescriptorSupplier() {}
  }

  private static final class LoadablePlanServiceMethodDescriptorSupplier
      extends LoadablePlanServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadablePlanServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoadablePlanServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new LoadablePlanServiceFileDescriptorSupplier())
                      .build();
        }
      }
    }
    return result;
  }
}
