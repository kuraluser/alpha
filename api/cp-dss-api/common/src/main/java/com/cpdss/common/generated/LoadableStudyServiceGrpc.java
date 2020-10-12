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
    comments = "Source: LoadableStudy.proto")
public final class LoadableStudyServiceGrpc {

  private LoadableStudyServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadableStudyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.VoyageRequest,
      com.cpdss.common.generated.LoadableStudy.VoyageReply> getSaveVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveVoyage",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.VoyageRequest,
      com.cpdss.common.generated.LoadableStudy.VoyageReply> getSaveVoyageMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.VoyageRequest, com.cpdss.common.generated.LoadableStudy.VoyageReply> getSaveVoyageMethod;
    if ((getSaveVoyageMethod = LoadableStudyServiceGrpc.getSaveVoyageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveVoyageMethod = LoadableStudyServiceGrpc.getSaveVoyageMethod) == null) {
          LoadableStudyServiceGrpc.getSaveVoyageMethod = getSaveVoyageMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.LoadableStudy.VoyageRequest, com.cpdss.common.generated.LoadableStudy.VoyageReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveVoyage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.VoyageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.VoyageReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadableStudyServiceMethodDescriptorSupplier("SaveVoyage"))
              .build();
        }
      }
    }
    return getSaveVoyageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LoadableStudyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceStub>() {
        @java.lang.Override
        public LoadableStudyServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadableStudyServiceStub(channel, callOptions);
        }
      };
    return LoadableStudyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadableStudyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceBlockingStub>() {
        @java.lang.Override
        public LoadableStudyServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadableStudyServiceBlockingStub(channel, callOptions);
        }
      };
    return LoadableStudyServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LoadableStudyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceFutureStub>() {
        @java.lang.Override
        public LoadableStudyServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LoadableStudyServiceFutureStub(channel, callOptions);
        }
      };
    return LoadableStudyServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LoadableStudyServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void saveVoyage(com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSaveVoyageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSaveVoyageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                com.cpdss.common.generated.LoadableStudy.VoyageReply>(
                  this, METHODID_SAVE_VOYAGE)))
          .build();
    }
  }

  /**
   */
  public static final class LoadableStudyServiceStub extends io.grpc.stub.AbstractAsyncStub<LoadableStudyServiceStub> {
    private LoadableStudyServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadableStudyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadableStudyServiceStub(channel, callOptions);
    }

    /**
     */
    public void saveVoyage(com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveVoyageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LoadableStudyServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LoadableStudyServiceBlockingStub> {
    private LoadableStudyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadableStudyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadableStudyServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cpdss.common.generated.LoadableStudy.VoyageReply saveVoyage(com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveVoyageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LoadableStudyServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LoadableStudyServiceFutureStub> {
    private LoadableStudyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadableStudyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadableStudyServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.LoadableStudy.VoyageReply> saveVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveVoyageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_VOYAGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoadableStudyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoadableStudyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAVE_VOYAGE:
          serviceImpl.saveVoyage((com.cpdss.common.generated.LoadableStudy.VoyageRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageReply>) responseObserver);
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

  private static abstract class LoadableStudyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoadableStudyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.LoadableStudy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoadableStudyService");
    }
  }

  private static final class LoadableStudyServiceFileDescriptorSupplier
      extends LoadableStudyServiceBaseDescriptorSupplier {
    LoadableStudyServiceFileDescriptorSupplier() {}
  }

  private static final class LoadableStudyServiceMethodDescriptorSupplier
      extends LoadableStudyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoadableStudyServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LoadableStudyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LoadableStudyServiceFileDescriptorSupplier())
              .addMethod(getSaveVoyageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
