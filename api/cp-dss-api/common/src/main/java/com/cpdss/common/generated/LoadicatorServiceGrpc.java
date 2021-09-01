/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: loadicator.proto")
public final class LoadicatorServiceGrpc {

  private LoadicatorServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadicatorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.Loadicator.LoadicatorRequest,
          com.cpdss.common.generated.Loadicator.LoadicatorReply>
      getSaveLoadicatorInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveLoadicatorInfo",
      requestType = com.cpdss.common.generated.Loadicator.LoadicatorRequest.class,
      responseType = com.cpdss.common.generated.Loadicator.LoadicatorReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.Loadicator.LoadicatorRequest,
          com.cpdss.common.generated.Loadicator.LoadicatorReply>
      getSaveLoadicatorInfoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.Loadicator.LoadicatorRequest,
            com.cpdss.common.generated.Loadicator.LoadicatorReply>
        getSaveLoadicatorInfoMethod;
    if ((getSaveLoadicatorInfoMethod = LoadicatorServiceGrpc.getSaveLoadicatorInfoMethod) == null) {
      synchronized (LoadicatorServiceGrpc.class) {
        if ((getSaveLoadicatorInfoMethod = LoadicatorServiceGrpc.getSaveLoadicatorInfoMethod)
            == null) {
          LoadicatorServiceGrpc.getSaveLoadicatorInfoMethod =
              getSaveLoadicatorInfoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.Loadicator.LoadicatorRequest,
                          com.cpdss.common.generated.Loadicator.LoadicatorReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "saveLoadicatorInfo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Loadicator.LoadicatorRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Loadicator.LoadicatorReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadicatorServiceMethodDescriptorSupplier("saveLoadicatorInfo"))
                      .build();
        }
      }
    }
    return getSaveLoadicatorInfoMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static LoadicatorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadicatorServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadicatorServiceStub>() {
          @java.lang.Override
          public LoadicatorServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadicatorServiceStub(channel, callOptions);
          }
        };
    return LoadicatorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadicatorServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadicatorServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadicatorServiceBlockingStub>() {
          @java.lang.Override
          public LoadicatorServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadicatorServiceBlockingStub(channel, callOptions);
          }
        };
    return LoadicatorServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadicatorServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadicatorServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadicatorServiceFutureStub>() {
          @java.lang.Override
          public LoadicatorServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadicatorServiceFutureStub(channel, callOptions);
          }
        };
    return LoadicatorServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadicatorServiceImplBase implements io.grpc.BindableService {

    /** */
    public void saveLoadicatorInfo(
        com.cpdss.common.generated.Loadicator.LoadicatorRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Loadicator.LoadicatorReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadicatorInfoMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getSaveLoadicatorInfoMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.Loadicator.LoadicatorRequest,
                      com.cpdss.common.generated.Loadicator.LoadicatorReply>(
                      this, METHODID_SAVE_LOADICATOR_INFO)))
          .build();
    }
  }

  /** */
  public static final class LoadicatorServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LoadicatorServiceStub> {
    private LoadicatorServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadicatorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadicatorServiceStub(channel, callOptions);
    }

    /** */
    public void saveLoadicatorInfo(
        com.cpdss.common.generated.Loadicator.LoadicatorRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Loadicator.LoadicatorReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadicatorInfoMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class LoadicatorServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LoadicatorServiceBlockingStub> {
    private LoadicatorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadicatorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadicatorServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.Loadicator.LoadicatorReply saveLoadicatorInfo(
        com.cpdss.common.generated.Loadicator.LoadicatorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadicatorInfoMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class LoadicatorServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LoadicatorServiceFutureStub> {
    private LoadicatorServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadicatorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadicatorServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Loadicator.LoadicatorReply>
        saveLoadicatorInfo(com.cpdss.common.generated.Loadicator.LoadicatorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadicatorInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_LOADICATOR_INFO = 0;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadicatorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadicatorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_LOADICATOR_INFO:
          serviceImpl.saveLoadicatorInfo(
              (com.cpdss.common.generated.Loadicator.LoadicatorRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Loadicator.LoadicatorReply>)
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

  private abstract static class LoadicatorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadicatorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.Loadicator.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadicatorService");
    }
  }

  private static final class LoadicatorServiceFileDescriptorSupplier
      extends LoadicatorServiceBaseDescriptorSupplier {
    LoadicatorServiceFileDescriptorSupplier() {}
  }

  private static final class LoadicatorServiceMethodDescriptorSupplier
      extends LoadicatorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadicatorServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoadicatorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new LoadicatorServiceFileDescriptorSupplier())
                      .addMethod(getSaveLoadicatorInfoMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
