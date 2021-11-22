/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: vessel_info.proto")
@io.grpc.stub.annotations.GrpcGenerated
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

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRuleRequest,
          com.cpdss.common.generated.VesselInfo.VesselRuleReply>
      getGetRulesByVesselIdAndSectionIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRulesByVesselIdAndSectionId",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRuleRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselRuleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRuleRequest,
          com.cpdss.common.generated.VesselInfo.VesselRuleReply>
      getGetRulesByVesselIdAndSectionIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRuleRequest,
            com.cpdss.common.generated.VesselInfo.VesselRuleReply>
        getGetRulesByVesselIdAndSectionIdMethod;
    if ((getGetRulesByVesselIdAndSectionIdMethod =
            VesselInfoServiceGrpc.getGetRulesByVesselIdAndSectionIdMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetRulesByVesselIdAndSectionIdMethod =
                VesselInfoServiceGrpc.getGetRulesByVesselIdAndSectionIdMethod)
            == null) {
          VesselInfoServiceGrpc.getGetRulesByVesselIdAndSectionIdMethod =
              getGetRulesByVesselIdAndSectionIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRuleRequest,
                          com.cpdss.common.generated.VesselInfo.VesselRuleReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetRulesByVesselIdAndSectionId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRuleRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRuleReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier(
                              "GetRulesByVesselIdAndSectionId"))
                      .build();
        }
      }
    }
    return getGetRulesByVesselIdAndSectionIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
      getGetVesselValveSequenceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselValveSequence",
      requestType = com.cpdss.common.generated.VesselInfo.VesselRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselRequest,
          com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
      getGetVesselValveSequenceMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselRequest,
            com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
        getGetVesselValveSequenceMethod;
    if ((getGetVesselValveSequenceMethod = VesselInfoServiceGrpc.getGetVesselValveSequenceMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselValveSequenceMethod =
                VesselInfoServiceGrpc.getGetVesselValveSequenceMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselValveSequenceMethod =
              getGetVesselValveSequenceMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselRequest,
                          com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselValveSequence"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselValveSequence"))
                      .build();
        }
      }
    }
    return getGetVesselValveSequenceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
      getGetLoadingInfoRulesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingInfoRules",
      requestType = com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
      getGetLoadingInfoRulesMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
            com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
        getGetLoadingInfoRulesMethod;
    if ((getGetLoadingInfoRulesMethod = VesselInfoServiceGrpc.getGetLoadingInfoRulesMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetLoadingInfoRulesMethod = VesselInfoServiceGrpc.getGetLoadingInfoRulesMethod)
            == null) {
          VesselInfoServiceGrpc.getGetLoadingInfoRulesMethod =
              getGetLoadingInfoRulesMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
                          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadingInfoRules"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetLoadingInfoRules"))
                      .build();
        }
      }
    }
    return getGetLoadingInfoRulesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselsInfoRequest,
          com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
      getGetVesselsInformationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselsInformation",
      requestType = com.cpdss.common.generated.VesselInfo.VesselsInfoRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselsInformationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselsInfoRequest,
          com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
      getGetVesselsInformationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselsInfoRequest,
            com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
        getGetVesselsInformationMethod;
    if ((getGetVesselsInformationMethod = VesselInfoServiceGrpc.getGetVesselsInformationMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselsInformationMethod = VesselInfoServiceGrpc.getGetVesselsInformationMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselsInformationMethod =
              getGetVesselsInformationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselsInfoRequest,
                          com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselsInformation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselsInfoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselsInformationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselsInformation"))
                      .build();
        }
      }
    }
    return getGetVesselsInformationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
          com.cpdss.common.generated.VesselInfo.VesselParticulars>
      getGetVesselParticularsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselParticulars",
      requestType = com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselParticulars.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
          com.cpdss.common.generated.VesselInfo.VesselParticulars>
      getGetVesselParticularsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
            com.cpdss.common.generated.VesselInfo.VesselParticulars>
        getGetVesselParticularsMethod;
    if ((getGetVesselParticularsMethod = VesselInfoServiceGrpc.getGetVesselParticularsMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselParticularsMethod = VesselInfoServiceGrpc.getGetVesselParticularsMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselParticularsMethod =
              getGetVesselParticularsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
                          com.cpdss.common.generated.VesselInfo.VesselParticulars>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselParticulars"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselParticulars
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselParticulars"))
                      .build();
        }
      }
    }
    return getGetVesselParticularsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselIdRequest,
          com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
      getGetVesselDetaildInformationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselDetaildInformation",
      requestType = com.cpdss.common.generated.VesselInfo.VesselIdRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselIdRequest,
          com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
      getGetVesselDetaildInformationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselIdRequest,
            com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
        getGetVesselDetaildInformationMethod;
    if ((getGetVesselDetaildInformationMethod =
            VesselInfoServiceGrpc.getGetVesselDetaildInformationMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselDetaildInformationMethod =
                VesselInfoServiceGrpc.getGetVesselDetaildInformationMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselDetaildInformationMethod =
              getGetVesselDetaildInformationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                          com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselDetaildInformation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier(
                              "GetVesselDetaildInformation"))
                      .build();
        }
      }
    }
    return getGetVesselDetaildInformationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselTankRequest,
          com.cpdss.common.generated.VesselInfo.VesselTankReply>
      getGetVesselTanksByTankIdsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVesselTanksByTankIds",
      requestType = com.cpdss.common.generated.VesselInfo.VesselTankRequest.class,
      responseType = com.cpdss.common.generated.VesselInfo.VesselTankReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.VesselInfo.VesselTankRequest,
          com.cpdss.common.generated.VesselInfo.VesselTankReply>
      getGetVesselTanksByTankIdsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.VesselInfo.VesselTankRequest,
            com.cpdss.common.generated.VesselInfo.VesselTankReply>
        getGetVesselTanksByTankIdsMethod;
    if ((getGetVesselTanksByTankIdsMethod = VesselInfoServiceGrpc.getGetVesselTanksByTankIdsMethod)
        == null) {
      synchronized (VesselInfoServiceGrpc.class) {
        if ((getGetVesselTanksByTankIdsMethod =
                VesselInfoServiceGrpc.getGetVesselTanksByTankIdsMethod)
            == null) {
          VesselInfoServiceGrpc.getGetVesselTanksByTankIdsMethod =
              getGetVesselTanksByTankIdsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.VesselInfo.VesselTankRequest,
                          com.cpdss.common.generated.VesselInfo.VesselTankReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVesselTanksByTankIds"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselTankRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.VesselInfo.VesselTankReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new VesselInfoServiceMethodDescriptorSupplier("GetVesselTanksByTankIds"))
                      .build();
        }
      }
    }
    return getGetVesselTanksByTankIdsMethod;
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
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetAllVesselsByCompanyMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselDetailsByIdMethod(), responseObserver);
    }

    /** */
    public void getVesselTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselTanksMethod(), responseObserver);
    }

    /** */
    public void getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselCargoTanksMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailsForAlgo(
        com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselDetailsForAlgoMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailForSynopticalTable(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselDetailForSynopticalTableMethod(), responseObserver);
    }

    /** */
    public void getVesselDetailByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselDetailByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getVesselInfoByPaging(
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselInfoByPagingMethod(), responseObserver);
    }

    /** */
    public void getVesselInfoBytankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselInfoBytankIdsMethod(), responseObserver);
    }

    /** */
    public void getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetDWTFromVesselByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getVesselInfoByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselIdResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselInfoByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getVesselPumpsByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselPumpsByVesselIdMethod(), responseObserver);
    }

    /** */
    public void getRulesByVesselIdAndSectionId(
        com.cpdss.common.generated.VesselInfo.VesselRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselRuleReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetRulesByVesselIdAndSectionIdMethod(), responseObserver);
    }

    /** */
    public void getVesselValveSequence(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselValveSequenceMethod(), responseObserver);
    }

    /** */
    public void getLoadingInfoRules(
        com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadingInfoRulesMethod(), responseObserver);
    }

    /** */
    public void getVesselsInformation(
        com.cpdss.common.generated.VesselInfo.VesselsInfoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselsInformationMethod(), responseObserver);
    }

    /** */
    public void getVesselParticulars(
        com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselParticulars>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselParticularsMethod(), responseObserver);
    }

    /** */
    public void getVesselDetaildInformation(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselDetaildInformationMethod(), responseObserver);
    }

    /** */
    public void getVesselTanksByTankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVesselTanksByTankIdsMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getGetAllVesselsByCompanyMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_ALL_VESSELS_BY_COMPANY)))
          .addMethod(
              getGetVesselDetailsByIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAILS_BY_ID)))
          .addMethod(
              getGetVesselTanksMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_TANKS)))
          .addMethod(
              getGetVesselCargoTanksMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_CARGO_TANKS)))
          .addMethod(
              getGetVesselDetailsForAlgoMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselAlgoRequest,
                      com.cpdss.common.generated.VesselInfo.VesselAlgoReply>(
                      this, METHODID_GET_VESSEL_DETAILS_FOR_ALGO)))
          .addMethod(
              getGetVesselDetailForSynopticalTableMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAIL_FOR_SYNOPTICAL_TABLE)))
          .addMethod(
              getGetVesselDetailByVesselIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_DETAIL_BY_VESSEL_ID)))
          .addMethod(
              getGetVesselInfoByPagingMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging,
                      com.cpdss.common.generated.VesselInfo.VesselReply>(
                      this, METHODID_GET_VESSEL_INFO_BY_PAGING)))
          .addMethod(
              getGetVesselInfoBytankIdsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselTankRequest,
                      com.cpdss.common.generated.VesselInfo.VesselTankResponse>(
                      this, METHODID_GET_VESSEL_INFO_BYTANK_IDS)))
          .addMethod(
              getGetDWTFromVesselByVesselIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselDWTRequest,
                      com.cpdss.common.generated.VesselInfo.VesselDWTResponse>(
                      this, METHODID_GET_DWTFROM_VESSEL_BY_VESSEL_ID)))
          .addMethod(
              getGetVesselInfoByVesselIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                      com.cpdss.common.generated.VesselInfo.VesselIdResponse>(
                      this, METHODID_GET_VESSEL_INFO_BY_VESSEL_ID)))
          .addMethod(
              getGetVesselPumpsByVesselIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                      com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>(
                      this, METHODID_GET_VESSEL_PUMPS_BY_VESSEL_ID)))
          .addMethod(
              getGetRulesByVesselIdAndSectionIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRuleRequest,
                      com.cpdss.common.generated.VesselInfo.VesselRuleReply>(
                      this, METHODID_GET_RULES_BY_VESSEL_ID_AND_SECTION_ID)))
          .addMethod(
              getGetVesselValveSequenceMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselRequest,
                      com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>(
                      this, METHODID_GET_VESSEL_VALVE_SEQUENCE)))
          .addMethod(
              getGetLoadingInfoRulesMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
                      com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>(
                      this, METHODID_GET_LOADING_INFO_RULES)))
          .addMethod(
              getGetVesselsInformationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselsInfoRequest,
                      com.cpdss.common.generated.VesselInfo.VesselsInformationReply>(
                      this, METHODID_GET_VESSELS_INFORMATION)))
          .addMethod(
              getGetVesselParticularsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest,
                      com.cpdss.common.generated.VesselInfo.VesselParticulars>(
                      this, METHODID_GET_VESSEL_PARTICULARS)))
          .addMethod(
              getGetVesselDetaildInformationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselIdRequest,
                      com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>(
                      this, METHODID_GET_VESSEL_DETAILD_INFORMATION)))
          .addMethod(
              getGetVesselTanksByTankIdsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.VesselInfo.VesselTankRequest,
                      com.cpdss.common.generated.VesselInfo.VesselTankReply>(
                      this, METHODID_GET_VESSEL_TANKS_BY_TANK_IDS)))
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
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllVesselsByCompanyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailsByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselTanksMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselCargoTanksMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailsForAlgo(
        com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailsForAlgoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailForSynopticalTable(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailForSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetailByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselDetailByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselInfoByPaging(
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselInfoByPagingMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselInfoBytankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankResponse>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselInfoBytankIdsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetDWTFromVesselByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselInfoByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselIdResponse>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselInfoByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselPumpsByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselPumpsByVesselIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getRulesByVesselIdAndSectionId(
        com.cpdss.common.generated.VesselInfo.VesselRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselRuleReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRulesByVesselIdAndSectionIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselValveSequence(
        com.cpdss.common.generated.VesselInfo.VesselRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselValveSequenceMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadingInfoRules(
        com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadingInfoRulesMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselsInformation(
        com.cpdss.common.generated.VesselInfo.VesselsInfoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselsInformationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselParticulars(
        com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselParticulars>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselParticularsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselDetaildInformation(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselDetaildInformationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVesselTanksByTankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVesselTanksByTankIdsMethod(), getCallOptions()),
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
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllVesselsByCompanyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailsById(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselDetailsByIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselTanksMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselCargoTanks(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselCargoTanksMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselAlgoReply getVesselDetailsForAlgo(
        com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselDetailsForAlgoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailForSynopticalTable(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselDetailForSynopticalTableMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselDetailByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselDetailByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselReply getVesselInfoByPaging(
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselInfoByPagingMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselTankResponse getVesselInfoBytankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselInfoBytankIdsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselDWTResponse getDWTFromVesselByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselDWTRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetDWTFromVesselByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselIdResponse getVesselInfoByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselInfoByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselPumpsResponse getVesselPumpsByVesselId(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselPumpsByVesselIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselRuleReply getRulesByVesselIdAndSectionId(
        com.cpdss.common.generated.VesselInfo.VesselRuleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRulesByVesselIdAndSectionIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply getVesselValveSequence(
        com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselValveSequenceMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply getLoadingInfoRules(
        com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadingInfoRulesMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselsInformationReply getVesselsInformation(
        com.cpdss.common.generated.VesselInfo.VesselsInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselsInformationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselParticulars getVesselParticulars(
        com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselParticularsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply getVesselDetaildInformation(
        com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselDetaildInformationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.VesselInfo.VesselTankReply getVesselTanksByTankIds(
        com.cpdss.common.generated.VesselInfo.VesselTankRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVesselTanksByTankIdsMethod(), getCallOptions(), request);
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
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllVesselsByCompanyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailsById(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselDetailsByIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselTanks(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselTanksMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselCargoTanks(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselCargoTanksMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselAlgoReply>
        getVesselDetailsForAlgo(com.cpdss.common.generated.VesselInfo.VesselAlgoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselDetailsForAlgoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailForSynopticalTable(
            com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselDetailForSynopticalTableMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselDetailByVesselId(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselDetailByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselReply>
        getVesselInfoByPaging(
            com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselInfoByPagingMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselTankResponse>
        getVesselInfoBytankIds(com.cpdss.common.generated.VesselInfo.VesselTankRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselInfoBytankIdsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselDWTResponse>
        getDWTFromVesselByVesselId(com.cpdss.common.generated.VesselInfo.VesselDWTRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetDWTFromVesselByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselIdResponse>
        getVesselInfoByVesselId(com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselInfoByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselPumpsResponse>
        getVesselPumpsByVesselId(com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselPumpsByVesselIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselRuleReply>
        getRulesByVesselIdAndSectionId(
            com.cpdss.common.generated.VesselInfo.VesselRuleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRulesByVesselIdAndSectionIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
        getVesselValveSequence(com.cpdss.common.generated.VesselInfo.VesselRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselValveSequenceMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>
        getLoadingInfoRules(com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadingInfoRulesMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselsInformationReply>
        getVesselsInformation(com.cpdss.common.generated.VesselInfo.VesselsInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselsInformationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselParticulars>
        getVesselParticulars(
            com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselParticularsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>
        getVesselDetaildInformation(com.cpdss.common.generated.VesselInfo.VesselIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselDetaildInformationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.VesselInfo.VesselTankReply>
        getVesselTanksByTankIds(com.cpdss.common.generated.VesselInfo.VesselTankRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVesselTanksByTankIdsMethod(), getCallOptions()), request);
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
  private static final int METHODID_GET_RULES_BY_VESSEL_ID_AND_SECTION_ID = 12;
  private static final int METHODID_GET_VESSEL_VALVE_SEQUENCE = 13;
  private static final int METHODID_GET_LOADING_INFO_RULES = 14;
  private static final int METHODID_GET_VESSELS_INFORMATION = 15;
  private static final int METHODID_GET_VESSEL_PARTICULARS = 16;
  private static final int METHODID_GET_VESSEL_DETAILD_INFORMATION = 17;
  private static final int METHODID_GET_VESSEL_TANKS_BY_TANK_IDS = 18;

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
        case METHODID_GET_RULES_BY_VESSEL_ID_AND_SECTION_ID:
          serviceImpl.getRulesByVesselIdAndSectionId(
              (com.cpdss.common.generated.VesselInfo.VesselRuleRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselRuleReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_VALVE_SEQUENCE:
          serviceImpl.getVesselValveSequence(
              (com.cpdss.common.generated.VesselInfo.VesselRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADING_INFO_RULES:
          serviceImpl.getLoadingInfoRules(
              (com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSELS_INFORMATION:
          serviceImpl.getVesselsInformation(
              (com.cpdss.common.generated.VesselInfo.VesselsInfoRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.VesselInfo.VesselsInformationReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_PARTICULARS:
          serviceImpl.getVesselParticulars(
              (com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselParticulars>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_DETAILD_INFORMATION:
          serviceImpl.getVesselDetaildInformation(
              (com.cpdss.common.generated.VesselInfo.VesselIdRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply>)
                  responseObserver);
          break;
        case METHODID_GET_VESSEL_TANKS_BY_TANK_IDS:
          serviceImpl.getVesselTanksByTankIds(
              (com.cpdss.common.generated.VesselInfo.VesselTankRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.VesselInfo.VesselTankReply>)
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
                      .addMethod(getGetRulesByVesselIdAndSectionIdMethod())
                      .addMethod(getGetVesselValveSequenceMethod())
                      .addMethod(getGetLoadingInfoRulesMethod())
                      .addMethod(getGetVesselsInformationMethod())
                      .addMethod(getGetVesselParticularsMethod())
                      .addMethod(getGetVesselDetaildInformationMethod())
                      .addMethod(getGetVesselTanksByTankIdsMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
