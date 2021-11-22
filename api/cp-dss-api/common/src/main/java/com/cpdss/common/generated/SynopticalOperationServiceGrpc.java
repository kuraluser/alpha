/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: loadable_study.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SynopticalOperationServiceGrpc {

  private SynopticalOperationServiceGrpc() {}

  public static final String SERVICE_NAME = "SynopticalOperationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateSynopticalTableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateSynopticalTable",
      requestType = com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateSynopticalTableMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
            com.cpdss.common.generated.Common.ResponseStatus>
        getUpdateSynopticalTableMethod;
    if ((getUpdateSynopticalTableMethod =
            SynopticalOperationServiceGrpc.getUpdateSynopticalTableMethod)
        == null) {
      synchronized (SynopticalOperationServiceGrpc.class) {
        if ((getUpdateSynopticalTableMethod =
                SynopticalOperationServiceGrpc.getUpdateSynopticalTableMethod)
            == null) {
          SynopticalOperationServiceGrpc.getUpdateSynopticalTableMethod =
              getUpdateSynopticalTableMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "updateSynopticalTable"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new SynopticalOperationServiceMethodDescriptorSupplier(
                              "updateSynopticalTable"))
                      .build();
        }
      }
    }
    return getUpdateSynopticalTableMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static SynopticalOperationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SynopticalOperationServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<SynopticalOperationServiceStub>() {
          @java.lang.Override
          public SynopticalOperationServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SynopticalOperationServiceStub(channel, callOptions);
          }
        };
    return SynopticalOperationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SynopticalOperationServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SynopticalOperationServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<SynopticalOperationServiceBlockingStub>() {
          @java.lang.Override
          public SynopticalOperationServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SynopticalOperationServiceBlockingStub(channel, callOptions);
          }
        };
    return SynopticalOperationServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static SynopticalOperationServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SynopticalOperationServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<SynopticalOperationServiceFutureStub>() {
          @java.lang.Override
          public SynopticalOperationServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SynopticalOperationServiceFutureStub(channel, callOptions);
          }
        };
    return SynopticalOperationServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class SynopticalOperationServiceImplBase
      implements io.grpc.BindableService {

    /** */
    public void updateSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getUpdateSynopticalTableMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getUpdateSynopticalTableMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_UPDATE_SYNOPTICAL_TABLE)))
          .build();
    }
  }

  /** */
  public static final class SynopticalOperationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SynopticalOperationServiceStub> {
    private SynopticalOperationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SynopticalOperationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SynopticalOperationServiceStub(channel, callOptions);
    }

    /** */
    public void updateSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class SynopticalOperationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SynopticalOperationServiceBlockingStub> {
    private SynopticalOperationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SynopticalOperationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SynopticalOperationServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus updateSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateSynopticalTableMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class SynopticalOperationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SynopticalOperationServiceFutureStub> {
    private SynopticalOperationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SynopticalOperationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SynopticalOperationServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        updateSynopticalTable(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateSynopticalTableMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPDATE_SYNOPTICAL_TABLE = 0;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SynopticalOperationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SynopticalOperationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPDATE_SYNOPTICAL_TABLE:
          serviceImpl.updateSynopticalTable(
              (com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
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

  private abstract static class SynopticalOperationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SynopticalOperationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.LoadableStudy.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SynopticalOperationService");
    }
  }

  private static final class SynopticalOperationServiceFileDescriptorSupplier
      extends SynopticalOperationServiceBaseDescriptorSupplier {
    SynopticalOperationServiceFileDescriptorSupplier() {}
  }

  private static final class SynopticalOperationServiceMethodDescriptorSupplier
      extends SynopticalOperationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SynopticalOperationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SynopticalOperationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new SynopticalOperationServiceFileDescriptorSupplier())
                      .addMethod(getUpdateSynopticalTableMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
