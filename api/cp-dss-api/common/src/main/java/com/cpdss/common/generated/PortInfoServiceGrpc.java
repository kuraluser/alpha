/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.1)",
    comments = "Source: port_info.proto")
public final class PortInfoServiceGrpc {

  private PortInfoServiceGrpc() {}

  public static final String SERVICE_NAME = "PortInfoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfo",
      requestType = com.cpdss.common.generated.PortInfo.PortRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.PortReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.PortRequest,
            com.cpdss.common.generated.PortInfo.PortReply>
        getGetPortInfoMethod;
    if ((getGetPortInfoMethod = PortInfoServiceGrpc.getGetPortInfoMethod) == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoMethod = PortInfoServiceGrpc.getGetPortInfoMethod) == null) {
          PortInfoServiceGrpc.getGetPortInfoMethod =
              getGetPortInfoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.PortRequest,
                          com.cpdss.common.generated.PortInfo.PortReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetPortInfo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfo"))
                      .build();
        }
      }
    }
    return getGetPortInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
      getGetPortInfoByCargoIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfoByCargoId",
      requestType = com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
      getGetPortInfoByCargoIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
        getGetPortInfoByCargoIdMethod;
    if ((getGetPortInfoByCargoIdMethod = PortInfoServiceGrpc.getGetPortInfoByCargoIdMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoByCargoIdMethod = PortInfoServiceGrpc.getGetPortInfoByCargoIdMethod)
            == null) {
          PortInfoServiceGrpc.getGetPortInfoByCargoIdMethod =
              getGetPortInfoByCargoIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
                          com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPortInfoByCargoId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfoByCargoId"))
                      .build();
        }
      }
    }
    return getGetPortInfoByCargoIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoByPortIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfoByPortIds",
      requestType = com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.PortReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoByPortIdsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
            com.cpdss.common.generated.PortInfo.PortReply>
        getGetPortInfoByPortIdsMethod;
    if ((getGetPortInfoByPortIdsMethod = PortInfoServiceGrpc.getGetPortInfoByPortIdsMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoByPortIdsMethod = PortInfoServiceGrpc.getGetPortInfoByPortIdsMethod)
            == null) {
          PortInfoServiceGrpc.getGetPortInfoByPortIdsMethod =
              getGetPortInfoByPortIdsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
                          com.cpdss.common.generated.PortInfo.PortReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPortInfoByPortIds"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfoByPortIds"))
                      .build();
        }
      }
    }
    return getGetPortInfoByPortIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoDetailsForAlgoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfoDetailsForAlgo",
      requestType = com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.PortReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoDetailsForAlgoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
            com.cpdss.common.generated.PortInfo.PortReply>
        getGetPortInfoDetailsForAlgoMethod;
    if ((getGetPortInfoDetailsForAlgoMethod =
            PortInfoServiceGrpc.getGetPortInfoDetailsForAlgoMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoDetailsForAlgoMethod =
                PortInfoServiceGrpc.getGetPortInfoDetailsForAlgoMethod)
            == null) {
          PortInfoServiceGrpc.getGetPortInfoDetailsForAlgoMethod =
              getGetPortInfoDetailsForAlgoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
                          com.cpdss.common.generated.PortInfo.PortReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPortInfoDetailsForAlgo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfoDetailsForAlgo"))
                      .build();
        }
      }
    }
    return getGetPortInfoDetailsForAlgoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortEmptyRequest,
          com.cpdss.common.generated.PortInfo.TimezoneResponse>
      getGetTimezoneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTimezone",
      requestType = com.cpdss.common.generated.PortInfo.PortEmptyRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.TimezoneResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortEmptyRequest,
          com.cpdss.common.generated.PortInfo.TimezoneResponse>
      getGetTimezoneMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.PortEmptyRequest,
            com.cpdss.common.generated.PortInfo.TimezoneResponse>
        getGetTimezoneMethod;
    if ((getGetTimezoneMethod = PortInfoServiceGrpc.getGetTimezoneMethod) == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetTimezoneMethod = PortInfoServiceGrpc.getGetTimezoneMethod) == null) {
          PortInfoServiceGrpc.getGetTimezoneMethod =
              getGetTimezoneMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.PortEmptyRequest,
                          com.cpdss.common.generated.PortInfo.TimezoneResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTimezone"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortEmptyRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.TimezoneResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetTimezone"))
                      .build();
        }
      }
    }
    return getGetTimezoneMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortRequestWithPaging,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoByPagingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortInfoByPaging",
      requestType = com.cpdss.common.generated.PortInfo.PortRequestWithPaging.class,
      responseType = com.cpdss.common.generated.PortInfo.PortReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortRequestWithPaging,
          com.cpdss.common.generated.PortInfo.PortReply>
      getGetPortInfoByPagingMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.PortRequestWithPaging,
            com.cpdss.common.generated.PortInfo.PortReply>
        getGetPortInfoByPagingMethod;
    if ((getGetPortInfoByPagingMethod = PortInfoServiceGrpc.getGetPortInfoByPagingMethod) == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetPortInfoByPagingMethod = PortInfoServiceGrpc.getGetPortInfoByPagingMethod)
            == null) {
          PortInfoServiceGrpc.getGetPortInfoByPagingMethod =
              getGetPortInfoByPagingMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.PortRequestWithPaging,
                          com.cpdss.common.generated.PortInfo.PortReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPortInfoByPaging"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortRequestWithPaging
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetPortInfoByPaging"))
                      .build();
        }
      }
    }
    return getGetPortInfoByPagingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortIdRequest,
          com.cpdss.common.generated.PortInfo.BerthInfoResponse>
      getGetBerthDetailsByPortIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetBerthDetailsByPortId",
      requestType = com.cpdss.common.generated.PortInfo.PortIdRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.BerthInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.PortIdRequest,
          com.cpdss.common.generated.PortInfo.BerthInfoResponse>
      getGetBerthDetailsByPortIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.PortIdRequest,
            com.cpdss.common.generated.PortInfo.BerthInfoResponse>
        getGetBerthDetailsByPortIdMethod;
    if ((getGetBerthDetailsByPortIdMethod = PortInfoServiceGrpc.getGetBerthDetailsByPortIdMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetBerthDetailsByPortIdMethod =
                PortInfoServiceGrpc.getGetBerthDetailsByPortIdMethod)
            == null) {
          PortInfoServiceGrpc.getGetBerthDetailsByPortIdMethod =
              getGetBerthDetailsByPortIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.PortIdRequest,
                          com.cpdss.common.generated.PortInfo.BerthInfoResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetBerthDetailsByPortId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.PortIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.BerthInfoResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetBerthDetailsByPortId"))
                      .build();
        }
      }
    }
    return getGetBerthDetailsByPortIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
          com.cpdss.common.generated.PortInfo.CargoInfos>
      getGetCargoInfoByPortIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoInfoByPortIds",
      requestType = com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.CargoInfos.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
          com.cpdss.common.generated.PortInfo.CargoInfos>
      getGetCargoInfoByPortIdsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
            com.cpdss.common.generated.PortInfo.CargoInfos>
        getGetCargoInfoByPortIdsMethod;
    if ((getGetCargoInfoByPortIdsMethod = PortInfoServiceGrpc.getGetCargoInfoByPortIdsMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetCargoInfoByPortIdsMethod = PortInfoServiceGrpc.getGetCargoInfoByPortIdsMethod)
            == null) {
          PortInfoServiceGrpc.getGetCargoInfoByPortIdsMethod =
              getGetCargoInfoByPortIdsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
                          com.cpdss.common.generated.PortInfo.CargoInfos>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetCargoInfoByPortIds"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.CargoInfos.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetCargoInfoByPortIds"))
                      .build();
        }
      }
    }
    return getGetCargoInfoByPortIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.BerthIdsRequest,
          com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
      getGetLoadingPlanBerthDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingPlanBerthData",
      requestType = com.cpdss.common.generated.PortInfo.BerthIdsRequest.class,
      responseType = com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.PortInfo.BerthIdsRequest,
          com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
      getGetLoadingPlanBerthDataMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.PortInfo.BerthIdsRequest,
            com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
        getGetLoadingPlanBerthDataMethod;
    if ((getGetLoadingPlanBerthDataMethod = PortInfoServiceGrpc.getGetLoadingPlanBerthDataMethod)
        == null) {
      synchronized (PortInfoServiceGrpc.class) {
        if ((getGetLoadingPlanBerthDataMethod =
                PortInfoServiceGrpc.getGetLoadingPlanBerthDataMethod)
            == null) {
          PortInfoServiceGrpc.getGetLoadingPlanBerthDataMethod =
              getGetLoadingPlanBerthDataMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.PortInfo.BerthIdsRequest,
                          com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadingPlanBerthData"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.BerthIdsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new PortInfoServiceMethodDescriptorSupplier("GetLoadingPlanBerthData"))
                      .build();
        }
      }
    }
    return getGetLoadingPlanBerthDataMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static PortInfoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceStub>() {
          @java.lang.Override
          public PortInfoServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new PortInfoServiceStub(channel, callOptions);
          }
        };
    return PortInfoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PortInfoServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceBlockingStub>() {
          @java.lang.Override
          public PortInfoServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new PortInfoServiceBlockingStub(channel, callOptions);
          }
        };
    return PortInfoServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static PortInfoServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<PortInfoServiceFutureStub>() {
          @java.lang.Override
          public PortInfoServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new PortInfoServiceFutureStub(channel, callOptions);
          }
        };
    return PortInfoServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class PortInfoServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getPortInfo(
        com.cpdss.common.generated.PortInfo.PortRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoMethod(), responseObserver);
    }

    /** */
    public void getPortInfoByCargoId(
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoByCargoIdMethod(), responseObserver);
    }

    /** */
    public void getPortInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoByPortIdsMethod(), responseObserver);
    }

    /** */
    public void getPortInfoDetailsForAlgo(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoDetailsForAlgoMethod(), responseObserver);
    }

    /** */
    public void getTimezone(
        com.cpdss.common.generated.PortInfo.PortEmptyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.TimezoneResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetTimezoneMethod(), responseObserver);
    }

    /** */
    public void getPortInfoByPaging(
        com.cpdss.common.generated.PortInfo.PortRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortInfoByPagingMethod(), responseObserver);
    }

    /** */
    public void getBerthDetailsByPortId(
        com.cpdss.common.generated.PortInfo.PortIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetBerthDetailsByPortIdMethod(), responseObserver);
    }

    /** */
    public void getCargoInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.CargoInfos>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetCargoInfoByPortIdsMethod(), responseObserver);
    }

    /** */
    public void getLoadingPlanBerthData(
        com.cpdss.common.generated.PortInfo.BerthIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadingPlanBerthDataMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetPortInfoMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.PortRequest,
                      com.cpdss.common.generated.PortInfo.PortReply>(this, METHODID_GET_PORT_INFO)))
          .addMethod(
              getGetPortInfoByCargoIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest,
                      com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>(
                      this, METHODID_GET_PORT_INFO_BY_CARGO_ID)))
          .addMethod(
              getGetPortInfoByPortIdsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
                      com.cpdss.common.generated.PortInfo.PortReply>(
                      this, METHODID_GET_PORT_INFO_BY_PORT_IDS)))
          .addMethod(
              getGetPortInfoDetailsForAlgoMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
                      com.cpdss.common.generated.PortInfo.PortReply>(
                      this, METHODID_GET_PORT_INFO_DETAILS_FOR_ALGO)))
          .addMethod(
              getGetTimezoneMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.PortEmptyRequest,
                      com.cpdss.common.generated.PortInfo.TimezoneResponse>(
                      this, METHODID_GET_TIMEZONE)))
          .addMethod(
              getGetPortInfoByPagingMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.PortRequestWithPaging,
                      com.cpdss.common.generated.PortInfo.PortReply>(
                      this, METHODID_GET_PORT_INFO_BY_PAGING)))
          .addMethod(
              getGetBerthDetailsByPortIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.PortIdRequest,
                      com.cpdss.common.generated.PortInfo.BerthInfoResponse>(
                      this, METHODID_GET_BERTH_DETAILS_BY_PORT_ID)))
          .addMethod(
              getGetCargoInfoByPortIdsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest,
                      com.cpdss.common.generated.PortInfo.CargoInfos>(
                      this, METHODID_GET_CARGO_INFO_BY_PORT_IDS)))
          .addMethod(
              getGetLoadingPlanBerthDataMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.PortInfo.BerthIdsRequest,
                      com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>(
                      this, METHODID_GET_LOADING_PLAN_BERTH_DATA)))
          .build();
    }
  }

  /** */
  public static final class PortInfoServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PortInfoServiceStub> {
    private PortInfoServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInfoServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInfoServiceStub(channel, callOptions);
    }

    /** */
    public void getPortInfo(
        com.cpdss.common.generated.PortInfo.PortRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortInfoByCargoId(
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoByCargoIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoByPortIdsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortInfoDetailsForAlgo(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoDetailsForAlgoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getTimezone(
        com.cpdss.common.generated.PortInfo.PortEmptyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.TimezoneResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTimezoneMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortInfoByPaging(
        com.cpdss.common.generated.PortInfo.PortRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortInfoByPagingMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getBerthDetailsByPortId(
        com.cpdss.common.generated.PortInfo.PortIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBerthDetailsByPortIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.CargoInfos>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoInfoByPortIdsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadingPlanBerthData(
        com.cpdss.common.generated.PortInfo.BerthIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadingPlanBerthDataMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getBerthDetailsByPortId(
        com.cpdss.common.generated.PortInfo.PortIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetBerthDetailsByPortIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.CargoInfos>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoInfoByPortIdsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadingPlanBerthData(
        com.cpdss.common.generated.PortInfo.BerthIdsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadingPlanBerthDataMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class PortInfoServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PortInfoServiceBlockingStub> {
    private PortInfoServiceBlockingStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInfoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInfoServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.PortReply getPortInfo(
        com.cpdss.common.generated.PortInfo.PortRequest request) {
      return blockingUnaryCall(getChannel(), getGetPortInfoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply getPortInfoByCargoId(
        com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPortInfoByCargoIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.PortReply getPortInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPortInfoByPortIdsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.PortReply getPortInfoDetailsForAlgo(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPortInfoDetailsForAlgoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.TimezoneResponse getTimezone(
        com.cpdss.common.generated.PortInfo.PortEmptyRequest request) {
      return blockingUnaryCall(getChannel(), getGetTimezoneMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.PortReply getPortInfoByPaging(
        com.cpdss.common.generated.PortInfo.PortRequestWithPaging request) {
      return blockingUnaryCall(
          getChannel(), getGetPortInfoByPagingMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.BerthInfoResponse getBerthDetailsByPortId(
        com.cpdss.common.generated.PortInfo.PortIdRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetBerthDetailsByPortIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.CargoInfos getCargoInfoByPortIds(
        com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCargoInfoByPortIdsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData getLoadingPlanBerthData(
        com.cpdss.common.generated.PortInfo.BerthIdsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadingPlanBerthDataMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class PortInfoServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PortInfoServiceFutureStub> {
    private PortInfoServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PortInfoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PortInfoServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.PortReply>
        getPortInfo(com.cpdss.common.generated.PortInfo.PortRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>
        getPortInfoByCargoId(
            com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoByCargoIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.PortReply>
        getPortInfoByPortIds(
            com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoByPortIdsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.PortReply>
        getPortInfoDetailsForAlgo(
            com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoDetailsForAlgoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.TimezoneResponse>
        getTimezone(com.cpdss.common.generated.PortInfo.PortEmptyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTimezoneMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.PortReply>
        getPortInfoByPaging(com.cpdss.common.generated.PortInfo.PortRequestWithPaging request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortInfoByPagingMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.BerthInfoResponse>
        getBerthDetailsByPortId(com.cpdss.common.generated.PortInfo.PortIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetBerthDetailsByPortIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.CargoInfos>
        getCargoInfoByPortIds(
            com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCargoInfoByPortIdsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>
        getLoadingPlanBerthData(com.cpdss.common.generated.PortInfo.BerthIdsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadingPlanBerthDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PORT_INFO = 0;
  private static final int METHODID_GET_PORT_INFO_BY_CARGO_ID = 1;
  private static final int METHODID_GET_PORT_INFO_BY_PORT_IDS = 2;
  private static final int METHODID_GET_PORT_INFO_DETAILS_FOR_ALGO = 3;
  private static final int METHODID_GET_TIMEZONE = 4;
  private static final int METHODID_GET_PORT_INFO_BY_PAGING = 5;
  private static final int METHODID_GET_BERTH_DETAILS_BY_PORT_ID = 6;
  private static final int METHODID_GET_CARGO_INFO_BY_PORT_IDS = 7;
  private static final int METHODID_GET_LOADING_PLAN_BERTH_DATA = 8;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PortInfoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PortInfoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PORT_INFO:
          serviceImpl.getPortInfo(
              (com.cpdss.common.generated.PortInfo.PortRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>)
                  responseObserver);
          break;
        case METHODID_GET_PORT_INFO_BY_CARGO_ID:
          serviceImpl.getPortInfoByCargoId(
              (com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.PortInfo.GetPortInfoByCargoIdReply>)
                  responseObserver);
          break;
        case METHODID_GET_PORT_INFO_BY_PORT_IDS:
          serviceImpl.getPortInfoByPortIds(
              (com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>)
                  responseObserver);
          break;
        case METHODID_GET_PORT_INFO_DETAILS_FOR_ALGO:
          serviceImpl.getPortInfoDetailsForAlgo(
              (com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>)
                  responseObserver);
          break;
        case METHODID_GET_TIMEZONE:
          serviceImpl.getTimezone(
              (com.cpdss.common.generated.PortInfo.PortEmptyRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.TimezoneResponse>)
                  responseObserver);
          break;
        case METHODID_GET_PORT_INFO_BY_PAGING:
          serviceImpl.getPortInfoByPaging(
              (com.cpdss.common.generated.PortInfo.PortRequestWithPaging) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.PortReply>)
                  responseObserver);
          break;
        case METHODID_GET_BERTH_DETAILS_BY_PORT_ID:
          serviceImpl.getBerthDetailsByPortId(
              (com.cpdss.common.generated.PortInfo.PortIdRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_INFO_BY_PORT_IDS:
          serviceImpl.getCargoInfoByPortIds(
              (com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.CargoInfos>)
                  responseObserver);
          break;
        case METHODID_GET_LOADING_PLAN_BERTH_DATA:
          serviceImpl.getLoadingPlanBerthData(
              (com.cpdss.common.generated.PortInfo.BerthIdsRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>)
                  responseObserver);
          break;
        case METHODID_GET_BERTH_DETAILS_BY_PORT_ID:
          serviceImpl.getBerthDetailsByPortId(
              (com.cpdss.common.generated.PortInfo.PortIdRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.BerthInfoResponse>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_INFO_BY_PORT_IDS:
          serviceImpl.getCargoInfoByPortIds(
              (com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.PortInfo.CargoInfos>)
                  responseObserver);
          break;
        case METHODID_GET_LOADING_PLAN_BERTH_DATA:
          serviceImpl.getLoadingPlanBerthData(
              (com.cpdss.common.generated.PortInfo.BerthIdsRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.PortInfo.LoadingAlgoBerthData>)
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

  private abstract static class PortInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PortInfoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.PortInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PortInfoService");
    }
  }

  private static final class PortInfoServiceFileDescriptorSupplier
      extends PortInfoServiceBaseDescriptorSupplier {
    PortInfoServiceFileDescriptorSupplier() {}
  }

  private static final class PortInfoServiceMethodDescriptorSupplier
      extends PortInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PortInfoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PortInfoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new PortInfoServiceFileDescriptorSupplier())
                      .addMethod(getGetPortInfoMethod())
                      .addMethod(getGetPortInfoByCargoIdMethod())
                      .addMethod(getGetPortInfoByPortIdsMethod())
                      .addMethod(getGetPortInfoDetailsForAlgoMethod())
                      .addMethod(getGetTimezoneMethod())
                      .addMethod(getGetPortInfoByPagingMethod())
                      .addMethod(getGetBerthDetailsByPortIdMethod())
                      .addMethod(getGetCargoInfoByPortIdsMethod())
                      .addMethod(getGetLoadingPlanBerthDataMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
