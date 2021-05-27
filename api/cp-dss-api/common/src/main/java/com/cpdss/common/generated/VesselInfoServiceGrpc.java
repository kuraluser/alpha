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
    comments = "Source: vessel_info.proto")
public final class VesselInfoServiceGrpc {

  private VesselInfoServiceGrpc() {}

  public static final String SERVICE_NAME = "VesselInfoService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetAllVesselsByCompanyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllVesselsByCompany",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetAllVesselsByCompanyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetAllVesselsByCompanyMethod;
    if ((getGetAllVesselsByCompanyMethod = VesselInfoServiceGrpc.getGetAllVesselsByCompanyMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetAllVesselsByCompanyMethod =
                VesselInfoServiceGrpc.getGetAllVesselsByCompanyMethod)
            == null) {
          VesselInfoServiceGrpc.getGetAllVesselsByCompanyMethod =
              getGetAllVesselsByCompanyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetAllVesselsByCompany"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetAllVesselsByCompany"))
                      .build();
        }
      }
    }
    return getGetAllVesselsByCompanyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailsByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselDetailsById",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailsByIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselDetailsByIdMethod;
    if ((getGetVesselDetailsByIdMethod = VesselInfoServiceGrpc.getGetVesselDetailsByIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselDetailsByIdMethod = VesselInfoServiceGrpc.getGetVesselDetailsByIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselDetailsByIdMethod =
              getGetVesselDetailsByIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselDetailsById"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselDetailsById"))
                      .build();
        }
      }
    }
    return getGetVesselDetailsByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselTanksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselTanks",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselTanksMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselTanksMethod;
    if ((getGetVesselTanksMethod = VesselInfoServiceGrpc.getGetVesselTanksMethod) == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselTanksMethod = VesselInfoServiceGrpc.getGetVesselTanksMethod) == null) {
          VesselInfoServiceGrpc.getGetVesselTanksMethod =
              getGetVesselTanksMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVesselTanks"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselTanks"))
                      .build();
        }
      }
    }
    return getGetVesselTanksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselCargoTanksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselCargoTanks",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselCargoTanksMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselCargoTanksMethod;
    if ((getGetVesselCargoTanksMethod = VesselInfoServiceGrpc.getGetVesselCargoTanksMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselCargoTanksMethod = VesselInfoServiceGrpc.getGetVesselCargoTanksMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselCargoTanksMethod =
              getGetVesselCargoTanksMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselCargoTanks"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselCargoTanks"))
                      .build();
        }
      }
    }
    return getGetVesselCargoTanksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselAlgoRequest,
          com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
      getGetVesselDetailsForAlgoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselDetailsForAlgo",
      requestType = com.cpdss.common.generated.VesselInfo.VesselAlgoRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselAlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselAlgoRequest,
          com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
      getGetVesselDetailsForAlgoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselAlgoRequest,
            com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
        getGetVesselDetailsForAlgoMethod;
    if ((getGetVesselDetailsForAlgoMethod = VesselInfoServiceGrpc.getGetVesselDetailsForAlgoMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselDetailsForAlgoMethod =
                VesselInfoServiceGrpc.getGetVesselDetailsForAlgoMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselDetailsForAlgoMethod =
              getGetVesselDetailsForAlgoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselAlgoRequest,
                          com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselDetailsForAlgo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselAlgoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselAlgoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselDetailsForAlgo"))
                      .build();
        }
      }
    }
    return getGetVesselDetailsForAlgoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailForSynopticalTableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselDetailForSynopticalTable",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailForSynopticalTableMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselDetailForSynopticalTableMethod;
    if ((getGetVesselDetailForSynopticalTableMethod =
            VesselInfoServiceGrpc.getGetVesselDetailForSynopticalTableMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselDetailForSynopticalTableMethod =
                VesselInfoServiceGrpc.getGetVesselDetailForSynopticalTableMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselDetailForSynopticalTableMethod =
              getGetVesselDetailForSynopticalTableMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselDetailForSynopticalTable"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier(
                              "GetVesselDetailForSynopticalTable"))
                      .build();
        }
      }
    }
    return getGetVesselDetailForSynopticalTableMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailByVesselIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselDetailByVesselId",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselDetailByVesselIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselDetailByVesselIdMethod;
    if ((getGetVesselDetailByVesselIdMethod =
            VesselInfoServiceGrpc.getGetVesselDetailByVesselIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselDetailByVesselIdMethod =
                VesselInfoServiceGrpc.getGetVesselDetailByVesselIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselDetailByVesselIdMethod =
              getGetVesselDetailByVesselIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselDetailByVesselId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier(
                              "GetVesselDetailByVesselId"))
                      .build();
        }
      }
    }
    return getGetVesselDetailByVesselIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselInfoByPagingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselInfoByPaging",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging,
          com.cpdss.common.generated.VesselInfo.VesselReply>
      getGetVesselInfoByPagingMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging,
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getGetVesselInfoByPagingMethod;
    if ((getGetVesselInfoByPagingMethod = VesselInfoServiceGrpc.getGetVesselInfoByPagingMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselInfoByPagingMethod = VesselInfoServiceGrpc.getGetVesselInfoByPagingMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselInfoByPagingMethod =
              getGetVesselInfoByPagingMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging,
                          com.cpdss.common.generated.VesselInfo.VesselReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselInfoByPaging"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselInfoByPaging"))
                      .build();
        }
      }
    }
    return getGetVesselInfoByPagingMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselTankRequest,
          com.cpdss.common.generated.VesselInfo.VesselTankResponse>
      getGetVesselInfoBytankIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselInfoBytankIds",
      requestType = com.cpdss.common.generated.VesselInfo.VesselTankRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselTankResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselTankRequest,
          com.cpdss.common.generated.VesselInfo.VesselTankResponse>
      getGetVesselInfoBytankIdsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselTankRequest,
            com.cpdss.common.generated.VesselInfo.VesselTankResponse>
        getGetVesselInfoBytankIdsMethod;
    if ((getGetVesselInfoBytankIdsMethod = VesselInfoServiceGrpc.getGetVesselInfoBytankIdsMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselInfoBytankIdsMethod =
                VesselInfoServiceGrpc.getGetVesselInfoBytankIdsMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselInfoBytankIdsMethod =
              getGetVesselInfoBytankIdsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselTankRequest,
                          com.cpdss.common.generated.VesselInfo.VesselTankResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselInfoBytankIds"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselTankRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselTankResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselInfoBytankIds"))
                      .build();
        }
      }
    }
    return getGetVesselInfoBytankIdsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselDWTRequest,
          com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
      getGetDWTFromVesselByVesselIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetDWTFromVesselByVesselId",
      requestType = com.cpdss.common.generated.VesselInfo.VesselDWTRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselDWTResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselDWTRequest,
          com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
      getGetDWTFromVesselByVesselIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselDWTRequest,
            com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
        getGetDWTFromVesselByVesselIdMethod;
    if ((getGetDWTFromVesselByVesselIdMethod =
            VesselInfoServiceGrpc.getGetDWTFromVesselByVesselIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetDWTFromVesselByVesselIdMethod =
                VesselInfoServiceGrpc.getGetDWTFromVesselByVesselIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetDWTFromVesselByVesselIdMethod =
              getGetDWTFromVesselByVesselIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselDWTRequest,
                          com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetDWTFromVesselByVesselId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselDWTRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselDWTResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier(
                              "GetDWTFromVesselByVesselId"))
                      .build();
        }
      }
    }
    return getGetDWTFromVesselByVesselIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselIdRequest,
          com.cpdss.common.generated.VesselInfo.VesselIdResponse>
      getGetVesselInfoByVesselIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselInfoByVesselId",
      requestType = com.cpdss.common.generated.VesselInfo.VesselIdRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselIdResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselIdRequest,
          com.cpdss.common.generated.VesselInfo.VesselIdResponse>
      getGetVesselInfoByVesselIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselIdRequest,
            com.cpdss.common.generated.VesselInfo.VesselIdResponse>
        getGetVesselInfoByVesselIdMethod;
    if ((getGetVesselInfoByVesselIdMethod = VesselInfoServiceGrpc.getGetVesselInfoByVesselIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselInfoByVesselIdMethod =
                VesselInfoServiceGrpc.getGetVesselInfoByVesselIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselInfoByVesselIdMethod =
              getGetVesselInfoByVesselIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                          com.cpdss.common.generated.VesselInfo.VesselIdResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselInfoByVesselId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselIdResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselInfoByVesselId"))
                      .build();
        }
      }
    }
    return getGetVesselInfoByVesselIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselIdRequest,
          com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
      getGetVesselPumpsByVesselIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselPumpsByVesselId",
      requestType = com.cpdss.common.generated.VesselInfo.VesselIdRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselPumpsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselIdRequest,
          com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
      getGetVesselPumpsByVesselIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselIdRequest,
            com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
        getGetVesselPumpsByVesselIdMethod;
    if ((getGetVesselPumpsByVesselIdMethod =
            VesselInfoServiceGrpc.getGetVesselPumpsByVesselIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselPumpsByVesselIdMethod =
                VesselInfoServiceGrpc.getGetVesselPumpsByVesselIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselPumpsByVesselIdMethod =
              getGetVesselPumpsByVesselIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                          com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselPumpsByVesselId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselPumpsResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselPumpsByVesselId"))
                      .build();
        }
      }
    }
    return getGetVesselPumpsByVesselIdMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static VesselInfoServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceStub>() {
          @java.lang.Override
          public VesselInfoServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VesselInfoServiceStub(channel, callOptions);
          }
        };
    return VesselInfoServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VesselInfoServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceBlockingStub>() {
          @java.lang.Override
          public VesselInfoServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VesselInfoServiceBlockingStub(channel, callOptions);
          }
        };
    return VesselInfoServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static VesselInfoServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<VesselInfoServiceFutureStub>() {
          @java.lang.Override
          public VesselInfoServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new VesselInfoServiceFutureStub(channel, callOptions);
          }
        };
    return VesselInfoServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class VesselInfoServiceImplBase implements io.grpc.BindableService {

    /** */
    public void getAllVesselsByCompany(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllVesselsByCompanyMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselDetailsByIdMethod(), responseObserver);
    }

    /** */
    public void getVesselTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselTanksMethod(), responseObserver);
    }

    /** */
    public void getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselCargoTanksMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailsForAlgo(
        com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselDetailsForAlgoMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailForSynopticalTable(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselDetailForSynopticalTableMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselDetailByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getVesselInfoByPaging(
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselInfoByPagingMethod(), responseObserver);
    }

    /** */
    public void getVesselInfoBytankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselInfoBytankIdsMethod(), responseObserver);
    }

    /** */
    public void getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetDWTFromVesselByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getVesselInfoByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselIdResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselInfoByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getVesselPumpsByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVesselPumpsByVesselIdMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetAllVesselsByCompanyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_ALL_VESSELS_BY_COMPANY)))
          .addMethod(
              getGetVesselDetailsByIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAILS_BY_ID)))
          .addMethod(
              getGetVesselTanksMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_TANKS)))
          .addMethod(
              getGetVesselCargoTanksMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_CARGO_TANKS)))
          .addMethod(
              getGetVesselDetailsForAlgoMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselAlgoRequest,
                      com.cpdss.common.generated.VesselInfo.VesselAlgoReply>(
                      this, METHODID_GET_VESSEL_DETAILS_FOR_ALGO)))
          .addMethod(
              getGetVesselDetailForSynopticalTableMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAIL_FOR_SYNOPTICAL_TABLE)))
          .addMethod(
              getGetVesselDetailByVesselIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAIL_BY_VESSEL_ID)))
          .addMethod(
              getGetVesselInfoByPagingMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_INFO_BY_PAGING)))
          .addMethod(
              getGetVesselInfoBytankIdsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselTankRequest,
                      com.cpdss.common.generated.VesselInfo.VesselTankResponse>(
                      this, METHODID_GET_VESSEL_INFO_BYTANK_IDS)))
          .addMethod(
              getGetDWTFromVesselByVesselIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselDWTRequest,
                      com.cpdss.common.generated.VesselInfo.VesselDWTResponse>(
                      this, METHODID_GET_DWTFROM_VESSEL_BY_VESSEL_ID)))
          .addMethod(
              getGetVesselInfoByVesselIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                      com.cpdss.common.generated.VesselInfo.VesselIdResponse>(
                      this, METHODID_GET_VESSEL_INFO_BY_VESSEL_ID)))
          .addMethod(
              getGetVesselPumpsByVesselIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                      com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>(
                      this, METHODID_GET_VESSEL_PUMPS_BY_VESSEL_ID)))
          .build();
    }
  }

  /** */
  public static final class VesselInfoServiceStub
      extends io.grpc.stub.AbstractAsyncStub<VesselInfoServiceStub> {
    private VesselInfoServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VesselInfoServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VesselInfoServiceStub(channel, callOptions);
    }

    /** */
    public void getAllVesselsByCompany(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAllVesselsByCompanyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailsByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselTanksMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselCargoTanksMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailsForAlgo(
        com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailsForAlgoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailForSynopticalTable(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailForSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselInfoByPaging(
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselInfoByPagingMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselInfoBytankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselInfoBytankIdsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetDWTFromVesselByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselInfoByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselIdResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselInfoByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselPumpsByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVesselPumpsByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class VesselInfoServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<VesselInfoServiceBlockingStub> {
    private VesselInfoServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VesselInfoServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VesselInfoServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getAllVesselsByCompany(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAllVesselsByCompanyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselDetailsByIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(getChannel(), getGetVesselTanksMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselCargoTanksMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselAlgoReply getVesselDetailsForAlgo(
        com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselDetailsForAlgoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailForSynopticalTable(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselDetailForSynopticalTableMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselDetailByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselInfoByPaging(
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselInfoByPagingMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselTankResponse getVesselInfoBytankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselInfoBytankIdsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselDWTResponse getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetDWTFromVesselByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselIdResponse getVesselInfoByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselInfoByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselPumpsResponse getVesselPumpsByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVesselPumpsByVesselIdMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class VesselInfoServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<VesselInfoServiceFutureStub> {
    private VesselInfoServiceFutureStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VesselInfoServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VesselInfoServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getAllVesselsByCompany(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAllVesselsByCompanyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailsById(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselDetailsByIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselTanks(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselTanksMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselCargoTanks(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselCargoTanksMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
        getVesselDetailsForAlgo(com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselDetailsForAlgoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailForSynopticalTable(
            com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselDetailForSynopticalTableMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailByVesselId(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselDetailByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselInfoByPaging(
            com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselInfoByPagingMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselTankResponse>
        getVesselInfoBytankIds(com.cpdss.common.generated.VesselInfo.VesselTankRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselInfoBytankIdsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
        getDWTFromVesselByVesselId(com.cpdss.common.generated.VesselInfo.VesselDWTRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetDWTFromVesselByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselIdResponse>
        getVesselInfoByVesselId(com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselInfoByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
        getVesselPumpsByVesselId(com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVesselPumpsByVesselIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_VESSELS_BY_COMPANY = 0;
  private static final int METHODID_GET_VESSEL_DETAILS_BY_ID = 1;
  private static final int METHODID_GET_VESSEL_TANKS = 2;
  private static final int METHODID_GET_VESSEL_CARGO_TANKS = 3;
  private static final int METHODID_GET_VESSEL_DETAILS_FOR_ALGO = 4;
  private static final int METHODID_GET_VESSEL_DETAIL_FOR_SYNOPTICAL_TABLE = 5;
  private static final int METHODID_GET_VESSEL_DETAIL_BY_VESSEL_ID = 6;
  private static final int METHODID_GET_VESSEL_INFO_BY_PAGING = 7;
  private static final int METHODID_GET_VESSEL_INFO_BYTANK_IDS = 8;
  private static final int METHODID_GET_DWTFROM_VESSEL_BY_VESSEL_ID = 9;
  private static final int METHODID_GET_VESSEL_INFO_BY_VESSEL_ID = 10;
  private static final int METHODID_GET_VESSEL_PUMPS_BY_VESSEL_ID = 11;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
          io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VesselInfoServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VesselInfoServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_VESSELS_BY_COMPANY:
          serviceImpl.getAllVesselsByCompany(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_DETAILS_BY_ID:
          serviceImpl.getVesselDetailsById(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_TANKS:
          serviceImpl.getVesselTanks(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_CARGO_TANKS:
          serviceImpl.getVesselCargoTanks(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_DETAILS_FOR_ALGO:
          serviceImpl.getVesselDetailsForAlgo(
              (com.cpdss.common.generated.VesselInfo.VesselAlgoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselAlgoReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_DETAIL_FOR_SYNOPTICAL_TABLE:
          serviceImpl.getVesselDetailForSynopticalTable(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_DETAIL_BY_VESSEL_ID:
          serviceImpl.getVesselDetailByVesselId(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_INFO_BY_PAGING:
          serviceImpl.getVesselInfoByPaging(
              (com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_INFO_BYTANK_IDS:
          serviceImpl.getVesselInfoBytankIds(
              (com.cpdss.common.generated.VesselInfo.VesselTankRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.VesselInfo.VesselTankResponse>)
                  responseObserver);
          break;
        case METHODID_GET_DWTFROM_VESSEL_BY_VESSEL_ID:
          serviceImpl.getDWTFromVesselByVesselId(
              (com.cpdss.common.generated.VesselInfo.VesselDWTRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDWTResponse>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_INFO_BY_VESSEL_ID:
          serviceImpl.getVesselInfoByVesselId(
              (com.cpdss.common.generated.VesselInfo.VesselIdRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselIdResponse>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_PUMPS_BY_VESSEL_ID:
          serviceImpl.getVesselPumpsByVesselId(
              (com.cpdss.common.generated.VesselInfo.VesselIdRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>)
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

  private abstract static class VesselInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VesselInfoServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.VesselInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VesselInfoService");
    }
  }

  private static final class VesselInfoServiceFileDescriptorSupplier
      extends VesselInfoServiceBaseDescriptorSupplier {
    VesselInfoServiceFileDescriptorSupplier() {}
  }

  private static final class VesselInfoServiceMethodDescriptorSupplier
      extends VesselInfoServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VesselInfoServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (VesselInfoServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new VesselInfoServiceFileDescriptorSupplier())
                      .addMethod(getGetAllVesselsByCompanyMethod())
                      .addMethod(getGetVesselDetailsByIdMethod())
                      .addMethod(getGetVesselTanksMethod())
                      .addMethod(getGetVesselCargoTanksMethod())
                      .addMethod(getGetVesselDetailsForAlgoMethod())
                      .addMethod(getGetVesselDetailForSynopticalTableMethod())
                      .addMethod(getGetVesselDetailByVesselIdMethod())
                      .addMethod(getGetVesselInfoByPagingMethod())
                      .addMethod(getGetVesselInfoBytankIdsMethod())
                      .addMethod(getGetDWTFromVesselByVesselIdMethod())
                      .addMethod(getGetVesselInfoByVesselIdMethod())
                      .addMethod(getGetVesselPumpsByVesselIdMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
