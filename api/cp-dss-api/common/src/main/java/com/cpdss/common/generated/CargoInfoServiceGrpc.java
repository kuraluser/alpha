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
    comments = "Source: cargo_info.proto")
public final class CargoInfoServiceGrpc {

  private CargoInfoServiceGrpc() {}

  public static final String SERVICE_NAME = "CargoInfoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.CargoInfo.CargoRequest,
      com.cpdss.common.generated.CargoInfo.CargoReply> getGetCargoInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoInfo",
      requestType = com.cpdss.common.generated.CargoInfo.CargoRequest.class,
      responseType = com.cpdss.common.generated.CargoInfo.CargoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.CargoInfo.CargoRequest,
      com.cpdss.common.generated.CargoInfo.CargoReply> getGetCargoInfoMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.CargoInfo.CargoRequest, com.cpdss.common.generated.CargoInfo.CargoReply> getGetCargoInfoMethod;
    if ((getGetCargoInfoMethod = CargoInfoServiceGrpc.getGetCargoInfoMethod) == null) {
      synchronized (CargoInfoServiceGrpc.class) {
        if ((getGetCargoInfoMethod = CargoInfoServiceGrpc.getGetCargoInfoMethod) == null) {
          CargoInfoServiceGrpc.getGetCargoInfoMethod = getGetCargoInfoMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.CargoInfo.CargoRequest, com.cpdss.common.generated.CargoInfo.CargoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCargoInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.CargoInfo.CargoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.CargoInfo.CargoReply.getDefaultInstance()))
              .setSchemaDescriptor(new CargoInfoServiceMethodDescriptorSupplier("GetCargoInfo"))
              .build();
        }
      }
    }
    return getGetCargoInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging,
      com.cpdss.common.generated.CargoInfo.CargoReply> getGetCargoInfoByPagingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoInfoByPaging",
      requestType = com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.class,
      responseType = com.cpdss.common.generated.CargoInfo.CargoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging,
      com.cpdss.common.generated.CargoInfo.CargoReply> getGetCargoInfoByPagingMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging, com.cpdss.common.generated.CargoInfo.CargoReply> getGetCargoInfoByPagingMethod;
    if ((getGetCargoInfoByPagingMethod = CargoInfoServiceGrpc.getGetCargoInfoByPagingMethod) == null) {
      synchronized (CargoInfoServiceGrpc.class) {
        if ((getGetCargoInfoByPagingMethod = CargoInfoServiceGrpc.getGetCargoInfoByPagingMethod) == null) {
          CargoInfoServiceGrpc.getGetCargoInfoByPagingMethod = getGetCargoInfoByPagingMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging, com.cpdss.common.generated.CargoInfo.CargoReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCargoInfoByPaging"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.CargoInfo.CargoReply.getDefaultInstance()))
              .setSchemaDescriptor(new CargoInfoServiceMethodDescriptorSupplier("GetCargoInfoByPaging"))
              .build();
        }
      }
    }
    return getGetCargoInfoByPagingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.CargoInfo.CargoRequest,
          com.cpdss.common.generated.CargoInfo.CargoDetailReply>
      getGetCargoInfoByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoInfoById",
      requestType = com.cpdss.common.generated.CargoInfo.CargoRequest.class,
      responseType = com.cpdss.common.generated.CargoInfo.CargoDetailReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.CargoInfo.CargoRequest,
          com.cpdss.common.generated.CargoInfo.CargoDetailReply>
      getGetCargoInfoByIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.CargoInfo.CargoRequest,
            com.cpdss.common.generated.CargoInfo.CargoDetailReply>
        getGetCargoInfoByIdMethod;
    if ((getGetCargoInfoByIdMethod = CargoInfoServiceGrpc.getGetCargoInfoByIdMethod) == null) {
      synchronized (CargoInfoServiceGrpc.class) {
        if ((getGetCargoInfoByIdMethod = CargoInfoServiceGrpc.getGetCargoInfoByIdMethod) == null) {
          CargoInfoServiceGrpc.getGetCargoInfoByIdMethod =
              getGetCargoInfoByIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.CargoInfo.CargoRequest,
                          com.cpdss.common.generated.CargoInfo.CargoDetailReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCargoInfoById"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.CargoInfo.CargoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.CargoInfo.CargoDetailReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new CargoInfoServiceMethodDescriptorSupplier("GetCargoInfoById"))
                      .build();
        }
      }
    }
    return getGetCargoInfoByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.CargoInfo.CargoRequest,
          com.cpdss.common.generated.CargoInfo.CargoDetailReply>
      getGetCargoInfoByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoInfoById",
      requestType = com.cpdss.common.generated.CargoInfo.CargoRequest.class,
      responseType = com.cpdss.common.generated.CargoInfo.CargoDetailReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.CargoInfo.CargoRequest,
          com.cpdss.common.generated.CargoInfo.CargoDetailReply>
      getGetCargoInfoByIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.CargoInfo.CargoRequest,
            com.cpdss.common.generated.CargoInfo.CargoDetailReply>
        getGetCargoInfoByIdMethod;
    if ((getGetCargoInfoByIdMethod = CargoInfoServiceGrpc.getGetCargoInfoByIdMethod) == null) {
      synchronized (CargoInfoServiceGrpc.class) {
        if ((getGetCargoInfoByIdMethod = CargoInfoServiceGrpc.getGetCargoInfoByIdMethod) == null) {
          CargoInfoServiceGrpc.getGetCargoInfoByIdMethod =
              getGetCargoInfoByIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.CargoInfo.CargoRequest,
                          com.cpdss.common.generated.CargoInfo.CargoDetailReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCargoInfoById"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.CargoInfo.CargoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.CargoInfo.CargoDetailReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new CargoInfoServiceMethodDescriptorSupplier("GetCargoInfoById"))
                      .build();
        }
      }
    }
    return getGetCargoInfoByIdMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static CargoInfoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CargoInfoServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CargoInfoServiceStub>() {
        @java.lang.Override
        public CargoInfoServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CargoInfoServiceStub(channel, callOptions);
        }
      };
    return CargoInfoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CargoInfoServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CargoInfoServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CargoInfoServiceBlockingStub>() {
        @java.lang.Override
        public CargoInfoServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CargoInfoServiceBlockingStub(channel, callOptions);
        }
      };
    return CargoInfoServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CargoInfoServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CargoInfoServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CargoInfoServiceFutureStub>() {
        @java.lang.Override
        public CargoInfoServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CargoInfoServiceFutureStub(channel, callOptions);
        }
      };
    return CargoInfoServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CargoInfoServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCargoInfo(com.cpdss.common.generated.CargoInfo.CargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetCargoInfoMethod(), responseObserver);
    }

    /**
     */
    public void getCargoInfoByPaging(com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetCargoInfoByPagingMethod(), responseObserver);
    }

    /** */
    public void getCargoInfoById(
        com.cpdss.common.generated.CargoInfo.CargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetCargoInfoByIdMethod(), responseObserver);
    }

    /** */
    public void getCargoInfoById(
        com.cpdss.common.generated.CargoInfo.CargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetCargoInfoByIdMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCargoInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.CargoInfo.CargoRequest,
                com.cpdss.common.generated.CargoInfo.CargoReply>(
                  this, METHODID_GET_CARGO_INFO)))
          .addMethod(
              getGetCargoInfoByPagingMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging,
                      com.cpdss.common.generated.CargoInfo.CargoReply>(
                      this, METHODID_GET_CARGO_INFO_BY_PAGING)))
          .addMethod(
              getGetCargoInfoByIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.CargoInfo.CargoRequest,
                      com.cpdss.common.generated.CargoInfo.CargoDetailReply>(
                      this, METHODID_GET_CARGO_INFO_BY_ID)))
          .build();
    }
  }

  /**
   */
  public static final class CargoInfoServiceStub extends io.grpc.stub.AbstractAsyncStub<CargoInfoServiceStub> {
    private CargoInfoServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CargoInfoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CargoInfoServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCargoInfo(com.cpdss.common.generated.CargoInfo.CargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCargoInfoByPaging(com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoInfoByPagingMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void getCargoInfoById(
        com.cpdss.common.generated.CargoInfo.CargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoInfoByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoInfoById(
        com.cpdss.common.generated.CargoInfo.CargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoInfoByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /**
   */
  public static final class CargoInfoServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CargoInfoServiceBlockingStub> {
    private CargoInfoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CargoInfoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CargoInfoServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cpdss.common.generated.CargoInfo.CargoReply getCargoInfo(com.cpdss.common.generated.CargoInfo.CargoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCargoInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.CargoInfo.CargoReply getCargoInfoByPaging(com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging request) {
      return blockingUnaryCall(
          getChannel(), getGetCargoInfoByPagingMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.CargoInfo.CargoDetailReply getCargoInfoById(
        com.cpdss.common.generated.CargoInfo.CargoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCargoInfoByIdMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CargoInfoServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CargoInfoServiceFutureStub> {
    private CargoInfoServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CargoInfoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CargoInfoServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.CargoInfo.CargoReply> getCargoInfo(
        com.cpdss.common.generated.CargoInfo.CargoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCargoInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.CargoInfo.CargoReply> getCargoInfoByPaging(
        com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCargoInfoByPagingMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.CargoInfo.CargoDetailReply>
        getCargoInfoById(com.cpdss.common.generated.CargoInfo.CargoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCargoInfoByIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.CargoInfo.CargoDetailReply>
        getCargoInfoById(com.cpdss.common.generated.CargoInfo.CargoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCargoInfoByIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CARGO_INFO = 0;
  private static final int METHODID_GET_CARGO_INFO_BY_PAGING = 1;
  private static final int METHODID_GET_CARGO_INFO_BY_ID = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CargoInfoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CargoInfoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CARGO_INFO:
          serviceImpl.getCargoInfo((com.cpdss.common.generated.CargoInfo.CargoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoReply>) responseObserver);
          break;
        case METHODID_GET_CARGO_INFO_BY_PAGING:
          serviceImpl.getCargoInfoByPaging((com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoReply>) responseObserver);
          break;
        case METHODID_GET_CARGO_INFO_BY_ID:
          serviceImpl.getCargoInfoById(
              (com.cpdss.common.generated.CargoInfo.CargoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailReply>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_INFO_BY_ID:
          serviceImpl.getCargoInfoById(
              (com.cpdss.common.generated.CargoInfo.CargoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.CargoInfo.CargoDetailReply>)
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

  private static abstract class CargoInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CargoInfoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.CargoInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CargoInfoService");
    }
  }

  private static final class CargoInfoServiceFileDescriptorSupplier
      extends CargoInfoServiceBaseDescriptorSupplier {
    CargoInfoServiceFileDescriptorSupplier() {}
  }

  private static final class CargoInfoServiceMethodDescriptorSupplier
      extends CargoInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CargoInfoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CargoInfoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new CargoInfoServiceFileDescriptorSupplier())
                      .addMethod(getGetCargoInfoMethod())
                      .addMethod(getGetCargoInfoByPagingMethod())
                      .addMethod(getGetCargoInfoByIdMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
