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
    comments = "Source: loadable_study.proto")
public final class PortInstructionServiceGrpc {

  private PortInstructionServiceGrpc() {}

  public static final String SERVICE_NAME = "PortInstructionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest,
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply> getGetInstructionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getInstructions",
      requestType = com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest.class,
      responseType = com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest,
      com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply> getGetInstructionsMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest, com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply> getGetInstructionsMethod;
    if ((getGetInstructionsMethod = PortInstructionServiceGrpc.getGetInstructionsMethod) == null) {
      synchronized (PortInstructionServiceGrpc.class) {
        if ((getGetInstructionsMethod = PortInstructionServiceGrpc.getGetInstructionsMethod) == null) {
          PortInstructionServiceGrpc.getGetInstructionsMethod = getGetInstructionsMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest, com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getInstructions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply.getDefaultInstance()))
              .setSchemaDescriptor(new PortInstructionServiceMethodDescriptorSupplier("getInstructions"))
              .build();
        }
      }
    }
    return getGetInstructionsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PortInstructionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInstructionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PortInstructionServiceStub>() {
        @java.lang.Override
        public PortInstructionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PortInstructionServiceStub(channel, callOptions);
        }
      };
    return PortInstructionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PortInstructionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInstructionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PortInstructionServiceBlockingStub>() {
        @java.lang.Override
        public PortInstructionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PortInstructionServiceBlockingStub(channel, callOptions);
        }
      };
    return PortInstructionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PortInstructionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInstructionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PortInstructionServiceFutureStub>() {
        @java.lang.Override
        public PortInstructionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PortInstructionServiceFutureStub(channel, callOptions);
        }
      };
    return PortInstructionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PortInstructionServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getInstructions(com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetInstructionsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetInstructionsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest,
                com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply>(
                  this, METHODID_GET_INSTRUCTIONS)))
          .build();
    }
  }

  /**
   */
  public static final class PortInstructionServiceStub extends io.grpc.stub.AbstractAsyncStub<PortInstructionServiceStub> {
    private PortInstructionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInstructionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInstructionServiceStub(channel, callOptions);
    }

    /**
     */
    public void getInstructions(com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetInstructionsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PortInstructionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<PortInstructionServiceBlockingStub> {
    private PortInstructionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInstructionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInstructionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply getInstructions(com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetInstructionsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PortInstructionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<PortInstructionServiceFutureStub> {
    private PortInstructionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInstructionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInstructionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply> getInstructions(
        com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetInstructionsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_INSTRUCTIONS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PortInstructionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PortInstructionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_INSTRUCTIONS:
          serviceImpl.getInstructions((com.cpdss.common.generated.loadableStudy.LoadableStudyModels.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.loadableStudy.LoadableStudyModels.InstructionReply>) responseObserver);
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

  private static abstract class PortInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PortInstructionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.LoadableStudy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PortInstructionService");
    }
  }

  private static final class PortInstructionServiceFileDescriptorSupplier
      extends PortInstructionServiceBaseDescriptorSupplier {
    PortInstructionServiceFileDescriptorSupplier() {}
  }

  private static final class PortInstructionServiceMethodDescriptorSupplier
      extends PortInstructionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PortInstructionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PortInstructionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PortInstructionServiceFileDescriptorSupplier())
              .addMethod(getGetInstructionsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
