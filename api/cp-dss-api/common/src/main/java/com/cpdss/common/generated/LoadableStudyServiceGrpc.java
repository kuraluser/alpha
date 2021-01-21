/* Licensed under Apache-2.0 */
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
    comments = "Source: loadable_study.proto")
public final class LoadableStudyServiceGrpc {

  private LoadableStudyServiceGrpc() {}

  public static final String SERVICE_NAME = "LoadableStudyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageReply>
      getSaveVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveVoyage",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageReply>
      getSaveVoyageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageRequest,
            com.cpdss.common.generated.LoadableStudy.VoyageReply>
        getSaveVoyageMethod;
    if ((getSaveVoyageMethod = LoadableStudyServiceGrpc.getSaveVoyageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveVoyageMethod = LoadableStudyServiceGrpc.getSaveVoyageMethod) == null) {
          LoadableStudyServiceGrpc.getSaveVoyageMethod =
              getSaveVoyageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                          com.cpdss.common.generated.LoadableStudy.VoyageReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveVoyage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveVoyage"))
                      .build();
        }
      }
    }
    return getSaveVoyageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageListReply>
      getGetVoyagesByVesselMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVoyagesByVessel",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageListReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageListReply>
      getGetVoyagesByVesselMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageRequest,
            com.cpdss.common.generated.LoadableStudy.VoyageListReply>
        getGetVoyagesByVesselMethod;
    if ((getGetVoyagesByVesselMethod = LoadableStudyServiceGrpc.getGetVoyagesByVesselMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetVoyagesByVesselMethod = LoadableStudyServiceGrpc.getGetVoyagesByVesselMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetVoyagesByVesselMethod =
              getGetVoyagesByVesselMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                          com.cpdss.common.generated.LoadableStudy.VoyageListReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVoyagesByVessel"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageListReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetVoyagesByVessel"))
                      .build();
        }
      }
    }
    return getGetVoyagesByVesselMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
      getSaveLoadableQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadableQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
      getSaveLoadableQuantityMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
        getSaveLoadableQuantityMethod;
    if ((getSaveLoadableQuantityMethod = LoadableStudyServiceGrpc.getSaveLoadableQuantityMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableQuantityMethod = LoadableStudyServiceGrpc.getSaveLoadableQuantityMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadableQuantityMethod =
              getSaveLoadableQuantityMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadableQuantity"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadableQuantity"))
                      .build();
        }
      }
    }
    return getSaveLoadableQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
      getFindLoadableStudiesByVesselAndVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindLoadableStudiesByVesselAndVoyage",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
      getFindLoadableStudiesByVesselAndVoyageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        getFindLoadableStudiesByVesselAndVoyageMethod;
    if ((getFindLoadableStudiesByVesselAndVoyageMethod =
            LoadableStudyServiceGrpc.getFindLoadableStudiesByVesselAndVoyageMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getFindLoadableStudiesByVesselAndVoyageMethod =
                LoadableStudyServiceGrpc.getFindLoadableStudiesByVesselAndVoyageMethod)
            == null) {
          LoadableStudyServiceGrpc.getFindLoadableStudiesByVesselAndVoyageMethod =
              getFindLoadableStudiesByVesselAndVoyageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "FindLoadableStudiesByVesselAndVoyage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "FindLoadableStudiesByVesselAndVoyage"))
                      .build();
        }
      }
    }
    return getFindLoadableStudiesByVesselAndVoyageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
      getSaveLoadableStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadableStudy",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
      getSaveLoadableStudyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        getSaveLoadableStudyMethod;
    if ((getSaveLoadableStudyMethod = LoadableStudyServiceGrpc.getSaveLoadableStudyMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableStudyMethod = LoadableStudyServiceGrpc.getSaveLoadableStudyMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadableStudyMethod =
              getSaveLoadableStudyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveLoadableStudy"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadableStudy"))
                      .build();
        }
      }
    }
    return getSaveLoadableStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
      getSaveCargoNominationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCargoNomination",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoNominationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
      getSaveCargoNominationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        getSaveCargoNominationMethod;
    if ((getSaveCargoNominationMethod = LoadableStudyServiceGrpc.getSaveCargoNominationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveCargoNominationMethod = LoadableStudyServiceGrpc.getSaveCargoNominationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveCargoNominationMethod =
              getSaveCargoNominationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveCargoNomination"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveCargoNomination"))
                      .build();
        }
      }
    }
    return getSaveCargoNominationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getGetLoadableStudyPortRotationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudyPortRotation",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getGetLoadableStudyPortRotationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getGetLoadableStudyPortRotationMethod;
    if ((getGetLoadableStudyPortRotationMethod =
            LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudyPortRotationMethod =
                LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationMethod =
              getGetLoadableStudyPortRotationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadableStudyPortRotation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadableStudyPortRotation"))
                      .build();
        }
      }
    }
    return getGetLoadableStudyPortRotationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
      getGetCargoNominationByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoNominationById",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoNominationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
      getGetCargoNominationByIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        getGetCargoNominationByIdMethod;
    if ((getGetCargoNominationByIdMethod = LoadableStudyServiceGrpc.getGetCargoNominationByIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetCargoNominationByIdMethod =
                LoadableStudyServiceGrpc.getGetCargoNominationByIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetCargoNominationByIdMethod =
              getGetCargoNominationByIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetCargoNominationById"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetCargoNominationById"))
                      .build();
        }
      }
    }
    return getGetCargoNominationByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest,
          com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
      getGetValveSegregationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetValveSegregation",
      requestType = com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.ValveSegregationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest,
          com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
      getGetValveSegregationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest,
            com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
        getGetValveSegregationMethod;
    if ((getGetValveSegregationMethod = LoadableStudyServiceGrpc.getGetValveSegregationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetValveSegregationMethod = LoadableStudyServiceGrpc.getGetValveSegregationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetValveSegregationMethod =
              getGetValveSegregationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest,
                          com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetValveSegregation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.ValveSegregationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetValveSegregation"))
                      .build();
        }
      }
    }
    return getGetValveSegregationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply,
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
      getGetLoadableQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadableQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply,
          com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
      getGetLoadableQuantityMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply,
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
        getGetLoadableQuantityMethod;
    if ((getGetLoadableQuantityMethod = LoadableStudyServiceGrpc.getGetLoadableQuantityMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableQuantityMethod = LoadableStudyServiceGrpc.getGetLoadableQuantityMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableQuantityMethod =
              getGetLoadableQuantityMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply,
                          com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getLoadableQuantity"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("getLoadableQuantity"))
                      .build();
        }
      }
    }
    return getGetLoadableQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationDetail,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getSaveLoadableStudyPortRotationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadableStudyPortRotation",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationDetail.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationDetail,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getSaveLoadableStudyPortRotationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationDetail,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getSaveLoadableStudyPortRotationMethod;
    if ((getSaveLoadableStudyPortRotationMethod =
            LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableStudyPortRotationMethod =
                LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationMethod =
              getSaveLoadableStudyPortRotationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationDetail,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadableStudyPortRotation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationDetail
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "SaveLoadableStudyPortRotation"))
                      .build();
        }
      }
    }
    return getSaveLoadableStudyPortRotationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getSaveLoadableStudyPortRotationListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadableStudyPortRotationList",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getSaveLoadableStudyPortRotationListMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getSaveLoadableStudyPortRotationListMethod;
    if ((getSaveLoadableStudyPortRotationListMethod =
            LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationListMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableStudyPortRotationListMethod =
                LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationListMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationListMethod =
              getSaveLoadableStudyPortRotationListMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadableStudyPortRotationList"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "SaveLoadableStudyPortRotationList"))
                      .build();
        }
      }
    }
    return getSaveLoadableStudyPortRotationListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
      getDeleteCargoNominationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteCargoNomination",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoNominationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
      getDeleteCargoNominationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        getDeleteCargoNominationMethod;
    if ((getDeleteCargoNominationMethod = LoadableStudyServiceGrpc.getDeleteCargoNominationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getDeleteCargoNominationMethod =
                LoadableStudyServiceGrpc.getDeleteCargoNominationMethod)
            == null) {
          LoadableStudyServiceGrpc.getDeleteCargoNominationMethod =
              getDeleteCargoNominationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "DeleteCargoNomination"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("DeleteCargoNomination"))
                      .build();
        }
      }
    }
    return getDeleteCargoNominationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getSaveDischargingPortsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveDischargingPorts",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getSaveDischargingPortsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getSaveDischargingPortsMethod;
    if ((getSaveDischargingPortsMethod = LoadableStudyServiceGrpc.getSaveDischargingPortsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveDischargingPortsMethod = LoadableStudyServiceGrpc.getSaveDischargingPortsMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveDischargingPortsMethod =
              getSaveDischargingPortsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveDischargingPorts"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveDischargingPorts"))
                      .build();
        }
      }
    }
    return getSaveDischargingPortsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getGetPortRotationByLoadableStudyIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPortRotationByLoadableStudyId",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getGetPortRotationByLoadableStudyIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getGetPortRotationByLoadableStudyIdMethod;
    if ((getGetPortRotationByLoadableStudyIdMethod =
            LoadableStudyServiceGrpc.getGetPortRotationByLoadableStudyIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetPortRotationByLoadableStudyIdMethod =
                LoadableStudyServiceGrpc.getGetPortRotationByLoadableStudyIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetPortRotationByLoadableStudyIdMethod =
              getGetPortRotationByLoadableStudyIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPortRotationByLoadableStudyId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetPortRotationByLoadableStudyId"))
                      .build();
        }
      }
    }
    return getGetPortRotationByLoadableStudyIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
      getDeleteLoadableStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteLoadableStudy",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
      getDeleteLoadableStudyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        getDeleteLoadableStudyMethod;
    if ((getDeleteLoadableStudyMethod = LoadableStudyServiceGrpc.getDeleteLoadableStudyMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getDeleteLoadableStudyMethod = LoadableStudyServiceGrpc.getDeleteLoadableStudyMethod)
            == null) {
          LoadableStudyServiceGrpc.getDeleteLoadableStudyMethod =
              getDeleteLoadableStudyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "DeleteLoadableStudy"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("DeleteLoadableStudy"))
                      .build();
        }
      }
    }
    return getDeleteLoadableStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getDeletePortRotationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeletePortRotation",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
      getDeletePortRotationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getDeletePortRotationMethod;
    if ((getDeletePortRotationMethod = LoadableStudyServiceGrpc.getDeletePortRotationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getDeletePortRotationMethod = LoadableStudyServiceGrpc.getDeletePortRotationMethod)
            == null) {
          LoadableStudyServiceGrpc.getDeletePortRotationMethod =
              getDeletePortRotationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeletePortRotation"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("DeletePortRotation"))
                      .build();
        }
      }
    }
    return getDeletePortRotationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest,
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
      getGetOnHandQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOnHandQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest,
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
      getGetOnHandQuantityMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest,
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
        getGetOnHandQuantityMethod;
    if ((getGetOnHandQuantityMethod = LoadableStudyServiceGrpc.getGetOnHandQuantityMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetOnHandQuantityMethod = LoadableStudyServiceGrpc.getGetOnHandQuantityMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetOnHandQuantityMethod =
              getGetOnHandQuantityMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest,
                          com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOnHandQuantity"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetOnHandQuantity"))
                      .build();
        }
      }
    }
    return getGetOnHandQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail,
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
      getSaveOnHandQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveOnHandQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail.class,
      responseType = com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail,
          com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
      getSaveOnHandQuantityMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail,
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
        getSaveOnHandQuantityMethod;
    if ((getSaveOnHandQuantityMethod = LoadableStudyServiceGrpc.getSaveOnHandQuantityMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveOnHandQuantityMethod = LoadableStudyServiceGrpc.getSaveOnHandQuantityMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveOnHandQuantityMethod =
              getSaveOnHandQuantityMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail,
                          com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveOnHandQuantity"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveOnHandQuantity"))
                      .build();
        }
      }
    }
    return getSaveOnHandQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
      getGetLoadablePatternDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadablePatternDetails",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
      getGetLoadablePatternDetailsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
        getGetLoadablePatternDetailsMethod;
    if ((getGetLoadablePatternDetailsMethod =
            LoadableStudyServiceGrpc.getGetLoadablePatternDetailsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternDetailsMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternDetailsMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternDetailsMethod =
              getGetLoadablePatternDetailsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadablePatternDetails"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePatternReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadablePatternDetails"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSaveLoadablePatternDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadablePatternDetails",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSaveLoadablePatternDetailsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getSaveLoadablePatternDetailsMethod;
    if ((getSaveLoadablePatternDetailsMethod =
            LoadableStudyServiceGrpc.getSaveLoadablePatternDetailsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadablePatternDetailsMethod =
                LoadableStudyServiceGrpc.getSaveLoadablePatternDetailsMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadablePatternDetailsMethod =
              getSaveLoadablePatternDetailsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadablePatternDetails"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "SaveLoadablePatternDetails"))
                      .build();
        }
      }
    }
    return getSaveLoadablePatternDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest,
          com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
      getGetPurposeOfCommingleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPurposeOfCommingle",
      requestType = com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest,
          com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
      getGetPurposeOfCommingleMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest,
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
        getGetPurposeOfCommingleMethod;
    if ((getGetPurposeOfCommingleMethod = LoadableStudyServiceGrpc.getGetPurposeOfCommingleMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetPurposeOfCommingleMethod =
                LoadableStudyServiceGrpc.getGetPurposeOfCommingleMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetPurposeOfCommingleMethod =
              getGetPurposeOfCommingleMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest,
                          com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetPurposeOfCommingle"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetPurposeOfCommingle"))
                      .build();
        }
      }
    }
    return getGetPurposeOfCommingleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
          com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
      getGetCommingleCargoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCommingleCargo",
      requestType = com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CommingleCargoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
          com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
      getGetCommingleCargoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
            com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
        getGetCommingleCargoMethod;
    if ((getGetCommingleCargoMethod = LoadableStudyServiceGrpc.getGetCommingleCargoMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetCommingleCargoMethod = LoadableStudyServiceGrpc.getGetCommingleCargoMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetCommingleCargoMethod =
              getGetCommingleCargoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
                          com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCommingleCargo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CommingleCargoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetCommingleCargo"))
                      .build();
        }
      }
    }
    return getGetCommingleCargoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
          com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
      getSaveCommingleCargoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCommingleCargo",
      requestType = com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CommingleCargoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
          com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
      getSaveCommingleCargoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
            com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
        getSaveCommingleCargoMethod;
    if ((getSaveCommingleCargoMethod = LoadableStudyServiceGrpc.getSaveCommingleCargoMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveCommingleCargoMethod = LoadableStudyServiceGrpc.getSaveCommingleCargoMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveCommingleCargoMethod =
              getSaveCommingleCargoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
                          com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveCommingleCargo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CommingleCargoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveCommingleCargo"))
                      .build();
        }
      }
    }
    return getSaveCommingleCargoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
      getGetLoadablePatternCommingleDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadablePatternCommingleDetails",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest.class,
      responseType =
          com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
      getGetLoadablePatternCommingleDetailsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
        getGetLoadablePatternCommingleDetailsMethod;
    if ((getGetLoadablePatternCommingleDetailsMethod =
            LoadableStudyServiceGrpc.getGetLoadablePatternCommingleDetailsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternCommingleDetailsMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternCommingleDetailsMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternCommingleDetailsMethod =
              getGetLoadablePatternCommingleDetailsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy
                              .LoadablePatternCommingleDetailsRequest,
                          com.cpdss.common.generated.LoadableStudy
                              .LoadablePatternCommingleDetailsReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "GetLoadablePatternCommingleDetails"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadablePatternCommingleDetailsRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadablePatternCommingleDetailsReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadablePatternCommingleDetails"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternCommingleDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getGenerateLoadablePatternsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateLoadablePatterns",
      requestType = com.cpdss.common.generated.LoadableStudy.AlgoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getGenerateLoadablePatternsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.AlgoRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getGenerateLoadablePatternsMethod;
    if ((getGenerateLoadablePatternsMethod =
            LoadableStudyServiceGrpc.getGenerateLoadablePatternsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGenerateLoadablePatternsMethod =
                LoadableStudyServiceGrpc.getGenerateLoadablePatternsMethod)
            == null) {
          LoadableStudyServiceGrpc.getGenerateLoadablePatternsMethod =
              getGenerateLoadablePatternsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.AlgoRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GenerateLoadablePatterns"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GenerateLoadablePatterns"))
                      .build();
        }
      }
    }
    return getGenerateLoadablePatternsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
      getGetLoadablePatternListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadablePatternList",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadablePatternReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
      getGetLoadablePatternListMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
        getGetLoadablePatternListMethod;
    if ((getGetLoadablePatternListMethod = LoadableStudyServiceGrpc.getGetLoadablePatternListMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternListMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternListMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternListMethod =
              getGetLoadablePatternListMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getLoadablePatternList"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePatternReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadablePatternList"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest,
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
      getGetOnBoardQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOnBoardQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest,
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
      getGetOnBoardQuantityMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest,
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
        getGetOnBoardQuantityMethod;
    if ((getGetOnBoardQuantityMethod = LoadableStudyServiceGrpc.getGetOnBoardQuantityMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetOnBoardQuantityMethod = LoadableStudyServiceGrpc.getGetOnBoardQuantityMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetOnBoardQuantityMethod =
              getGetOnBoardQuantityMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest,
                          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetOnBoardQuantity"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetOnBoardQuantity"))
                      .build();
        }
      }
    }
    return getGetOnBoardQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail,
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
      getSaveOnBoardQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveOnBoardQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail.class,
      responseType = com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail,
          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
      getSaveOnBoardQuantityMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail,
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
        getSaveOnBoardQuantityMethod;
    if ((getSaveOnBoardQuantityMethod = LoadableStudyServiceGrpc.getSaveOnBoardQuantityMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveOnBoardQuantityMethod = LoadableStudyServiceGrpc.getSaveOnBoardQuantityMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveOnBoardQuantityMethod =
              getSaveOnBoardQuantityMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail,
                          com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveOnBoardQuantity"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveOnBoardQuantity"))
                      .build();
        }
      }
    }
    return getSaveOnBoardQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
      getSaveAlgoLoadableStudyStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveAlgoLoadableStudyStatus",
      requestType = com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
      getSaveAlgoLoadableStudyStatusMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
        getSaveAlgoLoadableStudyStatusMethod;
    if ((getSaveAlgoLoadableStudyStatusMethod =
            LoadableStudyServiceGrpc.getSaveAlgoLoadableStudyStatusMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveAlgoLoadableStudyStatusMethod =
                LoadableStudyServiceGrpc.getSaveAlgoLoadableStudyStatusMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveAlgoLoadableStudyStatusMethod =
              getSaveAlgoLoadableStudyStatusMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveAlgoLoadableStudyStatus"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoStatusReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "SaveAlgoLoadableStudyStatus"))
                      .build();
        }
      }
    }
    return getSaveAlgoLoadableStudyStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getSaveSynopticalTableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveSynopticalTable",
      requestType = com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getSaveSynopticalTableMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSaveSynopticalTableMethod;
    if ((getSaveSynopticalTableMethod = LoadableStudyServiceGrpc.getSaveSynopticalTableMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveSynopticalTableMethod = LoadableStudyServiceGrpc.getSaveSynopticalTableMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveSynopticalTableMethod =
              getSaveSynopticalTableMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveSynopticalTable"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveSynopticalTable"))
                      .build();
        }
      }
    }
    return getSaveSynopticalTableMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticalTableMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSynopticalTable",
      requestType = com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticalTableMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getGetSynopticalTableMethod;
    if ((getGetSynopticalTableMethod = LoadableStudyServiceGrpc.getGetSynopticalTableMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetSynopticalTableMethod = LoadableStudyServiceGrpc.getGetSynopticalTableMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetSynopticalTableMethod =
              getGetSynopticalTableMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSynopticalTable"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetSynopticalTable"))
                      .build();
        }
      }
    }
    return getGetSynopticalTableMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticalDataByPortIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSynopticalDataByPortId",
      requestType = com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticalDataByPortIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getGetSynopticalDataByPortIdMethod;
    if ((getGetSynopticalDataByPortIdMethod =
            LoadableStudyServiceGrpc.getGetSynopticalDataByPortIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetSynopticalDataByPortIdMethod =
                LoadableStudyServiceGrpc.getGetSynopticalDataByPortIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetSynopticalDataByPortIdMethod =
              getGetSynopticalDataByPortIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetSynopticalDataByPortId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetSynopticalDataByPortId"))
                      .build();
        }
      }
    }
    return getGetSynopticalDataByPortIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticalPortDataByPortIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSynopticalPortDataByPortId",
      requestType = com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticalPortDataByPortIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getGetSynopticalPortDataByPortIdMethod;
    if ((getGetSynopticalPortDataByPortIdMethod =
            LoadableStudyServiceGrpc.getGetSynopticalPortDataByPortIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetSynopticalPortDataByPortIdMethod =
                LoadableStudyServiceGrpc.getGetSynopticalPortDataByPortIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetSynopticalPortDataByPortIdMethod =
              getGetSynopticalPortDataByPortIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetSynopticalPortDataByPortId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetSynopticalPortDataByPortId"))
                      .build();
        }
      }
    }
    return getGetSynopticalPortDataByPortIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
      getGetLoadableStudyStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudyStatus",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
      getGetLoadableStudyStatusMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
        getGetLoadableStudyStatusMethod;
    if ((getGetLoadableStudyStatusMethod = LoadableStudyServiceGrpc.getGetLoadableStudyStatusMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudyStatusMethod =
                LoadableStudyServiceGrpc.getGetLoadableStudyStatusMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudyStatusMethod =
              getGetLoadableStudyStatusMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadableStudyStatus"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadableStudyStatus"))
                      .build();
        }
      }
    }
    return getGetLoadableStudyStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
      getGetLoadablePlanDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadablePlanDetails",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
      getGetLoadablePlanDetailsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
        getGetLoadablePlanDetailsMethod;
    if ((getGetLoadablePlanDetailsMethod = LoadableStudyServiceGrpc.getGetLoadablePlanDetailsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePlanDetailsMethod =
                LoadableStudyServiceGrpc.getGetLoadablePlanDetailsMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePlanDetailsMethod =
              getGetLoadablePlanDetailsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadablePlanDetails"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadablePlanDetails"))
                      .build();
        }
      }
    }
    return getGetLoadablePlanDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
      getConfirmPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConfirmPlan",
      requestType = com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
      getConfirmPlanMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
        getConfirmPlanMethod;
    if ((getConfirmPlanMethod = LoadableStudyServiceGrpc.getConfirmPlanMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getConfirmPlanMethod = LoadableStudyServiceGrpc.getConfirmPlanMethod) == null) {
          LoadableStudyServiceGrpc.getConfirmPlanMethod =
              getConfirmPlanMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
                          com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConfirmPlan"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("ConfirmPlan"))
                      .build();
        }
      }
    }
    return getConfirmPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
      getDownloadLoadableStudyAttachmentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadLoadableStudyAttachment",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
      getDownloadLoadableStudyAttachmentMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
        getDownloadLoadableStudyAttachmentMethod;
    if ((getDownloadLoadableStudyAttachmentMethod =
            LoadableStudyServiceGrpc.getDownloadLoadableStudyAttachmentMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getDownloadLoadableStudyAttachmentMethod =
                LoadableStudyServiceGrpc.getDownloadLoadableStudyAttachmentMethod)
            == null) {
          LoadableStudyServiceGrpc.getDownloadLoadableStudyAttachmentMethod =
              getDownloadLoadableStudyAttachmentMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "DownloadLoadableStudyAttachment"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyAttachmentRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "DownloadLoadableStudyAttachment"))
                      .build();
        }
      }
    }
    return getDownloadLoadableStudyAttachmentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SaveCommentRequest,
          com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
      getSaveCommentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveComment",
      requestType = com.cpdss.common.generated.LoadableStudy.SaveCommentRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SaveCommentReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SaveCommentRequest,
          com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
      getSaveCommentMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SaveCommentRequest,
            com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
        getSaveCommentMethod;
    if ((getSaveCommentMethod = LoadableStudyServiceGrpc.getSaveCommentMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveCommentMethod = LoadableStudyServiceGrpc.getSaveCommentMethod) == null) {
          LoadableStudyServiceGrpc.getSaveCommentMethod =
              getSaveCommentMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SaveCommentRequest,
                          com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveComment"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SaveCommentRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SaveCommentReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveComment"))
                      .build();
        }
      }
    }
    return getSaveCommentMethod;
  }

  /** Creates a new async stub that supports all call types for the service */
  public static LoadableStudyServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceStub>() {
          @java.lang.Override
          public LoadableStudyServiceStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadableStudyServiceStub(channel, callOptions);
          }
        };
    return LoadableStudyServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoadableStudyServiceBlockingStub newBlockingStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceBlockingStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceBlockingStub>() {
          @java.lang.Override
          public LoadableStudyServiceBlockingStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadableStudyServiceBlockingStub(channel, callOptions);
          }
        };
    return LoadableStudyServiceBlockingStub.newStub(factory, channel);
  }

  /** Creates a new ListenableFuture-style stub that supports unary calls on the service */
  public static LoadableStudyServiceFutureStub newFutureStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceFutureStub> factory =
        new io.grpc.stub.AbstractStub.StubFactory<LoadableStudyServiceFutureStub>() {
          @java.lang.Override
          public LoadableStudyServiceFutureStub newStub(
              io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new LoadableStudyServiceFutureStub(channel, callOptions);
          }
        };
    return LoadableStudyServiceFutureStub.newStub(factory, channel);
  }

  /** */
  public abstract static class LoadableStudyServiceImplBase implements io.grpc.BindableService {

    /** */
    public void saveVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveVoyageMethod(), responseObserver);
    }

    /** */
    public void getVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageListReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetVoyagesByVesselMethod(), responseObserver);
    }

    /** */
    public void saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadableQuantityMethod(), responseObserver);
    }

    /** */
    public void findLoadableStudiesByVesselAndVoyage(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(
          getFindLoadableStudiesByVesselAndVoyageMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadableStudyMethod(), responseObserver);
    }

    /** */
    public void saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveCargoNominationMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadableStudyPortRotationMethod(), responseObserver);
    }

    /** */
    public void getCargoNominationById(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetCargoNominationByIdMethod(), responseObserver);
    }

    /** */
    public void getValveSegregation(
        com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetValveSegregationMethod(), responseObserver);
    }

    /** */
    public void getLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadableQuantityMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadableStudyPortRotationMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotationList(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadableStudyPortRotationListMethod(), responseObserver);
    }

    /** */
    public void deleteCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteCargoNominationMethod(), responseObserver);
    }

    /** */
    public void saveDischargingPorts(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveDischargingPortsMethod(), responseObserver);
    }

    /** */
    public void getPortRotationByLoadableStudyId(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPortRotationByLoadableStudyIdMethod(), responseObserver);
    }

    /** */
    public void deleteLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteLoadableStudyMethod(), responseObserver);
    }

    /** */
    public void deletePortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getDeletePortRotationMethod(), responseObserver);
    }

    /** */
    public void getOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetOnHandQuantityMethod(), responseObserver);
    }

    /** */
    public void saveOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveOnHandQuantityMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadablePatternDetailsMethod(), responseObserver);
    }

    /** */
    public void saveLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadablePatternDetailsMethod(), responseObserver);
    }

    /** */
    public void getPurposeOfCommingle(
        com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetPurposeOfCommingleMethod(), responseObserver);
    }

    /** */
    public void getCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetCommingleCargoMethod(), responseObserver);
    }

    /** */
    public void saveCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveCommingleCargoMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternCommingleDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadablePatternCommingleDetailsMethod(), responseObserver);
    }

    /** */
    public void generateLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGenerateLoadablePatternsMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternList(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadablePatternListMethod(), responseObserver);
    }

    /** */
    public void getOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetOnBoardQuantityMethod(), responseObserver);
    }

    /** */
    public void saveOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveOnBoardQuantityMethod(), responseObserver);
    }

    /** */
    public void saveAlgoLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveAlgoLoadableStudyStatusMethod(), responseObserver);
    }

    /** */
    public void saveSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveSynopticalTableMethod(), responseObserver);
    }

    /** */
    public void getSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetSynopticalTableMethod(), responseObserver);
    }

    /** */
    public void getSynopticalDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetSynopticalDataByPortIdMethod(), responseObserver);
    }

    /** */
    public void getSynopticalPortDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetSynopticalPortDataByPortIdMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadableStudyStatusMethod(), responseObserver);
    }

    /** */
    public void getLoadablePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadablePlanDetailsMethod(), responseObserver);
    }

    /** */
    public void confirmPlan(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getConfirmPlanMethod(), responseObserver);
    }

    /** */
    public void downloadLoadableStudyAttachment(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getDownloadLoadableStudyAttachmentMethod(), responseObserver);
    }

    /** */
    public void saveComment(
        com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
            responseObserver) {
      asyncUnimplementedUnaryCall(getSaveCommentMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getSaveVoyageMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageReply>(
                      this, METHODID_SAVE_VOYAGE)))
          .addMethod(
              getGetVoyagesByVesselMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageListReply>(
                      this, METHODID_GET_VOYAGES_BY_VESSEL)))
          .addMethod(
              getSaveLoadableQuantityMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>(
                      this, METHODID_SAVE_LOADABLE_QUANTITY)))
          .addMethod(
              getFindLoadableStudiesByVesselAndVoyageMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>(
                      this, METHODID_FIND_LOADABLE_STUDIES_BY_VESSEL_AND_VOYAGE)))
          .addMethod(
              getSaveLoadableStudyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY)))
          .addMethod(
              getSaveCargoNominationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>(
                      this, METHODID_SAVE_CARGO_NOMINATION)))
          .addMethod(
              getGetLoadableStudyPortRotationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_GET_LOADABLE_STUDY_PORT_ROTATION)))
          .addMethod(
              getGetCargoNominationByIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>(
                      this, METHODID_GET_CARGO_NOMINATION_BY_ID)))
          .addMethod(
              getGetValveSegregationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest,
                      com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>(
                      this, METHODID_GET_VALVE_SEGREGATION)))
          .addMethod(
              getGetLoadableQuantityMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply,
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>(
                      this, METHODID_GET_LOADABLE_QUANTITY)))
          .addMethod(
              getSaveLoadableStudyPortRotationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationDetail,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION)))
          .addMethod(
              getSaveLoadableStudyPortRotationListMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_LIST)))
          .addMethod(
              getDeleteCargoNominationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>(
                      this, METHODID_DELETE_CARGO_NOMINATION)))
          .addMethod(
              getSaveDischargingPortsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_SAVE_DISCHARGING_PORTS)))
          .addMethod(
              getGetPortRotationByLoadableStudyIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_GET_PORT_ROTATION_BY_LOADABLE_STUDY_ID)))
          .addMethod(
              getDeleteLoadableStudyMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>(
                      this, METHODID_DELETE_LOADABLE_STUDY)))
          .addMethod(
              getDeletePortRotationMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_DELETE_PORT_ROTATION)))
          .addMethod(
              getGetOnHandQuantityMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest,
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>(
                      this, METHODID_GET_ON_HAND_QUANTITY)))
          .addMethod(
              getSaveOnHandQuantityMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail,
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>(
                      this, METHODID_SAVE_ON_HAND_QUANTITY)))
          .addMethod(
              getGetLoadablePatternDetailsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_DETAILS)))
          .addMethod(
              getSaveLoadablePatternDetailsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_SAVE_LOADABLE_PATTERN_DETAILS)))
          .addMethod(
              getGetPurposeOfCommingleMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest,
                      com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>(
                      this, METHODID_GET_PURPOSE_OF_COMMINGLE)))
          .addMethod(
              getGetCommingleCargoMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>(
                      this, METHODID_GET_COMMINGLE_CARGO)))
          .addMethod(
              getSaveCommingleCargoMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>(
                      this, METHODID_SAVE_COMMINGLE_CARGO)))
          .addMethod(
              getGetLoadablePatternCommingleDetailsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadablePatternCommingleDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy
                          .LoadablePatternCommingleDetailsReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_COMMINGLE_DETAILS)))
          .addMethod(
              getGenerateLoadablePatternsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_GENERATE_LOADABLE_PATTERNS)))
          .addMethod(
              getGetLoadablePatternListMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_LIST)))
          .addMethod(
              getGetOnBoardQuantityMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest,
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>(
                      this, METHODID_GET_ON_BOARD_QUANTITY)))
          .addMethod(
              getSaveOnBoardQuantityMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail,
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>(
                      this, METHODID_SAVE_ON_BOARD_QUANTITY)))
          .addMethod(
              getSaveAlgoLoadableStudyStatusMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>(
                      this, METHODID_SAVE_ALGO_LOADABLE_STUDY_STATUS)))
          .addMethod(
              getSaveSynopticalTableMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_SAVE_SYNOPTICAL_TABLE)))
          .addMethod(
              getGetSynopticalTableMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTICAL_TABLE)))
          .addMethod(
              getGetSynopticalDataByPortIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTICAL_DATA_BY_PORT_ID)))
          .addMethod(
              getGetSynopticalPortDataByPortIdMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTICAL_PORT_DATA_BY_PORT_ID)))
          .addMethod(
              getGetLoadableStudyStatusMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>(
                      this, METHODID_GET_LOADABLE_STUDY_STATUS)))
          .addMethod(
              getGetLoadablePlanDetailsMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>(
                      this, METHODID_GET_LOADABLE_PLAN_DETAILS)))
          .addMethod(
              getConfirmPlanMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>(
                      this, METHODID_CONFIRM_PLAN)))
          .addMethod(
              getDownloadLoadableStudyAttachmentMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>(
                      this, METHODID_DOWNLOAD_LOADABLE_STUDY_ATTACHMENT)))
          .addMethod(
              getSaveCommentMethod(),
              asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SaveCommentRequest,
                      com.cpdss.common.generated.LoadableStudy.SaveCommentReply>(
                      this, METHODID_SAVE_COMMENT)))
          .build();
    }
  }

  /** */
  public static final class LoadableStudyServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LoadableStudyServiceStub> {
    private LoadableStudyServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadableStudyServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadableStudyServiceStub(channel, callOptions);
    }

    /** */
    public void saveVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveVoyageMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void getVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageListReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVoyagesByVesselMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadableQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void findLoadableStudiesByVesselAndVoyage(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveCargoNominationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudyPortRotationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoNominationById(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCargoNominationByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getValveSegregation(
        com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetValveSegregationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadableQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotationList(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationListMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deleteCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteCargoNominationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveDischargingPorts(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveDischargingPortsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortRotationByLoadableStudyId(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPortRotationByLoadableStudyIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deleteLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteLoadableStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deletePortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeletePortRotationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOnHandQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveOnHandQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadablePatternDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPurposeOfCommingle(
        com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPurposeOfCommingleMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetCommingleCargoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveCommingleCargoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternCommingleDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternCommingleDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void generateLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGenerateLoadablePatternsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternList(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternListMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetOnBoardQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveOnBoardQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveAlgoLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveAlgoLoadableStudyStatusMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetSynopticalDataByPortIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalPortDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetSynopticalPortDataByPortIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudyStatusMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadablePlanDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void confirmPlan(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getConfirmPlanMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void downloadLoadableStudyAttachment(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDownloadLoadableStudyAttachmentMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveComment(
        com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
            responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveCommentMethod(), getCallOptions()),
          request,
          responseObserver);
    }
  }

  /** */
  public static final class LoadableStudyServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LoadableStudyServiceBlockingStub> {
    private LoadableStudyServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadableStudyServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadableStudyServiceBlockingStub(channel, callOptions);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageReply saveVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return blockingUnaryCall(getChannel(), getSaveVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageListReply getVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVoyagesByVesselMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadableQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply
        findLoadableStudiesByVesselAndVoyage(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return blockingUnaryCall(
          getChannel(), getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadableStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveCargoNominationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadableStudyPortRotationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply getCargoNominationById(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCargoNominationByIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.ValveSegregationReply getValveSegregation(
        com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetValveSegregationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse getLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadableQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply saveLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadableStudyPortRotationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply
        saveLoadableStudyPortRotationList(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadableStudyPortRotationListMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply deleteCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteCargoNominationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply saveDischargingPorts(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveDischargingPortsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply
        getPortRotationByLoadableStudyId(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPortRotationByLoadableStudyIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply deleteLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteLoadableStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply deletePortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeletePortRotationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply getOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOnHandQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply saveOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request) {
      return blockingUnaryCall(
          getChannel(), getSaveOnHandQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternReply getLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadablePatternDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply saveLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadablePatternDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply getPurposeOfCommingle(
        com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetPurposeOfCommingleMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CommingleCargoReply getCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetCommingleCargoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CommingleCargoReply saveCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveCommingleCargoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply
        getLoadablePatternCommingleDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest
                request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadablePatternCommingleDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply generateLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request) {
      return blockingUnaryCall(
          getChannel(), getGenerateLoadablePatternsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternReply getLoadablePatternList(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadablePatternListMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply getOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetOnBoardQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply saveOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request) {
      return blockingUnaryCall(
          getChannel(), getSaveOnBoardQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoStatusReply saveAlgoLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveAlgoLoadableStudyStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply saveSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveSynopticalTableMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply getSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetSynopticalTableMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply getSynopticalDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetSynopticalDataByPortIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
        getSynopticalPortDataByPortId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetSynopticalPortDataByPortIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply getLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadableStudyStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply getLoadablePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadablePlanDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply confirmPlan(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request) {
      return blockingUnaryCall(getChannel(), getConfirmPlanMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply
        downloadLoadableStudyAttachment(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request) {
      return blockingUnaryCall(
          getChannel(), getDownloadLoadableStudyAttachmentMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SaveCommentReply saveComment(
        com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request) {
      return blockingUnaryCall(getChannel(), getSaveCommentMethod(), getCallOptions(), request);
    }
  }

  /** */
  public static final class LoadableStudyServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LoadableStudyServiceFutureStub> {
    private LoadableStudyServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LoadableStudyServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LoadableStudyServiceFutureStub(channel, callOptions);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageReply>
        saveVoyage(com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveVoyageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageListReply>
        getVoyagesByVessel(com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVoyagesByVesselMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
        saveLoadableQuantity(
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadableQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        findLoadableStudiesByVesselAndVoyage(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        saveLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        saveCargoNomination(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveCargoNominationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getLoadableStudyPortRotation(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyPortRotationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        getCargoNominationById(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCargoNominationByIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
        getValveSegregation(
            com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetValveSegregationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
        getLoadableQuantity(
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadableQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        saveLoadableStudyPortRotation(
            com.cpdss.common.generated.LoadableStudy.PortRotationDetail request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        saveLoadableStudyPortRotationList(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationListMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        deleteCargoNomination(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteCargoNominationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        saveDischargingPorts(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveDischargingPortsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getPortRotationByLoadableStudyId(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPortRotationByLoadableStudyIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        deleteLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteLoadableStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        deletePortRotation(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeletePortRotationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
        getOnHandQuantity(com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOnHandQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
        saveOnHandQuantity(com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveOnHandQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
        getLoadablePatternDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternDetailsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        saveLoadablePatternDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadablePatternDetailsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
        getPurposeOfCommingle(
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPurposeOfCommingleMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
        getCommingleCargo(com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetCommingleCargoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
        saveCommingleCargo(com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveCommingleCargoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
        getLoadablePatternCommingleDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest
                request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternCommingleDetailsMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        generateLoadablePatterns(com.cpdss.common.generated.LoadableStudy.AlgoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGenerateLoadablePatternsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
        getLoadablePatternList(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternListMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
        getOnBoardQuantity(
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetOnBoardQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
        saveOnBoardQuantity(
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveOnBoardQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
        saveAlgoLoadableStudyStatus(
            com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveAlgoLoadableStudyStatusMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        saveSynopticalTable(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveSynopticalTableMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticalTable(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetSynopticalTableMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticalDataByPortId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetSynopticalDataByPortIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticalPortDataByPortId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetSynopticalPortDataByPortIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
        getLoadableStudyStatus(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyStatusMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
        getLoadablePlanDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadablePlanDetailsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
        confirmPlan(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getConfirmPlanMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
        downloadLoadableStudyAttachment(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDownloadLoadableStudyAttachmentMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
        saveComment(com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveCommentMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_VOYAGE = 0;
  private static final int METHODID_GET_VOYAGES_BY_VESSEL = 1;
  private static final int METHODID_SAVE_LOADABLE_QUANTITY = 2;
  private static final int METHODID_FIND_LOADABLE_STUDIES_BY_VESSEL_AND_VOYAGE = 3;
  private static final int METHODID_SAVE_LOADABLE_STUDY = 4;
  private static final int METHODID_SAVE_CARGO_NOMINATION = 5;
  private static final int METHODID_GET_LOADABLE_STUDY_PORT_ROTATION = 6;
  private static final int METHODID_GET_CARGO_NOMINATION_BY_ID = 7;
  private static final int METHODID_GET_VALVE_SEGREGATION = 8;
  private static final int METHODID_GET_LOADABLE_QUANTITY = 9;
  private static final int METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION = 10;
  private static final int METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_LIST = 11;
  private static final int METHODID_DELETE_CARGO_NOMINATION = 12;
  private static final int METHODID_SAVE_DISCHARGING_PORTS = 13;
  private static final int METHODID_GET_PORT_ROTATION_BY_LOADABLE_STUDY_ID = 14;
  private static final int METHODID_DELETE_LOADABLE_STUDY = 15;
  private static final int METHODID_DELETE_PORT_ROTATION = 16;
  private static final int METHODID_GET_ON_HAND_QUANTITY = 17;
  private static final int METHODID_SAVE_ON_HAND_QUANTITY = 18;
  private static final int METHODID_GET_LOADABLE_PATTERN_DETAILS = 19;
  private static final int METHODID_SAVE_LOADABLE_PATTERN_DETAILS = 20;
  private static final int METHODID_GET_PURPOSE_OF_COMMINGLE = 21;
  private static final int METHODID_GET_COMMINGLE_CARGO = 22;
  private static final int METHODID_SAVE_COMMINGLE_CARGO = 23;
  private static final int METHODID_GET_LOADABLE_PATTERN_COMMINGLE_DETAILS = 24;
  private static final int METHODID_GENERATE_LOADABLE_PATTERNS = 25;
  private static final int METHODID_GET_LOADABLE_PATTERN_LIST = 26;
  private static final int METHODID_GET_ON_BOARD_QUANTITY = 27;
  private static final int METHODID_SAVE_ON_BOARD_QUANTITY = 28;
  private static final int METHODID_SAVE_ALGO_LOADABLE_STUDY_STATUS = 29;
  private static final int METHODID_SAVE_SYNOPTICAL_TABLE = 30;
  private static final int METHODID_GET_SYNOPTICAL_TABLE = 31;
  private static final int METHODID_GET_SYNOPTICAL_DATA_BY_PORT_ID = 32;
  private static final int METHODID_GET_SYNOPTICAL_PORT_DATA_BY_PORT_ID = 33;
  private static final int METHODID_GET_LOADABLE_STUDY_STATUS = 34;
  private static final int METHODID_GET_LOADABLE_PLAN_DETAILS = 35;
  private static final int METHODID_CONFIRM_PLAN = 36;
  private static final int METHODID_DOWNLOAD_LOADABLE_STUDY_ATTACHMENT = 37;
  private static final int METHODID_SAVE_COMMENT = 38;

  private static final class MethodHandlers<Req, Resp>
      implements io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
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
          serviceImpl.saveVoyage(
              (com.cpdss.common.generated.LoadableStudy.VoyageRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageReply>)
                  responseObserver);
          break;
        case METHODID_GET_VOYAGES_BY_VESSEL:
          serviceImpl.getVoyagesByVessel(
              (com.cpdss.common.generated.LoadableStudy.VoyageRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.VoyageListReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_QUANTITY:
          serviceImpl.saveLoadableQuantity(
              (com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>)
                  responseObserver);
          break;
        case METHODID_FIND_LOADABLE_STUDIES_BY_VESSEL_AND_VOYAGE:
          serviceImpl.findLoadableStudiesByVesselAndVoyage(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_STUDY:
          serviceImpl.saveLoadableStudy(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_CARGO_NOMINATION:
          serviceImpl.saveCargoNomination(
              (com.cpdss.common.generated.LoadableStudy.CargoNominationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_PORT_ROTATION:
          serviceImpl.getLoadableStudyPortRotation(
              (com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_NOMINATION_BY_ID:
          serviceImpl.getCargoNominationById(
              (com.cpdss.common.generated.LoadableStudy.CargoNominationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>)
                  responseObserver);
          break;
        case METHODID_GET_VALVE_SEGREGATION:
          serviceImpl.getValveSegregation(
              (com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_QUANTITY:
          serviceImpl.getLoadableQuantity(
              (com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION:
          serviceImpl.saveLoadableStudyPortRotation(
              (com.cpdss.common.generated.LoadableStudy.PortRotationDetail) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_LIST:
          serviceImpl.saveLoadableStudyPortRotationList(
              (com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
          break;
        case METHODID_DELETE_CARGO_NOMINATION:
          serviceImpl.deleteCargoNomination(
              (com.cpdss.common.generated.LoadableStudy.CargoNominationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_DISCHARGING_PORTS:
          serviceImpl.saveDischargingPorts(
              (com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
          break;
        case METHODID_GET_PORT_ROTATION_BY_LOADABLE_STUDY_ID:
          serviceImpl.getPortRotationByLoadableStudyId(
              (com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
          break;
        case METHODID_DELETE_LOADABLE_STUDY:
          serviceImpl.deleteLoadableStudy(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>)
                  responseObserver);
          break;
        case METHODID_DELETE_PORT_ROTATION:
          serviceImpl.deletePortRotation(
              (com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>)
                  responseObserver);
          break;
        case METHODID_GET_ON_HAND_QUANTITY:
          serviceImpl.getOnHandQuantity(
              (com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ON_HAND_QUANTITY:
          serviceImpl.saveOnHandQuantity(
              (com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_DETAILS:
          serviceImpl.getLoadablePatternDetails(
              (com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_PATTERN_DETAILS:
          serviceImpl.saveLoadablePatternDetails(
              (com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>)
                  responseObserver);
          break;
        case METHODID_GET_PURPOSE_OF_COMMINGLE:
          serviceImpl.getPurposeOfCommingle(
              (com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>)
                  responseObserver);
          break;
        case METHODID_GET_COMMINGLE_CARGO:
          serviceImpl.getCommingleCargo(
              (com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_COMMINGLE_CARGO:
          serviceImpl.saveCommingleCargo(
              (com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_COMMINGLE_DETAILS:
          serviceImpl.getLoadablePatternCommingleDetails(
              (com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadablePatternCommingleDetailsReply>)
                  responseObserver);
          break;
        case METHODID_GENERATE_LOADABLE_PATTERNS:
          serviceImpl.generateLoadablePatterns(
              (com.cpdss.common.generated.LoadableStudy.AlgoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_LIST:
          serviceImpl.getLoadablePatternList(
              (com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>)
                  responseObserver);
          break;
        case METHODID_GET_ON_BOARD_QUANTITY:
          serviceImpl.getOnBoardQuantity(
              (com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ON_BOARD_QUANTITY:
          serviceImpl.saveOnBoardQuantity(
              (com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ALGO_LOADABLE_STUDY_STATUS:
          serviceImpl.saveAlgoLoadableStudyStatus(
              (com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_SYNOPTICAL_TABLE:
          serviceImpl.saveSynopticalTable(
              (com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>)
                  responseObserver);
          break;
        case METHODID_GET_SYNOPTICAL_TABLE:
          serviceImpl.getSynopticalTable(
              (com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>)
                  responseObserver);
          break;
        case METHODID_GET_SYNOPTICAL_DATA_BY_PORT_ID:
          serviceImpl.getSynopticalDataByPortId(
              (com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>)
                  responseObserver);
          break;
        case METHODID_GET_SYNOPTICAL_PORT_DATA_BY_PORT_ID:
          serviceImpl.getSynopticalPortDataByPortId(
              (com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_STATUS:
          serviceImpl.getLoadableStudyStatus(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PLAN_DETAILS:
          serviceImpl.getLoadablePlanDetails(
              (com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>)
                  responseObserver);
          break;
        case METHODID_CONFIRM_PLAN:
          serviceImpl.confirmPlan(
              (com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>)
                  responseObserver);
          break;
        case METHODID_DOWNLOAD_LOADABLE_STUDY_ATTACHMENT:
          serviceImpl.downloadLoadableStudyAttachment(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_COMMENT:
          serviceImpl.saveComment(
              (com.cpdss.common.generated.LoadableStudy.SaveCommentRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SaveCommentReply>)
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

  private abstract static class LoadableStudyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier,
          io.grpc.protobuf.ProtoServiceDescriptorSupplier {
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
          serviceDescriptor =
              result =
                  io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                      .setSchemaDescriptor(new LoadableStudyServiceFileDescriptorSupplier())
                      .addMethod(getSaveVoyageMethod())
                      .addMethod(getGetVoyagesByVesselMethod())
                      .addMethod(getSaveLoadableQuantityMethod())
                      .addMethod(getFindLoadableStudiesByVesselAndVoyageMethod())
                      .addMethod(getSaveLoadableStudyMethod())
                      .addMethod(getSaveCargoNominationMethod())
                      .addMethod(getGetLoadableStudyPortRotationMethod())
                      .addMethod(getGetCargoNominationByIdMethod())
                      .addMethod(getGetValveSegregationMethod())
                      .addMethod(getGetLoadableQuantityMethod())
                      .addMethod(getSaveLoadableStudyPortRotationMethod())
                      .addMethod(getSaveLoadableStudyPortRotationListMethod())
                      .addMethod(getDeleteCargoNominationMethod())
                      .addMethod(getSaveDischargingPortsMethod())
                      .addMethod(getGetPortRotationByLoadableStudyIdMethod())
                      .addMethod(getDeleteLoadableStudyMethod())
                      .addMethod(getDeletePortRotationMethod())
                      .addMethod(getGetOnHandQuantityMethod())
                      .addMethod(getSaveOnHandQuantityMethod())
                      .addMethod(getGetLoadablePatternDetailsMethod())
                      .addMethod(getSaveLoadablePatternDetailsMethod())
                      .addMethod(getGetPurposeOfCommingleMethod())
                      .addMethod(getGetCommingleCargoMethod())
                      .addMethod(getSaveCommingleCargoMethod())
                      .addMethod(getGetLoadablePatternCommingleDetailsMethod())
                      .addMethod(getGenerateLoadablePatternsMethod())
                      .addMethod(getGetLoadablePatternListMethod())
                      .addMethod(getGetOnBoardQuantityMethod())
                      .addMethod(getSaveOnBoardQuantityMethod())
                      .addMethod(getSaveAlgoLoadableStudyStatusMethod())
                      .addMethod(getSaveSynopticalTableMethod())
                      .addMethod(getGetSynopticalTableMethod())
                      .addMethod(getGetSynopticalDataByPortIdMethod())
                      .addMethod(getGetSynopticalPortDataByPortIdMethod())
                      .addMethod(getGetLoadableStudyStatusMethod())
                      .addMethod(getGetLoadablePlanDetailsMethod())
                      .addMethod(getConfirmPlanMethod())
                      .addMethod(getDownloadLoadableStudyAttachmentMethod())
                      .addMethod(getSaveCommentMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
