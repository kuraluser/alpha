/* Licensed at AlphaOri Technologies */
package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/** */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: loadable_study.proto")
@io.grpc.stub.annotations.GrpcGenerated
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
      getSaveLoadablePatternsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadablePatterns",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSaveLoadablePatternsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getSaveLoadablePatternsMethod;
    if ((getSaveLoadablePatternsMethod = LoadableStudyServiceGrpc.getSaveLoadablePatternsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadablePatternsMethod = LoadableStudyServiceGrpc.getSaveLoadablePatternsMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadablePatternsMethod =
              getSaveLoadablePatternsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadablePatterns"))
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
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadablePatterns"))
                      .build();
        }
      }
    }
    return getSaveLoadablePatternsMethod;
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
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getValidateLoadablePlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateLoadablePlan",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getValidateLoadablePlanMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getValidateLoadablePlanMethod;
    if ((getValidateLoadablePlanMethod = LoadableStudyServiceGrpc.getValidateLoadablePlanMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getValidateLoadablePlanMethod = LoadableStudyServiceGrpc.getValidateLoadablePlanMethod)
            == null) {
          LoadableStudyServiceGrpc.getValidateLoadablePlanMethod =
              getValidateLoadablePlanMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "ValidateLoadablePlan"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("ValidateLoadablePlan"))
                      .build();
        }
      }
    }
    return getValidateLoadablePlanMethod;
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
          com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest,
          com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
      getGetLoadicatorDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadicatorData",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest,
          com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
      getGetLoadicatorDataMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest,
            com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
        getGetLoadicatorDataMethod;
    if ((getGetLoadicatorDataMethod = LoadableStudyServiceGrpc.getGetLoadicatorDataMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadicatorDataMethod = LoadableStudyServiceGrpc.getGetLoadicatorDataMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadicatorDataMethod =
              getGetLoadicatorDataMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadicatorData"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetLoadicatorData"))
                      .build();
        }
      }
    }
    return getGetLoadicatorDataMethod;
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
          com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSaveLoadicatorResultsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadicatorResults",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSaveLoadicatorResultsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getSaveLoadicatorResultsMethod;
    if ((getSaveLoadicatorResultsMethod = LoadableStudyServiceGrpc.getSaveLoadicatorResultsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadicatorResultsMethod =
                LoadableStudyServiceGrpc.getSaveLoadicatorResultsMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadicatorResultsMethod =
              getSaveLoadicatorResultsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadicatorResults"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadicatorResults"))
                      .build();
        }
      }
    }
    return getSaveLoadicatorResultsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSavePatternValidateResultMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SavePatternValidateResult",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoReply>
      getSavePatternValidateResultMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        getSavePatternValidateResultMethod;
    if ((getSavePatternValidateResultMethod =
            LoadableStudyServiceGrpc.getSavePatternValidateResultMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSavePatternValidateResultMethod =
                LoadableStudyServiceGrpc.getSavePatternValidateResultMethod)
            == null) {
          LoadableStudyServiceGrpc.getSavePatternValidateResultMethod =
              getSavePatternValidateResultMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SavePatternValidateResult"))
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
                              "SavePatternValidateResult"))
                      .build();
        }
      }
    }
    return getSavePatternValidateResultMethod;
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
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticDataByLoadableStudyIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSynopticDataByLoadableStudyId",
      requestType = com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
      getGetSynopticDataByLoadableStudyIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getGetSynopticDataByLoadableStudyIdMethod;
    if ((getGetSynopticDataByLoadableStudyIdMethod =
            LoadableStudyServiceGrpc.getGetSynopticDataByLoadableStudyIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetSynopticDataByLoadableStudyIdMethod =
                LoadableStudyServiceGrpc.getGetSynopticDataByLoadableStudyIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetSynopticDataByLoadableStudyIdMethod =
              getGetSynopticDataByLoadableStudyIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                          com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetSynopticDataByLoadableStudyId"))
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
                              "GetSynopticDataByLoadableStudyId"))
                      .build();
        }
      }
    }
    return getGetSynopticDataByLoadableStudyIdMethod;
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
          com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
          com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
      getUpdateUllageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateUllage",
      requestType = com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.UpdateUllageReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
          com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
      getUpdateUllageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
            com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
        getUpdateUllageMethod;
    if ((getUpdateUllageMethod = LoadableStudyServiceGrpc.getUpdateUllageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getUpdateUllageMethod = LoadableStudyServiceGrpc.getUpdateUllageMethod) == null) {
          LoadableStudyServiceGrpc.getUpdateUllageMethod =
              getUpdateUllageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
                          com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateUllage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.UpdateUllageReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("updateUllage"))
                      .build();
        }
      }
    }
    return getUpdateUllageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
          com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
      getGetUllageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUllage",
      requestType = com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.UpdateUllageReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
          com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
      getGetUllageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
            com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
        getGetUllageMethod;
    if ((getGetUllageMethod = LoadableStudyServiceGrpc.getGetUllageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetUllageMethod = LoadableStudyServiceGrpc.getGetUllageMethod) == null) {
          LoadableStudyServiceGrpc.getGetUllageMethod =
              getGetUllageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
                          com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getUllage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.UpdateUllageReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("getUllage"))
                      .build();
        }
      }
    }
    return getGetUllageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
      getConfirmPlanStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConfirmPlanStatus",
      requestType = com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
          com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
      getConfirmPlanStatusMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
        getConfirmPlanStatusMethod;
    if ((getConfirmPlanStatusMethod = LoadableStudyServiceGrpc.getConfirmPlanStatusMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getConfirmPlanStatusMethod = LoadableStudyServiceGrpc.getConfirmPlanStatusMethod)
            == null) {
          LoadableStudyServiceGrpc.getConfirmPlanStatusMethod =
              getConfirmPlanStatusMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
                          com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConfirmPlanStatus"))
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
                          new LoadableStudyServiceMethodDescriptorSupplier("ConfirmPlanStatus"))
                      .build();
        }
      }
    }
    return getConfirmPlanStatusMethod;
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

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest,
          com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
      getSaveLoadOnTopMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadOnTop",
      requestType = com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SaveCommentReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest,
          com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
      getSaveLoadOnTopMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest,
            com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
        getSaveLoadOnTopMethod;
    if ((getSaveLoadOnTopMethod = LoadableStudyServiceGrpc.getSaveLoadOnTopMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadOnTopMethod = LoadableStudyServiceGrpc.getSaveLoadOnTopMethod) == null) {
          LoadableStudyServiceGrpc.getSaveLoadOnTopMethod =
              getSaveLoadOnTopMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest,
                          com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveLoadOnTop"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SaveCommentReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadOnTop"))
                      .build();
        }
      }
    }
    return getSaveLoadOnTopMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageListReply>
      getGetVoyagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVoyages",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageListReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageListReply>
      getGetVoyagesMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageRequest,
            com.cpdss.common.generated.LoadableStudy.VoyageListReply>
        getGetVoyagesMethod;
    if ((getGetVoyagesMethod = LoadableStudyServiceGrpc.getGetVoyagesMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetVoyagesMethod = LoadableStudyServiceGrpc.getGetVoyagesMethod) == null) {
          LoadableStudyServiceGrpc.getGetVoyagesMethod =
              getGetVoyagesMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                          com.cpdss.common.generated.LoadableStudy.VoyageListReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVoyages"))
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
                          new LoadableStudyServiceMethodDescriptorSupplier("GetVoyages"))
                      .build();
        }
      }
    }
    return getGetVoyagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
      getGetLoadablePlanReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadablePlanReport",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
      getGetLoadablePlanReportMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
        getGetLoadablePlanReportMethod;
    if ((getGetLoadablePlanReportMethod = LoadableStudyServiceGrpc.getGetLoadablePlanReportMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePlanReportMethod =
                LoadableStudyServiceGrpc.getGetLoadablePlanReportMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePlanReportMethod =
              getGetLoadablePlanReportMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadablePlanReport"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetLoadablePlanReport"))
                      .build();
        }
      }
    }
    return getGetLoadablePlanReportMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
      getGetAlgoErrorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAlgoErrors",
      requestType = com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest,
          com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
      getGetAlgoErrorsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest,
            com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
        getGetAlgoErrorsMethod;
    if ((getGetAlgoErrorsMethod = LoadableStudyServiceGrpc.getGetAlgoErrorsMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetAlgoErrorsMethod = LoadableStudyServiceGrpc.getGetAlgoErrorsMethod) == null) {
          LoadableStudyServiceGrpc.getGetAlgoErrorsMethod =
              getGetAlgoErrorsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest,
                          com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAlgoErrors"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoErrorReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetAlgoErrors"))
                      .build();
        }
      }
    }
    return getGetAlgoErrorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest,
          com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
      getSaveVoyageStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveVoyageStatus",
      requestType = com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest,
          com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
      getSaveVoyageStatusMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest,
            com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
        getSaveVoyageStatusMethod;
    if ((getSaveVoyageStatusMethod = LoadableStudyServiceGrpc.getSaveVoyageStatusMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveVoyageStatusMethod = LoadableStudyServiceGrpc.getSaveVoyageStatusMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveVoyageStatusMethod =
              getSaveVoyageStatusMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest,
                          com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveVoyageStatus"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveVoyageStatus"))
                      .build();
        }
      }
    }
    return getSaveVoyageStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
          com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
      getGetCargoApiTempHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoApiTempHistory",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
          com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
      getGetCargoApiTempHistoryMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
            com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
        getGetCargoApiTempHistoryMethod;
    if ((getGetCargoApiTempHistoryMethod = LoadableStudyServiceGrpc.getGetCargoApiTempHistoryMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetCargoApiTempHistoryMethod =
                LoadableStudyServiceGrpc.getGetCargoApiTempHistoryMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetCargoApiTempHistoryMethod =
              getGetCargoApiTempHistoryMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetCargoApiTempHistory"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoHistoryReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetCargoApiTempHistory"))
                      .build();
        }
      }
    }
    return getGetCargoApiTempHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
          com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
      getGetAllCargoHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAllCargoHistory",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
          com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
      getGetAllCargoHistoryMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
            com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
        getGetAllCargoHistoryMethod;
    if ((getGetAllCargoHistoryMethod = LoadableStudyServiceGrpc.getGetAllCargoHistoryMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetAllCargoHistoryMethod = LoadableStudyServiceGrpc.getGetAllCargoHistoryMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetAllCargoHistoryMethod =
              getGetAllCargoHistoryMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAllCargoHistory"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoHistoryReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetAllCargoHistory"))
                      .build();
        }
      }
    }
    return getGetAllCargoHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.JsonRequest,
          com.cpdss.common.generated.LoadableStudy.StatusReply>
      getSaveJsonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveJson",
      requestType = com.cpdss.common.generated.LoadableStudy.JsonRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.StatusReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.JsonRequest,
          com.cpdss.common.generated.LoadableStudy.StatusReply>
      getSaveJsonMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.JsonRequest,
            com.cpdss.common.generated.LoadableStudy.StatusReply>
        getSaveJsonMethod;
    if ((getSaveJsonMethod = LoadableStudyServiceGrpc.getSaveJsonMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveJsonMethod = LoadableStudyServiceGrpc.getSaveJsonMethod) == null) {
          LoadableStudyServiceGrpc.getSaveJsonMethod =
              getSaveJsonMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.JsonRequest,
                          com.cpdss.common.generated.LoadableStudy.StatusReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveJson"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.JsonRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.StatusReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveJson"))
                      .build();
        }
      }
    }
    return getSaveJsonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoErrors,
          com.cpdss.common.generated.LoadableStudy.AlgoErrors>
      getSaveAlgoErrorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveAlgoErrors",
      requestType = com.cpdss.common.generated.LoadableStudy.AlgoErrors.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoErrors.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoErrors,
          com.cpdss.common.generated.LoadableStudy.AlgoErrors>
      getSaveAlgoErrorsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.AlgoErrors,
            com.cpdss.common.generated.LoadableStudy.AlgoErrors>
        getSaveAlgoErrorsMethod;
    if ((getSaveAlgoErrorsMethod = LoadableStudyServiceGrpc.getSaveAlgoErrorsMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveAlgoErrorsMethod = LoadableStudyServiceGrpc.getSaveAlgoErrorsMethod) == null) {
          LoadableStudyServiceGrpc.getSaveAlgoErrorsMethod =
              getSaveAlgoErrorsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.AlgoErrors,
                          com.cpdss.common.generated.LoadableStudy.AlgoErrors>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveAlgoErrors"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoErrors
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoErrors
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("SaveAlgoErrors"))
                      .build();
        }
      }
    }
    return getSaveAlgoErrorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoErrors,
          com.cpdss.common.generated.LoadableStudy.AlgoErrors>
      getFetchAllAlgoErrorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FetchAllAlgoErrors",
      requestType = com.cpdss.common.generated.LoadableStudy.AlgoErrors.class,
      responseType = com.cpdss.common.generated.LoadableStudy.AlgoErrors.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.AlgoErrors,
          com.cpdss.common.generated.LoadableStudy.AlgoErrors>
      getFetchAllAlgoErrorsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.AlgoErrors,
            com.cpdss.common.generated.LoadableStudy.AlgoErrors>
        getFetchAllAlgoErrorsMethod;
    if ((getFetchAllAlgoErrorsMethod = LoadableStudyServiceGrpc.getFetchAllAlgoErrorsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getFetchAllAlgoErrorsMethod = LoadableStudyServiceGrpc.getFetchAllAlgoErrorsMethod)
            == null) {
          LoadableStudyServiceGrpc.getFetchAllAlgoErrorsMethod =
              getFetchAllAlgoErrorsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.AlgoErrors,
                          com.cpdss.common.generated.LoadableStudy.AlgoErrors>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FetchAllAlgoErrors"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoErrors
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.AlgoErrors
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("FetchAllAlgoErrors"))
                      .build();
        }
      }
    }
    return getFetchAllAlgoErrorsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LatestCargoRequest,
          com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
      getGetCargoHistoryByCargoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCargoHistoryByCargo",
      requestType = com.cpdss.common.generated.LoadableStudy.LatestCargoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LatestCargoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LatestCargoRequest,
          com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
      getGetCargoHistoryByCargoMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LatestCargoRequest,
            com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
        getGetCargoHistoryByCargoMethod;
    if ((getGetCargoHistoryByCargoMethod = LoadableStudyServiceGrpc.getGetCargoHistoryByCargoMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetCargoHistoryByCargoMethod =
                LoadableStudyServiceGrpc.getGetCargoHistoryByCargoMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetCargoHistoryByCargoMethod =
              getGetCargoHistoryByCargoMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LatestCargoRequest,
                          com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetCargoHistoryByCargo"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LatestCargoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LatestCargoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetCargoHistoryByCargo"))
                      .build();
        }
      }
    }
    return getGetCargoHistoryByCargoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
      getGetActiveVoyagesByVesselMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetActiveVoyagesByVessel",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.ActiveVoyage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageRequest,
          com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
      getGetActiveVoyagesByVesselMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageRequest,
            com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
        getGetActiveVoyagesByVesselMethod;
    if ((getGetActiveVoyagesByVesselMethod =
            LoadableStudyServiceGrpc.getGetActiveVoyagesByVesselMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetActiveVoyagesByVesselMethod =
                LoadableStudyServiceGrpc.getGetActiveVoyagesByVesselMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetActiveVoyagesByVesselMethod =
              getGetActiveVoyagesByVesselMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                          com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetActiveVoyagesByVessel"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.ActiveVoyage
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetActiveVoyagesByVessel"))
                      .build();
        }
      }
    }
    return getGetActiveVoyagesByVesselMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest,
          com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
      getGetSynopticDataForLoadingPlanMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSynopticDataForLoadingPlan",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest,
          com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
      getGetSynopticDataForLoadingPlanMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest,
            com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
        getGetSynopticDataForLoadingPlanMethod;
    if ((getGetSynopticDataForLoadingPlanMethod =
            LoadableStudyServiceGrpc.getGetSynopticDataForLoadingPlanMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetSynopticDataForLoadingPlanMethod =
                LoadableStudyServiceGrpc.getGetSynopticDataForLoadingPlanMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetSynopticDataForLoadingPlanMethod =
              getGetSynopticDataForLoadingPlanMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetSynopticDataForLoadingPlan"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetSynopticDataForLoadingPlan"))
                      .build();
        }
      }
    }
    return getGetSynopticDataForLoadingPlanMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getSaveLoadingInfoToSynopticDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadingInfoToSynopticData",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getSaveLoadingInfoToSynopticDataMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest,
            com.cpdss.common.generated.Common.ResponseStatus>
        getSaveLoadingInfoToSynopticDataMethod;
    if ((getSaveLoadingInfoToSynopticDataMethod =
            LoadableStudyServiceGrpc.getSaveLoadingInfoToSynopticDataMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadingInfoToSynopticDataMethod =
                LoadableStudyServiceGrpc.getSaveLoadingInfoToSynopticDataMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadingInfoToSynopticDataMethod =
              getSaveLoadingInfoToSynopticDataMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "SaveLoadingInfoToSynopticData"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadingInfoSynopticalUpdateRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "SaveLoadingInfoToSynopticData"))
                      .build();
        }
      }
    }
    return getSaveLoadingInfoToSynopticDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
      getGetOrSaveRulesForLoadableStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetOrSaveRulesForLoadableStudy",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
      getGetOrSaveRulesForLoadableStudyMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
        getGetOrSaveRulesForLoadableStudyMethod;
    if ((getGetOrSaveRulesForLoadableStudyMethod =
            LoadableStudyServiceGrpc.getGetOrSaveRulesForLoadableStudyMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetOrSaveRulesForLoadableStudyMethod =
                LoadableStudyServiceGrpc.getGetOrSaveRulesForLoadableStudyMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetOrSaveRulesForLoadableStudyMethod =
              getGetOrSaveRulesForLoadableStudyMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetOrSaveRulesForLoadableStudy"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableRuleReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetOrSaveRulesForLoadableStudy"))
                      .build();
        }
      }
    }
    return getGetOrSaveRulesForLoadableStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>
      getGetLoadablePatternDetailsJsonMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadablePatternDetailsJson",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.class,
      responseType =
          com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>
      getGetLoadablePatternDetailsJsonMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>
        getGetLoadablePatternDetailsJsonMethod;
    if ((getGetLoadablePatternDetailsJsonMethod =
            LoadableStudyServiceGrpc.getGetLoadablePatternDetailsJsonMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternDetailsJsonMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternDetailsJsonMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternDetailsJsonMethod =
              getGetLoadablePatternDetailsJsonMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                          com.cpdss.common.generated.LoadableStudy
                              .LoadablePatternPortWiseDetailsJson>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadablePatternDetailsJson"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadablePatternPortWiseDetailsJson.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadablePatternDetailsJson"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternDetailsJsonMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
      getGetLoadablePatternByVoyageAndStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadablePatternByVoyageAndStatus",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
          com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
      getGetLoadablePatternByVoyageAndStatusMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
            com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
        getGetLoadablePatternByVoyageAndStatusMethod;
    if ((getGetLoadablePatternByVoyageAndStatusMethod =
            LoadableStudyServiceGrpc.getGetLoadablePatternByVoyageAndStatusMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternByVoyageAndStatusMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternByVoyageAndStatusMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternByVoyageAndStatusMethod =
              getGetLoadablePatternByVoyageAndStatusMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getLoadablePatternByVoyageAndStatus"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadablePatternByVoyageAndStatus"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternByVoyageAndStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
      getGetCargoNominationByCargoNominationIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getCargoNominationByCargoNominationId",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
      getGetCargoNominationByCargoNominationIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
            com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
        getGetCargoNominationByCargoNominationIdMethod;
    if ((getGetCargoNominationByCargoNominationIdMethod =
            LoadableStudyServiceGrpc.getGetCargoNominationByCargoNominationIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetCargoNominationByCargoNominationIdMethod =
                LoadableStudyServiceGrpc.getGetCargoNominationByCargoNominationIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetCargoNominationByCargoNominationIdMethod =
              getGetCargoNominationByCargoNominationIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getCargoNominationByCargoNominationId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getCargoNominationByCargoNominationId"))
                      .build();
        }
      }
    }
    return getGetCargoNominationByCargoNominationIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
      getGetLoadableCommingleByPatternIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadableCommingleByPatternId",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
      getGetLoadableCommingleByPatternIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
        getGetLoadableCommingleByPatternIdMethod;
    if ((getGetLoadableCommingleByPatternIdMethod =
            LoadableStudyServiceGrpc.getGetLoadableCommingleByPatternIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableCommingleByPatternIdMethod =
                LoadableStudyServiceGrpc.getGetLoadableCommingleByPatternIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableCommingleByPatternIdMethod =
              getGetLoadableCommingleByPatternIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getLoadableCommingleByPatternId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadableCommingleByPatternId"))
                      .build();
        }
      }
    }
    return getGetLoadableCommingleByPatternIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
      getGetLoadableStudyShoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudyShore",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
      getGetLoadableStudyShoreMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
        getGetLoadableStudyShoreMethod;
    if ((getGetLoadableStudyShoreMethod = LoadableStudyServiceGrpc.getGetLoadableStudyShoreMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudyShoreMethod =
                LoadableStudyServiceGrpc.getGetLoadableStudyShoreMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudyShoreMethod =
              getGetLoadableStudyShoreMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadableStudyShore"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetLoadableStudyShore"))
                      .build();
        }
      }
    }
    return getGetLoadableStudyShoreMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
      getGetLoadableStudyPortRotationByPortRotationIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudyPortRotationByPortRotationId",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
          com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
      getGetLoadableStudyPortRotationByPortRotationIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
            com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
        getGetLoadableStudyPortRotationByPortRotationIdMethod;
    if ((getGetLoadableStudyPortRotationByPortRotationIdMethod =
            LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationByPortRotationIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudyPortRotationByPortRotationIdMethod =
                LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationByPortRotationIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationByPortRotationIdMethod =
              getGetLoadableStudyPortRotationByPortRotationIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                          com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "GetLoadableStudyPortRotationByPortRotationId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadableStudyPortRotationByPortRotationId"))
                      .build();
        }
      }
    }
    return getGetLoadableStudyPortRotationByPortRotationIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest,
          com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
      getGetLoadableStudySimulatorJsonDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudySimulatorJsonData",
      requestType = com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest,
          com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
      getGetLoadableStudySimulatorJsonDataMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest,
            com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
        getGetLoadableStudySimulatorJsonDataMethod;
    if ((getGetLoadableStudySimulatorJsonDataMethod =
            LoadableStudyServiceGrpc.getGetLoadableStudySimulatorJsonDataMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudySimulatorJsonDataMethod =
                LoadableStudyServiceGrpc.getGetLoadableStudySimulatorJsonDataMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudySimulatorJsonDataMethod =
              getGetLoadableStudySimulatorJsonDataMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest,
                          com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadableStudySimulatorJsonData"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadableStudySimulatorJsonData"))
                      .build();
        }
      }
    }
    return getGetLoadableStudySimulatorJsonDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateDischargeQuantityCargoDetailsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateDischargeQuantityCargoDetails",
      requestType =
          com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest.class,
      responseType = com.cpdss.common.generated.Common.ResponseStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest,
          com.cpdss.common.generated.Common.ResponseStatus>
      getUpdateDischargeQuantityCargoDetailsMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest,
            com.cpdss.common.generated.Common.ResponseStatus>
        getUpdateDischargeQuantityCargoDetailsMethod;
    if ((getUpdateDischargeQuantityCargoDetailsMethod =
            LoadableStudyServiceGrpc.getUpdateDischargeQuantityCargoDetailsMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getUpdateDischargeQuantityCargoDetailsMethod =
                LoadableStudyServiceGrpc.getUpdateDischargeQuantityCargoDetailsMethod)
            == null) {
          LoadableStudyServiceGrpc.getUpdateDischargeQuantityCargoDetailsMethod =
              getUpdateDischargeQuantityCargoDetailsMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy
                              .DischargeQuantityCargoDetailsRequest,
                          com.cpdss.common.generated.Common.ResponseStatus>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "updateDischargeQuantityCargoDetails"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .DischargeQuantityCargoDetailsRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.Common.ResponseStatus
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "updateDischargeQuantityCargoDetails"))
                      .build();
        }
      }
    }
    return getUpdateDischargeQuantityCargoDetailsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest,
          com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
      getGetLoadingSimulatorJsonDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadingSimulatorJsonData",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest,
          com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
      getGetLoadingSimulatorJsonDataMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest,
            com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
        getGetLoadingSimulatorJsonDataMethod;
    if ((getGetLoadingSimulatorJsonDataMethod =
            LoadableStudyServiceGrpc.getGetLoadingSimulatorJsonDataMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadingSimulatorJsonDataMethod =
                LoadableStudyServiceGrpc.getGetLoadingSimulatorJsonDataMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadingSimulatorJsonDataMethod =
              getGetLoadingSimulatorJsonDataMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetLoadingSimulatorJsonData"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "GetLoadingSimulatorJsonData"))
                      .build();
        }
      }
    }
    return getGetLoadingSimulatorJsonDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
      getGetVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVoyage",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageActivateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
      getGetVoyageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
            com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
        getGetVoyageMethod;
    if ((getGetVoyageMethod = LoadableStudyServiceGrpc.getGetVoyageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetVoyageMethod = LoadableStudyServiceGrpc.getGetVoyageMethod) == null) {
          LoadableStudyServiceGrpc.getGetVoyageMethod =
              getGetVoyageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
                          com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetVoyage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageActivateReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetVoyage"))
                      .build();
        }
      }
    }
    return getGetVoyageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
      getSaveActivatedVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveActivatedVoyage",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageActivateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
      getSaveActivatedVoyageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
            com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
        getSaveActivatedVoyageMethod;
    if ((getSaveActivatedVoyageMethod = LoadableStudyServiceGrpc.getSaveActivatedVoyageMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveActivatedVoyageMethod = LoadableStudyServiceGrpc.getSaveActivatedVoyageMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveActivatedVoyageMethod =
              getSaveActivatedVoyageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
                          com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "saveActivatedVoyage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageActivateReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("saveActivatedVoyage"))
                      .build();
        }
      }
    }
    return getSaveActivatedVoyageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
      getGetVoyageByVoyageIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVoyageByVoyageId",
      requestType = com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.VoyageInfoReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest,
          com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
      getGetVoyageByVoyageIdMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest,
            com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
        getGetVoyageByVoyageIdMethod;
    if ((getGetVoyageByVoyageIdMethod = LoadableStudyServiceGrpc.getGetVoyageByVoyageIdMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetVoyageByVoyageIdMethod = LoadableStudyServiceGrpc.getGetVoyageByVoyageIdMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetVoyageByVoyageIdMethod =
              getGetVoyageByVoyageIdMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest,
                          com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "GetVoyageByVoyageId"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.VoyageInfoReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("GetVoyageByVoyageId"))
                      .build();
        }
      }
    }
    return getGetVoyageByVoyageIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
      getGetLoadablePatternForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadablePatternForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest.class,
      responseType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
      getGetLoadablePatternForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
        getGetLoadablePatternForCommunicationMethod;
    if ((getGetLoadablePatternForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetLoadablePatternForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternForCommunicationMethod =
              getGetLoadablePatternForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy
                              .LoadableStudyPatternCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy
                              .LoadableStudyPatternCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getLoadablePatternForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyPatternCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyPatternCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadablePatternForCommunication"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
      getSaveLoadablePatternForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveLoadablePatternForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest.class,
      responseType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
      getSaveLoadablePatternForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
        getSaveLoadablePatternForCommunicationMethod;
    if ((getSaveLoadablePatternForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveLoadablePatternForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadablePatternForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveLoadablePatternForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadablePatternForCommunicationMethod =
              getSaveLoadablePatternForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy
                              .LoadableStudyPatternCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy
                              .LoadableStudyPatternCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveLoadablePatternForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyPatternCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyPatternCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveLoadablePatternForCommunication"))
                      .build();
        }
      }
    }
    return getSaveLoadablePatternForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadicatorDataSynopticalForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadicatorDataSynopticalForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadicatorDataSynopticalForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetLoadicatorDataSynopticalForCommunicationMethod;
    if ((getGetLoadicatorDataSynopticalForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetLoadicatorDataSynopticalForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadicatorDataSynopticalForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetLoadicatorDataSynopticalForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadicatorDataSynopticalForCommunicationMethod =
              getGetLoadicatorDataSynopticalForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getLoadicatorDataSynopticalForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadicatorDataSynopticalForCommunication"))
                      .build();
        }
      }
    }
    return getGetLoadicatorDataSynopticalForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadicatorDataSynopticalForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveLoadicatorDataSynopticalForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadicatorDataSynopticalForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveLoadicatorDataSynopticalForCommunicationMethod;
    if ((getSaveLoadicatorDataSynopticalForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveLoadicatorDataSynopticalForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadicatorDataSynopticalForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveLoadicatorDataSynopticalForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadicatorDataSynopticalForCommunicationMethod =
              getSaveLoadicatorDataSynopticalForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveLoadicatorDataSynopticalForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveLoadicatorDataSynopticalForCommunication"))
                      .build();
        }
      }
    }
    return getSaveLoadicatorDataSynopticalForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetJsonDataForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getJsonDataForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetJsonDataForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetJsonDataForCommunicationMethod;
    if ((getGetJsonDataForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetJsonDataForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetJsonDataForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetJsonDataForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetJsonDataForCommunicationMethod =
              getGetJsonDataForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getJsonDataForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getJsonDataForCommunication"))
                      .build();
        }
      }
    }
    return getGetJsonDataForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveJsonDataForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveJsonDataForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveJsonDataForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveJsonDataForCommunicationMethod;
    if ((getSaveJsonDataForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveJsonDataForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveJsonDataForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveJsonDataForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveJsonDataForCommunicationMethod =
              getSaveJsonDataForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "saveJsonDataForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveJsonDataForCommunication"))
                      .build();
        }
      }
    }
    return getSaveJsonDataForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetSynopticalDataForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getSynopticalDataForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetSynopticalDataForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetSynopticalDataForCommunicationMethod;
    if ((getGetSynopticalDataForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetSynopticalDataForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetSynopticalDataForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetSynopticalDataForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetSynopticalDataForCommunicationMethod =
              getGetSynopticalDataForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getSynopticalDataForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getSynopticalDataForCommunication"))
                      .build();
        }
      }
    }
    return getGetSynopticalDataForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveSynopticalDataForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveSynopticalDataForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveSynopticalDataForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveSynopticalDataForCommunicationMethod;
    if ((getSaveSynopticalDataForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveSynopticalDataForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveSynopticalDataForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveSynopticalDataForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveSynopticalDataForCommunicationMethod =
              getSaveSynopticalDataForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveSynopticalDataForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveSynopticalDataForCommunication"))
                      .build();
        }
      }
    }
    return getSaveSynopticalDataForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadableStudyPortRotationDataForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadableStudyPortRotationDataForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadableStudyPortRotationDataForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetLoadableStudyPortRotationDataForCommunicationMethod;
    if ((getGetLoadableStudyPortRotationDataForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationDataForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudyPortRotationDataForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationDataForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationDataForCommunicationMethod =
              getGetLoadableStudyPortRotationDataForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getLoadableStudyPortRotationDataForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadableStudyPortRotationDataForCommunication"))
                      .build();
        }
      }
    }
    return getGetLoadableStudyPortRotationDataForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadableStudyPortRotationDataForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveLoadableStudyPortRotationDataForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadableStudyPortRotationDataForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveLoadableStudyPortRotationDataForCommunicationMethod;
    if ((getSaveLoadableStudyPortRotationDataForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationDataForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableStudyPortRotationDataForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationDataForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadableStudyPortRotationDataForCommunicationMethod =
              getSaveLoadableStudyPortRotationDataForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveLoadableStudyPortRotationDataForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveLoadableStudyPortRotationDataForCommunication"))
                      .build();
        }
      }
    }
    return getSaveLoadableStudyPortRotationDataForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetJsonDataForDischargePlanCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getJsonDataForDischargePlanCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetJsonDataForDischargePlanCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetJsonDataForDischargePlanCommunicationMethod;
    if ((getGetJsonDataForDischargePlanCommunicationMethod =
            LoadableStudyServiceGrpc.getGetJsonDataForDischargePlanCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetJsonDataForDischargePlanCommunicationMethod =
                LoadableStudyServiceGrpc.getGetJsonDataForDischargePlanCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetJsonDataForDischargePlanCommunicationMethod =
              getGetJsonDataForDischargePlanCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getJsonDataForDischargePlanCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getJsonDataForDischargePlanCommunication"))
                      .build();
        }
      }
    }
    return getGetJsonDataForDischargePlanCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
      getCheckCargoUsageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkCargoUsage",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest,
          com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
      getCheckCargoUsageMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest,
            com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
        getCheckCargoUsageMethod;
    if ((getCheckCargoUsageMethod = LoadableStudyServiceGrpc.getCheckCargoUsageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getCheckCargoUsageMethod = LoadableStudyServiceGrpc.getCheckCargoUsageMethod)
            == null) {
          LoadableStudyServiceGrpc.getCheckCargoUsageMethod =
              getCheckCargoUsageMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest,
                          com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkCargoUsage"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest
                                  .getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply
                                  .getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier("checkCargoUsage"))
                      .build();
        }
      }
    }
    return getCheckCargoUsageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadablePlanStowageBallastDetailsForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadablePlanStowageBallastDetailsForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadablePlanStowageBallastDetailsForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetLoadablePlanStowageBallastDetailsForCommunicationMethod;
    if ((getGetLoadablePlanStowageBallastDetailsForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetLoadablePlanStowageBallastDetailsForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePlanStowageBallastDetailsForCommunicationMethod =
                LoadableStudyServiceGrpc
                    .getGetLoadablePlanStowageBallastDetailsForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePlanStowageBallastDetailsForCommunicationMethod =
              getGetLoadablePlanStowageBallastDetailsForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getLoadablePlanStowageBallastDetailsForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadablePlanStowageBallastDetailsForCommunication"))
                      .build();
        }
      }
    }
    return getGetLoadablePlanStowageBallastDetailsForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveLoadablePlanStowageBallastDetailsForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod;
    if ((getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod =
                LoadableStudyServiceGrpc
                    .getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod =
              getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME,
                              "saveLoadablePlanStowageBallastDetailsForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveLoadablePlanStowageBallastDetailsForCommunication"))
                      .build();
        }
      }
    }
    return getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadablePatternCargoDetailsForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLoadablePatternCargoDetailsForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadablePatternCargoDetailsForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetLoadablePatternCargoDetailsForCommunicationMethod;
    if ((getGetLoadablePatternCargoDetailsForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetLoadablePatternCargoDetailsForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePatternCargoDetailsForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetLoadablePatternCargoDetailsForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetLoadablePatternCargoDetailsForCommunicationMethod =
              getGetLoadablePatternCargoDetailsForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getLoadablePatternCargoDetailsForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadablePatternCargoDetailsForCommunication"))
                      .build();
        }
      }
    }
    return getGetLoadablePatternCargoDetailsForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadablePatternCargoDetailsForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveLoadablePatternCargoDetailsForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadablePatternCargoDetailsForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveLoadablePatternCargoDetailsForCommunicationMethod;
    if ((getSaveLoadablePatternCargoDetailsForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveLoadablePatternCargoDetailsForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadablePatternCargoDetailsForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveLoadablePatternCargoDetailsForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveLoadablePatternCargoDetailsForCommunicationMethod =
              getSaveLoadablePatternCargoDetailsForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveLoadablePatternCargoDetailsForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveLoadablePatternCargoDetailsForCommunication"))
                      .build();
        }
      }
    }
    return getSaveLoadablePatternCargoDetailsForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName =
          SERVICE_NAME + '/' + "getLoadablePlanCommingleDetailsPortwiseForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod;
    if ((getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
            LoadableStudyServiceGrpc
                .getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
                LoadableStudyServiceGrpc
                    .getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc
                  .getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
              getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME,
                              "getLoadablePlanCommingleDetailsPortwiseForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getLoadablePlanCommingleDetailsPortwiseForCommunication"))
                      .build();
        }
      }
    }
    return getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName =
          SERVICE_NAME + '/' + "saveLoadablePlanCommingleDetailsPortwiseForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod;
    if ((getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
            LoadableStudyServiceGrpc
                .getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
                LoadableStudyServiceGrpc
                    .getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc
                  .getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
              getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME,
                              "saveLoadablePlanCommingleDetailsPortwiseForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveLoadablePlanCommingleDetailsPortwiseForCommunication"))
                      .build();
        }
      }
    }
    return getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetOnBoardQuantityForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getOnBoardQuantityForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetOnBoardQuantityForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetOnBoardQuantityForCommunicationMethod;
    if ((getGetOnBoardQuantityForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetOnBoardQuantityForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetOnBoardQuantityForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetOnBoardQuantityForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetOnBoardQuantityForCommunicationMethod =
              getGetOnBoardQuantityForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "getOnBoardQuantityForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getOnBoardQuantityForCommunication"))
                      .build();
        }
      }
    }
    return getGetOnBoardQuantityForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveOnBoardQuantityForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveOnBoardQuantityForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveOnBoardQuantityForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveOnBoardQuantityForCommunicationMethod;
    if ((getSaveOnBoardQuantityForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveOnBoardQuantityForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveOnBoardQuantityForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveOnBoardQuantityForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveOnBoardQuantityForCommunicationMethod =
              getSaveOnBoardQuantityForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveOnBoardQuantityForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveOnBoardQuantityForCommunication"))
                      .build();
        }
      }
    }
    return getSaveOnBoardQuantityForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetOnHandQuantityForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getOnHandQuantityForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getGetOnHandQuantityForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getGetOnHandQuantityForCommunicationMethod;
    if ((getGetOnHandQuantityForCommunicationMethod =
            LoadableStudyServiceGrpc.getGetOnHandQuantityForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetOnHandQuantityForCommunicationMethod =
                LoadableStudyServiceGrpc.getGetOnHandQuantityForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getGetOnHandQuantityForCommunicationMethod =
              getGetOnHandQuantityForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(SERVICE_NAME, "getOnHandQuantityForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "getOnHandQuantityForCommunication"))
                      .build();
        }
      }
    }
    return getGetOnHandQuantityForCommunicationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveOnHandQuantityForCommunicationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "saveOnHandQuantityForCommunication",
      requestType =
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
      getSaveOnHandQuantityForCommunicationMethod() {
    io.grpc.MethodDescriptor<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSaveOnHandQuantityForCommunicationMethod;
    if ((getSaveOnHandQuantityForCommunicationMethod =
            LoadableStudyServiceGrpc.getSaveOnHandQuantityForCommunicationMethod)
        == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveOnHandQuantityForCommunicationMethod =
                LoadableStudyServiceGrpc.getSaveOnHandQuantityForCommunicationMethod)
            == null) {
          LoadableStudyServiceGrpc.getSaveOnHandQuantityForCommunicationMethod =
              getSaveOnHandQuantityForCommunicationMethod =
                  io.grpc.MethodDescriptor
                      .<com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                          com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
                          newBuilder()
                      .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                      .setFullMethodName(
                          generateFullMethodName(
                              SERVICE_NAME, "saveOnHandQuantityForCommunication"))
                      .setSampledToLocalTracing(true)
                      .setRequestMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationRequest.getDefaultInstance()))
                      .setResponseMarshaller(
                          io.grpc.protobuf.ProtoUtils.marshaller(
                              com.cpdss.common.generated.LoadableStudy
                                  .LoadableStudyCommunicationReply.getDefaultInstance()))
                      .setSchemaDescriptor(
                          new LoadableStudyServiceMethodDescriptorSupplier(
                              "saveOnHandQuantityForCommunication"))
                      .build();
        }
      }
    }
    return getSaveOnHandQuantityForCommunicationMethod;
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
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveVoyageMethod(), responseObserver);
    }

    /** */
    public void getVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageListReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVoyagesByVesselMethod(), responseObserver);
    }

    /** */
    public void saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadableQuantityMethod(), responseObserver);
    }

    /** */
    public void findLoadableStudiesByVesselAndVoyage(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getFindLoadableStudiesByVesselAndVoyageMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadableStudyMethod(), responseObserver);
    }

    /** */
    public void saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveCargoNominationMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableStudyPortRotationMethod(), responseObserver);
    }

    /** */
    public void getCargoNominationById(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetCargoNominationByIdMethod(), responseObserver);
    }

    /** */
    public void getValveSegregation(
        com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetValveSegregationMethod(), responseObserver);
    }

    /** */
    public void getLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableQuantityMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadableStudyPortRotationMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotationList(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadableStudyPortRotationListMethod(), responseObserver);
    }

    /** */
    public void deleteCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getDeleteCargoNominationMethod(), responseObserver);
    }

    /** */
    public void saveDischargingPorts(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveDischargingPortsMethod(), responseObserver);
    }

    /** */
    public void getPortRotationByLoadableStudyId(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetPortRotationByLoadableStudyIdMethod(), responseObserver);
    }

    /** */
    public void deleteLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getDeleteLoadableStudyMethod(), responseObserver);
    }

    /** */
    public void deletePortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getDeletePortRotationMethod(), responseObserver);
    }

    /** */
    public void getOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetOnHandQuantityMethod(), responseObserver);
    }

    /** */
    public void saveOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveOnHandQuantityMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternDetailsMethod(), responseObserver);
    }

    /** */
    public void saveLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadablePatternsMethod(), responseObserver);
    }

    /** */
    public void getPurposeOfCommingle(
        com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetPurposeOfCommingleMethod(), responseObserver);
    }

    /** */
    public void getCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetCommingleCargoMethod(), responseObserver);
    }

    /** */
    public void saveCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveCommingleCargoMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternCommingleDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternCommingleDetailsMethod(), responseObserver);
    }

    /** */
    public void generateLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGenerateLoadablePatternsMethod(), responseObserver);
    }

    /** */
    public void validateLoadablePlan(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getValidateLoadablePlanMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternList(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternListMethod(), responseObserver);
    }

    /** */
    public void getOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetOnBoardQuantityMethod(), responseObserver);
    }

    /** */
    public void saveOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveOnBoardQuantityMethod(), responseObserver);
    }

    /** */
    public void getLoadicatorData(
        com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadicatorDataMethod(), responseObserver);
    }

    /** */
    public void saveAlgoLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveAlgoLoadableStudyStatusMethod(), responseObserver);
    }

    /** */
    public void saveLoadicatorResults(
        com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadicatorResultsMethod(), responseObserver);
    }

    /** */
    public void savePatternValidateResult(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSavePatternValidateResultMethod(), responseObserver);
    }

    /** */
    public void saveSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveSynopticalTableMethod(), responseObserver);
    }

    /** */
    public void getSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetSynopticalTableMethod(), responseObserver);
    }

    /** */
    public void getSynopticalDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetSynopticalDataByPortIdMethod(), responseObserver);
    }

    /** */
    public void getSynopticalPortDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetSynopticalPortDataByPortIdMethod(), responseObserver);
    }

    /** */
    public void getSynopticDataByLoadableStudyId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetSynopticDataByLoadableStudyIdMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableStudyStatusMethod(), responseObserver);
    }

    /** */
    public void getLoadablePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePlanDetailsMethod(), responseObserver);
    }

    /** */
    public void updateUllage(
        com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getUpdateUllageMethod(), responseObserver);
    }

    /** */
    public void getUllage(
        com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUllageMethod(), responseObserver);
    }

    /** */
    public void confirmPlanStatus(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getConfirmPlanStatusMethod(), responseObserver);
    }

    /** */
    public void confirmPlan(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getConfirmPlanMethod(), responseObserver);
    }

    /** */
    public void downloadLoadableStudyAttachment(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getDownloadLoadableStudyAttachmentMethod(), responseObserver);
    }

    /** */
    public void saveComment(
        com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveCommentMethod(), responseObserver);
    }

    /** */
    public void saveLoadOnTop(
        com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadOnTopMethod(), responseObserver);
    }

    /** */
    public void getVoyages(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageListReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVoyagesMethod(), responseObserver);
    }

    /**
     *
     *
     * <pre>
     * Generate loadable plan excel report
     * </pre>
     */
    public void getLoadablePlanReport(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePlanReportMethod(), responseObserver);
    }

    /** */
    public void getAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetAlgoErrorsMethod(), responseObserver);
    }

    /** */
    public void saveVoyageStatus(
        com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveVoyageStatusMethod(), responseObserver);
    }

    /** */
    public void getCargoApiTempHistory(
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetCargoApiTempHistoryMethod(), responseObserver);
    }

    /** */
    public void getAllCargoHistory(
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetAllCargoHistoryMethod(), responseObserver);
    }

    /** */
    public void saveJson(
        com.cpdss.common.generated.LoadableStudy.JsonRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.StatusReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveJsonMethod(), responseObserver);
    }

    /** */
    public void saveAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveAlgoErrorsMethod(), responseObserver);
    }

    /** */
    public void fetchAllAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getFetchAllAlgoErrorsMethod(), responseObserver);
    }

    /** */
    public void getCargoHistoryByCargo(
        com.cpdss.common.generated.LoadableStudy.LatestCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetCargoHistoryByCargoMethod(), responseObserver);
    }

    /** */
    public void getActiveVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetActiveVoyagesByVesselMethod(), responseObserver);
    }

    /**
     *
     *
     * <pre>
     * For both Loading and Discharge
     * </pre>
     */
    public void getSynopticDataForLoadingPlan(
        com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetSynopticDataForLoadingPlanMethod(), responseObserver);
    }

    /** */
    public void saveLoadingInfoToSynopticData(
        com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadingInfoToSynopticDataMethod(), responseObserver);
    }

    /** */
    public void getOrSaveRulesForLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetOrSaveRulesForLoadableStudyMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternDetailsJson(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternDetailsJsonMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternByVoyageAndStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternByVoyageAndStatusMethod(), responseObserver);
    }

    /** */
    public void getCargoNominationByCargoNominationId(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetCargoNominationByCargoNominationIdMethod(), responseObserver);
    }

    /** */
    public void getLoadableCommingleByPatternId(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableCommingleByPatternIdMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyShore(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableStudyShoreMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotationByPortRotationId(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableStudyPortRotationByPortRotationIdMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudySimulatorJsonData(
        com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableStudySimulatorJsonDataMethod(), responseObserver);
    }

    /** */
    public void updateDischargeQuantityCargoDetails(
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getUpdateDischargeQuantityCargoDetailsMethod(), responseObserver);
    }

    /** */
    public void getLoadingSimulatorJsonData(
        com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadingSimulatorJsonDataMethod(), responseObserver);
    }

    /** */
    public void getVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVoyageMethod(), responseObserver);
    }

    /** */
    public void saveActivatedVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveActivatedVoyageMethod(), responseObserver);
    }

    /** */
    public void getVoyageByVoyageId(
        com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetVoyageByVoyageIdMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveLoadablePatternForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadablePatternForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getLoadicatorDataSynopticalForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadicatorDataSynopticalForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveLoadicatorDataSynopticalForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadicatorDataSynopticalForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getJsonDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetJsonDataForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveJsonDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveJsonDataForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getSynopticalDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetSynopticalDataForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveSynopticalDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveSynopticalDataForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotationDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadableStudyPortRotationDataForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotationDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadableStudyPortRotationDataForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getJsonDataForDischargePlanCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetJsonDataForDischargePlanCommunicationMethod(), responseObserver);
    }

    /** */
    public void checkCargoUsage(
        com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getCheckCargoUsageMethod(), responseObserver);
    }

    /** */
    public void getLoadablePlanStowageBallastDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePlanStowageBallastDetailsForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveLoadablePlanStowageBallastDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getLoadablePatternCargoDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePatternCargoDetailsForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveLoadablePatternCargoDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadablePatternCargoDetailsForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getLoadablePlanCommingleDetailsPortwiseForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveLoadablePlanCommingleDetailsPortwiseForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getOnBoardQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetOnBoardQuantityForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveOnBoardQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveOnBoardQuantityForCommunicationMethod(), responseObserver);
    }

    /** */
    public void getOnHandQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getGetOnHandQuantityForCommunicationMethod(), responseObserver);
    }

    /** */
    public void saveOnHandQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(
          getSaveOnHandQuantityForCommunicationMethod(), responseObserver);
    }

    @java.lang.Override
    public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
              getSaveVoyageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageReply>(
                      this, METHODID_SAVE_VOYAGE)))
          .addMethod(
              getGetVoyagesByVesselMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageListReply>(
                      this, METHODID_GET_VOYAGES_BY_VESSEL)))
          .addMethod(
              getSaveLoadableQuantityMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>(
                      this, METHODID_SAVE_LOADABLE_QUANTITY)))
          .addMethod(
              getFindLoadableStudiesByVesselAndVoyageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>(
                      this, METHODID_FIND_LOADABLE_STUDIES_BY_VESSEL_AND_VOYAGE)))
          .addMethod(
              getSaveLoadableStudyMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY)))
          .addMethod(
              getSaveCargoNominationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>(
                      this, METHODID_SAVE_CARGO_NOMINATION)))
          .addMethod(
              getGetLoadableStudyPortRotationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_GET_LOADABLE_STUDY_PORT_ROTATION)))
          .addMethod(
              getGetCargoNominationByIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>(
                      this, METHODID_GET_CARGO_NOMINATION_BY_ID)))
          .addMethod(
              getGetValveSegregationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest,
                      com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>(
                      this, METHODID_GET_VALVE_SEGREGATION)))
          .addMethod(
              getGetLoadableQuantityMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply,
                      com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>(
                      this, METHODID_GET_LOADABLE_QUANTITY)))
          .addMethod(
              getSaveLoadableStudyPortRotationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationDetail,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION)))
          .addMethod(
              getSaveLoadableStudyPortRotationListMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_LIST)))
          .addMethod(
              getDeleteCargoNominationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationReply>(
                      this, METHODID_DELETE_CARGO_NOMINATION)))
          .addMethod(
              getSaveDischargingPortsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_SAVE_DISCHARGING_PORTS)))
          .addMethod(
              getGetPortRotationByLoadableStudyIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_GET_PORT_ROTATION_BY_LOADABLE_STUDY_ID)))
          .addMethod(
              getDeleteLoadableStudyMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>(
                      this, METHODID_DELETE_LOADABLE_STUDY)))
          .addMethod(
              getDeletePortRotationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationReply>(
                      this, METHODID_DELETE_PORT_ROTATION)))
          .addMethod(
              getGetOnHandQuantityMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest,
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>(
                      this, METHODID_GET_ON_HAND_QUANTITY)))
          .addMethod(
              getSaveOnHandQuantityMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail,
                      com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>(
                      this, METHODID_SAVE_ON_HAND_QUANTITY)))
          .addMethod(
              getGetLoadablePatternDetailsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_DETAILS)))
          .addMethod(
              getSaveLoadablePatternsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_SAVE_LOADABLE_PATTERNS)))
          .addMethod(
              getGetPurposeOfCommingleMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest,
                      com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>(
                      this, METHODID_GET_PURPOSE_OF_COMMINGLE)))
          .addMethod(
              getGetCommingleCargoMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>(
                      this, METHODID_GET_COMMINGLE_CARGO)))
          .addMethod(
              getSaveCommingleCargoMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest,
                      com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>(
                      this, METHODID_SAVE_COMMINGLE_CARGO)))
          .addMethod(
              getGetLoadablePatternCommingleDetailsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadablePatternCommingleDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy
                          .LoadablePatternCommingleDetailsReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_COMMINGLE_DETAILS)))
          .addMethod(
              getGenerateLoadablePatternsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_GENERATE_LOADABLE_PATTERNS)))
          .addMethod(
              getValidateLoadablePlanMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_VALIDATE_LOADABLE_PLAN)))
          .addMethod(
              getGetLoadablePatternListMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_LIST)))
          .addMethod(
              getGetOnBoardQuantityMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest,
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>(
                      this, METHODID_GET_ON_BOARD_QUANTITY)))
          .addMethod(
              getSaveOnBoardQuantityMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail,
                      com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>(
                      this, METHODID_SAVE_ON_BOARD_QUANTITY)))
          .addMethod(
              getGetLoadicatorDataMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>(
                      this, METHODID_GET_LOADICATOR_DATA)))
          .addMethod(
              getSaveAlgoLoadableStudyStatusMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>(
                      this, METHODID_SAVE_ALGO_LOADABLE_STUDY_STATUS)))
          .addMethod(
              getSaveLoadicatorResultsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_SAVE_LOADICATOR_RESULTS)))
          .addMethod(
              getSavePatternValidateResultMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoReply>(
                      this, METHODID_SAVE_PATTERN_VALIDATE_RESULT)))
          .addMethod(
              getSaveSynopticalTableMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_SAVE_SYNOPTICAL_TABLE)))
          .addMethod(
              getGetSynopticalTableMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTICAL_TABLE)))
          .addMethod(
              getGetSynopticalDataByPortIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTICAL_DATA_BY_PORT_ID)))
          .addMethod(
              getGetSynopticalPortDataByPortIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTICAL_PORT_DATA_BY_PORT_ID)))
          .addMethod(
              getGetSynopticDataByLoadableStudyIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest,
                      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>(
                      this, METHODID_GET_SYNOPTIC_DATA_BY_LOADABLE_STUDY_ID)))
          .addMethod(
              getGetLoadableStudyStatusMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>(
                      this, METHODID_GET_LOADABLE_STUDY_STATUS)))
          .addMethod(
              getGetLoadablePlanDetailsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>(
                      this, METHODID_GET_LOADABLE_PLAN_DETAILS)))
          .addMethod(
              getUpdateUllageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
                      com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>(
                      this, METHODID_UPDATE_ULLAGE)))
          .addMethod(
              getGetUllageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest,
                      com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>(
                      this, METHODID_GET_ULLAGE)))
          .addMethod(
              getConfirmPlanStatusMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>(
                      this, METHODID_CONFIRM_PLAN_STATUS)))
          .addMethod(
              getConfirmPlanMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest,
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>(
                      this, METHODID_CONFIRM_PLAN)))
          .addMethod(
              getDownloadLoadableStudyAttachmentMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>(
                      this, METHODID_DOWNLOAD_LOADABLE_STUDY_ATTACHMENT)))
          .addMethod(
              getSaveCommentMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SaveCommentRequest,
                      com.cpdss.common.generated.LoadableStudy.SaveCommentReply>(
                      this, METHODID_SAVE_COMMENT)))
          .addMethod(
              getSaveLoadOnTopMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest,
                      com.cpdss.common.generated.LoadableStudy.SaveCommentReply>(
                      this, METHODID_SAVE_LOAD_ON_TOP)))
          .addMethod(
              getGetVoyagesMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageListReply>(
                      this, METHODID_GET_VOYAGES)))
          .addMethod(
              getGetLoadablePlanReportMethod(),
              io.grpc.stub.ServerCalls.asyncServerStreamingCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>(
                      this, METHODID_GET_LOADABLE_PLAN_REPORT)))
          .addMethod(
              getGetAlgoErrorsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest,
                      com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>(
                      this, METHODID_GET_ALGO_ERRORS)))
          .addMethod(
              getSaveVoyageStatusMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest,
                      com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>(
                      this, METHODID_SAVE_VOYAGE_STATUS)))
          .addMethod(
              getGetCargoApiTempHistoryMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>(
                      this, METHODID_GET_CARGO_API_TEMP_HISTORY)))
          .addMethod(
              getGetAllCargoHistoryMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>(
                      this, METHODID_GET_ALL_CARGO_HISTORY)))
          .addMethod(
              getSaveJsonMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.JsonRequest,
                      com.cpdss.common.generated.LoadableStudy.StatusReply>(
                      this, METHODID_SAVE_JSON)))
          .addMethod(
              getSaveAlgoErrorsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoErrors,
                      com.cpdss.common.generated.LoadableStudy.AlgoErrors>(
                      this, METHODID_SAVE_ALGO_ERRORS)))
          .addMethod(
              getFetchAllAlgoErrorsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.AlgoErrors,
                      com.cpdss.common.generated.LoadableStudy.AlgoErrors>(
                      this, METHODID_FETCH_ALL_ALGO_ERRORS)))
          .addMethod(
              getGetCargoHistoryByCargoMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LatestCargoRequest,
                      com.cpdss.common.generated.LoadableStudy.LatestCargoReply>(
                      this, METHODID_GET_CARGO_HISTORY_BY_CARGO)))
          .addMethod(
              getGetActiveVoyagesByVesselMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageRequest,
                      com.cpdss.common.generated.LoadableStudy.ActiveVoyage>(
                      this, METHODID_GET_ACTIVE_VOYAGES_BY_VESSEL)))
          .addMethod(
              getGetSynopticDataForLoadingPlanMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>(
                      this, METHODID_GET_SYNOPTIC_DATA_FOR_LOADING_PLAN)))
          .addMethod(
              getSaveLoadingInfoToSynopticDataMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_SAVE_LOADING_INFO_TO_SYNOPTIC_DATA)))
          .addMethod(
              getGetOrSaveRulesForLoadableStudyMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>(
                      this, METHODID_GET_OR_SAVE_RULES_FOR_LOADABLE_STUDY)))
          .addMethod(
              getGetLoadablePatternDetailsJsonMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>(
                      this, METHODID_GET_LOADABLE_PATTERN_DETAILS_JSON)))
          .addMethod(
              getGetLoadablePatternByVoyageAndStatusMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_BY_VOYAGE_AND_STATUS)))
          .addMethod(
              getGetCargoNominationByCargoNominationIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>(
                      this, METHODID_GET_CARGO_NOMINATION_BY_CARGO_NOMINATION_ID)))
          .addMethod(
              getGetLoadableCommingleByPatternIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>(
                      this, METHODID_GET_LOADABLE_COMMINGLE_BY_PATTERN_ID)))
          .addMethod(
              getGetLoadableStudyShoreMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>(
                      this, METHODID_GET_LOADABLE_STUDY_SHORE)))
          .addMethod(
              getGetLoadableStudyPortRotationByPortRotationIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
                      com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>(
                      this, METHODID_GET_LOADABLE_STUDY_PORT_ROTATION_BY_PORT_ROTATION_ID)))
          .addMethod(
              getGetLoadableStudySimulatorJsonDataMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest,
                      com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>(
                      this, METHODID_GET_LOADABLE_STUDY_SIMULATOR_JSON_DATA)))
          .addMethod(
              getUpdateDischargeQuantityCargoDetailsMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest,
                      com.cpdss.common.generated.Common.ResponseStatus>(
                      this, METHODID_UPDATE_DISCHARGE_QUANTITY_CARGO_DETAILS)))
          .addMethod(
              getGetLoadingSimulatorJsonDataMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>(
                      this, METHODID_GET_LOADING_SIMULATOR_JSON_DATA)))
          .addMethod(
              getGetVoyageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>(
                      this, METHODID_GET_VOYAGE)))
          .addMethod(
              getSaveActivatedVoyageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>(
                      this, METHODID_SAVE_ACTIVATED_VOYAGE)))
          .addMethod(
              getGetVoyageByVoyageIdMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest,
                      com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>(
                      this, METHODID_GET_VOYAGE_BY_VOYAGE_ID)))
          .addMethod(
              getGetLoadablePatternForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadableStudyPatternCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy
                          .LoadableStudyPatternCommunicationReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_FOR_COMMUNICATION)))
          .addMethod(
              getSaveLoadablePatternForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadableStudyPatternCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy
                          .LoadableStudyPatternCommunicationReply>(
                      this, METHODID_SAVE_LOADABLE_PATTERN_FOR_COMMUNICATION)))
          .addMethod(
              getGetLoadicatorDataSynopticalForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_LOADICATOR_DATA_SYNOPTICAL_FOR_COMMUNICATION)))
          .addMethod(
              getSaveLoadicatorDataSynopticalForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_LOADICATOR_DATA_SYNOPTICAL_FOR_COMMUNICATION)))
          .addMethod(
              getGetJsonDataForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_JSON_DATA_FOR_COMMUNICATION)))
          .addMethod(
              getSaveJsonDataForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_JSON_DATA_FOR_COMMUNICATION)))
          .addMethod(
              getGetSynopticalDataForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_SYNOPTICAL_DATA_FOR_COMMUNICATION)))
          .addMethod(
              getSaveSynopticalDataForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_SYNOPTICAL_DATA_FOR_COMMUNICATION)))
          .addMethod(
              getGetLoadableStudyPortRotationDataForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_LOADABLE_STUDY_PORT_ROTATION_DATA_FOR_COMMUNICATION)))
          .addMethod(
              getSaveLoadableStudyPortRotationDataForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_DATA_FOR_COMMUNICATION)))
          .addMethod(
              getGetJsonDataForDischargePlanCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_JSON_DATA_FOR_DISCHARGE_PLAN_COMMUNICATION)))
          .addMethod(
              getCheckCargoUsageMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest,
                      com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>(
                      this, METHODID_CHECK_CARGO_USAGE)))
          .addMethod(
              getGetLoadablePlanStowageBallastDetailsForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS_FOR_COMMUNICATION)))
          .addMethod(
              getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS_FOR_COMMUNICATION)))
          .addMethod(
              getGetLoadablePatternCargoDetailsForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_LOADABLE_PATTERN_CARGO_DETAILS_FOR_COMMUNICATION)))
          .addMethod(
              getSaveLoadablePatternCargoDetailsForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_LOADABLE_PATTERN_CARGO_DETAILS_FOR_COMMUNICATION)))
          .addMethod(
              getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this,
                      METHODID_GET_LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE_FOR_COMMUNICATION)))
          .addMethod(
              getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this,
                      METHODID_SAVE_LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE_FOR_COMMUNICATION)))
          .addMethod(
              getGetOnBoardQuantityForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_ON_BOARD_QUANTITY_FOR_COMMUNICATION)))
          .addMethod(
              getSaveOnBoardQuantityForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_ON_BOARD_QUANTITY_FOR_COMMUNICATION)))
          .addMethod(
              getGetOnHandQuantityForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_GET_ON_HAND_QUANTITY_FOR_COMMUNICATION)))
          .addMethod(
              getSaveOnHandQuantityForCommunicationMethod(),
              io.grpc.stub.ServerCalls.asyncUnaryCall(
                  new MethodHandlers<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest,
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>(
                      this, METHODID_SAVE_ON_HAND_QUANTITY_FOR_COMMUNICATION)))
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
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveVoyageMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void getVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageListReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVoyagesByVesselMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadableQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void findLoadableStudiesByVesselAndVoyage(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveCargoNominationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudyPortRotationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoNominationById(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCargoNominationByIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getValveSegregation(
        com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
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
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadableQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotationList(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationListMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deleteCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteCargoNominationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveDischargingPorts(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveDischargingPortsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPortRotationByLoadableStudyId(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPortRotationByLoadableStudyIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deleteLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteLoadableStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void deletePortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeletePortRotationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOnHandQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveOnHandQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadablePatternsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getPurposeOfCommingle(
        com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetPurposeOfCommingleMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCommingleCargoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
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
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternCommingleDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void generateLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateLoadablePatternsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void validateLoadablePlan(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getValidateLoadablePlanMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternList(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternListMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOnBoardQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveOnBoardQuantityMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadicatorData(
        com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadicatorDataMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveAlgoLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveAlgoLoadableStudyStatusMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadicatorResults(
        com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadicatorResultsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void savePatternValidateResult(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSavePatternValidateResultMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSynopticalTableMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSynopticalDataByPortIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalPortDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSynopticalPortDataByPortIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticDataByLoadableStudyId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSynopticDataByLoadableStudyIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
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
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePlanDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void updateUllage(
        com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateUllageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getUllage(
        com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUllageMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void confirmPlanStatus(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConfirmPlanStatusMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void confirmPlan(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
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
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDownloadLoadableStudyAttachmentMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveComment(
        com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveCommentMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadOnTop(
        com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadOnTopMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVoyages(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageListReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVoyagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     *
     *
     * <pre>
     * Generate loadable plan excel report
     * </pre>
     */
    public void getLoadablePlanReport(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetLoadablePlanReportMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAlgoErrorsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveVoyageStatus(
        com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveVoyageStatusMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoApiTempHistory(
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCargoApiTempHistoryMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getAllCargoHistory(
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllCargoHistoryMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveJson(
        com.cpdss.common.generated.LoadableStudy.JsonRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.StatusReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveJsonMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void saveAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveAlgoErrorsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void fetchAllAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFetchAllAlgoErrorsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoHistoryByCargo(
        com.cpdss.common.generated.LoadableStudy.LatestCargoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCargoHistoryByCargoMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getActiveVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetActiveVoyagesByVesselMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /**
     *
     *
     * <pre>
     * For both Loading and Discharge
     * </pre>
     */
    public void getSynopticDataForLoadingPlan(
        com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSynopticDataForLoadingPlanMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadingInfoToSynopticData(
        com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadingInfoToSynopticDataMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOrSaveRulesForLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOrSaveRulesForLoadableStudyMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternDetailsJson(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternDetailsJsonMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternByVoyageAndStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternByVoyageAndStatusMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getCargoNominationByCargoNominationId(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCargoNominationByCargoNominationIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableCommingleByPatternId(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadableCommingleByPatternIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyShore(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudyShoreMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotationByPortRotationId(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(getGetLoadableStudyPortRotationByPortRotationIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudySimulatorJsonData(
        com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudySimulatorJsonDataMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void updateDischargeQuantityCargoDetails(
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateDischargeQuantityCargoDetailsMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadingSimulatorJsonData(
        com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadingSimulatorJsonDataMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVoyageMethod(), getCallOptions()), request, responseObserver);
    }

    /** */
    public void saveActivatedVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveActivatedVoyageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getVoyageByVoyageId(
        com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetVoyageByVoyageIdMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLoadablePatternForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadablePatternForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveLoadablePatternForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadicatorDataSynopticalForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(getGetLoadicatorDataSynopticalForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadicatorDataSynopticalForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(getSaveLoadicatorDataSynopticalForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getJsonDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetJsonDataForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveJsonDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveJsonDataForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getSynopticalDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSynopticalDataForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveSynopticalDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveSynopticalDataForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadableStudyPortRotationDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getGetLoadableStudyPortRotationDataForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadableStudyPortRotationDataForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadableStudyPortRotationDataForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getJsonDataForDischargePlanCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(getGetJsonDataForDischargePlanCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void checkCargoUsage(
        com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckCargoUsageMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePlanStowageBallastDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getGetLoadablePlanStowageBallastDetailsForCommunicationMethod(),
                  getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadablePlanStowageBallastDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod(),
                  getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePatternCargoDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(getGetLoadablePatternCargoDetailsForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadablePatternCargoDetailsForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadablePatternCargoDetailsForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getLoadablePlanCommingleDetailsPortwiseForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
                  getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveLoadablePlanCommingleDetailsPortwiseForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
                  getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOnBoardQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOnBoardQuantityForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveOnBoardQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveOnBoardQuantityForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void getOnHandQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetOnHandQuantityForCommunicationMethod(), getCallOptions()),
          request,
          responseObserver);
    }

    /** */
    public void saveOnHandQuantityForCommunication(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request,
        io.grpc.stub.StreamObserver<
                com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
            responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveOnHandQuantityForCommunicationMethod(), getCallOptions()),
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
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageListReply getVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVoyagesByVesselMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadableQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply
        findLoadableStudiesByVesselAndVoyage(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadableStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCargoNominationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableStudyPortRotationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply getCargoNominationById(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCargoNominationByIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.ValveSegregationReply getValveSegregation(
        com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetValveSegregationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse getLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply saveLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationDetail request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadableStudyPortRotationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply
        saveLoadableStudyPortRotationList(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadableStudyPortRotationListMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply deleteCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteCargoNominationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply saveDischargingPorts(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveDischargingPortsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply
        getPortRotationByLoadableStudyId(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPortRotationByLoadableStudyIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply deleteLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteLoadableStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply deletePortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeletePortRotationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply getOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOnHandQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply saveOnHandQuantity(
        com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveOnHandQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternReply getLoadablePatternDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePatternDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply saveLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadablePatternsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply getPurposeOfCommingle(
        com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetPurposeOfCommingleMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CommingleCargoReply getCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCommingleCargoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CommingleCargoReply saveCommingleCargo(
        com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCommingleCargoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply
        getLoadablePatternCommingleDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest
                request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePatternCommingleDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply generateLoadablePatterns(
        com.cpdss.common.generated.LoadableStudy.AlgoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateLoadablePatternsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply validateLoadablePlan(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getValidateLoadablePlanMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternReply getLoadablePatternList(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePatternListMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply getOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOnBoardQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply saveOnBoardQuantity(
        com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveOnBoardQuantityMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply getLoadicatorData(
        com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadicatorDataMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoStatusReply saveAlgoLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveAlgoLoadableStudyStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply saveLoadicatorResults(
        com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadicatorResultsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoReply savePatternValidateResult(
        com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSavePatternValidateResultMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply saveSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveSynopticalTableMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply getSynopticalTable(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSynopticalTableMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply getSynopticalDataByPortId(
        com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSynopticalDataByPortIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
        getSynopticalPortDataByPortId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSynopticalPortDataByPortIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SynopticalTableReply
        getSynopticDataByLoadableStudyId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSynopticDataByLoadableStudyIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply getLoadableStudyStatus(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableStudyStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply getLoadablePlanDetails(
        com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePlanDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.UpdateUllageReply updateUllage(
        com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateUllageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.UpdateUllageReply getUllage(
        com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUllageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply confirmPlanStatus(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConfirmPlanStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply confirmPlan(
        com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConfirmPlanMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply
        downloadLoadableStudyAttachment(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDownloadLoadableStudyAttachmentMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SaveCommentReply saveComment(
        com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveCommentMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SaveCommentReply saveLoadOnTop(
        com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadOnTopMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageListReply getVoyages(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVoyagesMethod(), getCallOptions(), request);
    }

    /**
     *
     *
     * <pre>
     * Generate loadable plan excel report
     * </pre>
     */
    public java.util.Iterator<com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
        getLoadablePlanReport(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetLoadablePlanReportMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoErrorReply getAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAlgoErrorsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply saveVoyageStatus(
        com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveVoyageStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoHistoryReply getCargoApiTempHistory(
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCargoApiTempHistoryMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoHistoryReply getAllCargoHistory(
        com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllCargoHistoryMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.StatusReply saveJson(
        com.cpdss.common.generated.LoadableStudy.JsonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveJsonMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoErrors saveAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrors request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveAlgoErrorsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.AlgoErrors fetchAllAlgoErrors(
        com.cpdss.common.generated.LoadableStudy.AlgoErrors request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFetchAllAlgoErrorsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LatestCargoReply getCargoHistoryByCargo(
        com.cpdss.common.generated.LoadableStudy.LatestCargoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCargoHistoryByCargoMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.ActiveVoyage getActiveVoyagesByVessel(
        com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetActiveVoyagesByVesselMethod(), getCallOptions(), request);
    }

    /**
     *
     *
     * <pre>
     * For both Loading and Discharge
     * </pre>
     */
    public com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse
        getSynopticDataForLoadingPlan(
            com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSynopticDataForLoadingPlanMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus saveLoadingInfoToSynopticData(
        com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadingInfoToSynopticDataMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableRuleReply
        getOrSaveRulesForLoadableStudy(
            com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOrSaveRulesForLoadableStudyMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson
        getLoadablePatternDetailsJson(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePatternDetailsJsonMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply
        getLoadablePatternByVoyageAndStatus(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePatternByVoyageAndStatusMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply
        getCargoNominationByCargoNominationId(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetCargoNominationByCargoNominationIdMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply
        getLoadableCommingleByPatternId(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableCommingleByPatternIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse
        getLoadableStudyShore(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableStudyShoreMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply
        getLoadableStudyPortRotationByPortRotationId(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetLoadableStudyPortRotationByPortRotationIdMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply
        getLoadableStudySimulatorJsonData(
            com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadableStudySimulatorJsonDataMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.Common.ResponseStatus updateDischargeQuantityCargoDetails(
        com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateDischargeQuantityCargoDetailsMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply
        getLoadingSimulatorJsonData(
            com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadingSimulatorJsonDataMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageActivateReply getVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageActivateReply saveActivatedVoyage(
        com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveActivatedVoyageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.VoyageInfoReply getVoyageByVoyageId(
        com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetVoyageByVoyageIdMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply
        getLoadablePatternForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest
                request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLoadablePatternForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply
        saveLoadablePatternForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest
                request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveLoadablePatternForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getLoadicatorDataSynopticalForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetLoadicatorDataSynopticalForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveLoadicatorDataSynopticalForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getSaveLoadicatorDataSynopticalForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getJsonDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetJsonDataForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveJsonDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveJsonDataForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getSynopticalDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSynopticalDataForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveSynopticalDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveSynopticalDataForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getLoadableStudyPortRotationDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetLoadableStudyPortRotationDataForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveLoadableStudyPortRotationDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getSaveLoadableStudyPortRotationDataForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getJsonDataForDischargePlanCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetJsonDataForDischargePlanCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply checkCargoUsage(
        com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckCargoUsageMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getLoadablePlanStowageBallastDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetLoadablePlanStowageBallastDetailsForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveLoadablePlanStowageBallastDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getLoadablePatternCargoDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetLoadablePatternCargoDetailsForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveLoadablePatternCargoDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getSaveLoadablePatternCargoDetailsForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getLoadablePlanCommingleDetailsPortwiseForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveLoadablePlanCommingleDetailsPortwiseForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(),
          getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
          getCallOptions(),
          request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getOnBoardQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOnBoardQuantityForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveOnBoardQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveOnBoardQuantityForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        getOnHandQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetOnHandQuantityForCommunicationMethod(), getCallOptions(), request);
    }

    /** */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply
        saveOnHandQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveOnHandQuantityForCommunicationMethod(), getCallOptions(), request);
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
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveVoyageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageListReply>
        getVoyagesByVessel(com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVoyagesByVesselMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>
        saveLoadableQuantity(
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadableQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        findLoadableStudiesByVesselAndVoyage(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        saveLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        saveCargoNomination(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCargoNominationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getLoadableStudyPortRotation(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyPortRotationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        getCargoNominationById(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCargoNominationByIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.ValveSegregationReply>
        getValveSegregation(
            com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetValveSegregationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse>
        getLoadableQuantity(
            com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        saveLoadableStudyPortRotation(
            com.cpdss.common.generated.LoadableStudy.PortRotationDetail request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        saveLoadableStudyPortRotationList(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyPortRotationListMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationReply>
        deleteCargoNomination(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteCargoNominationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        saveDischargingPorts(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveDischargingPortsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        getPortRotationByLoadableStudyId(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPortRotationByLoadableStudyIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>
        deleteLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteLoadableStudyMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationReply>
        deletePortRotation(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeletePortRotationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
        getOnHandQuantity(com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOnHandQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply>
        saveOnHandQuantity(com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveOnHandQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
        getLoadablePatternDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternDetailsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        saveLoadablePatterns(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadablePatternsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply>
        getPurposeOfCommingle(
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetPurposeOfCommingleMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
        getCommingleCargo(com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCommingleCargoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CommingleCargoReply>
        saveCommingleCargo(com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCommingleCargoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply>
        getLoadablePatternCommingleDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest
                request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternCommingleDetailsMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        generateLoadablePatterns(com.cpdss.common.generated.LoadableStudy.AlgoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateLoadablePatternsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        validateLoadablePlan(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getValidateLoadablePlanMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternReply>
        getLoadablePatternList(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternListMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
        getOnBoardQuantity(
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOnBoardQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply>
        saveOnBoardQuantity(
            com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveOnBoardQuantityMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>
        getLoadicatorData(com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadicatorDataMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>
        saveAlgoLoadableStudyStatus(
            com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveAlgoLoadableStudyStatusMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        saveLoadicatorResults(
            com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadicatorResultsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoReply>
        savePatternValidateResult(
            com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSavePatternValidateResultMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        saveSynopticalTable(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveSynopticalTableMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticalTable(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSynopticalTableMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticalDataByPortId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSynopticalDataByPortIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticalPortDataByPortId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSynopticalPortDataByPortIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
        getSynopticDataByLoadableStudyId(
            com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSynopticDataByLoadableStudyIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply>
        getLoadableStudyStatus(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyStatusMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply>
        getLoadablePlanDetails(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePlanDetailsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
        updateUllage(com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateUllageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>
        getUllage(com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUllageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
        confirmPlanStatus(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConfirmPlanStatusMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>
        confirmPlan(com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConfirmPlanMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply>
        downloadLoadableStudyAttachment(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDownloadLoadableStudyAttachmentMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
        saveComment(com.cpdss.common.generated.LoadableStudy.SaveCommentRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveCommentMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SaveCommentReply>
        saveLoadOnTop(com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadOnTopMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageListReply>
        getVoyages(com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVoyagesMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>
        getAlgoErrors(com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAlgoErrorsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>
        saveVoyageStatus(com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveVoyageStatusMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
        getCargoApiTempHistory(
            com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCargoApiTempHistoryMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>
        getAllCargoHistory(com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllCargoHistoryMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.StatusReply>
        saveJson(com.cpdss.common.generated.LoadableStudy.JsonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveJsonMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoErrors>
        saveAlgoErrors(com.cpdss.common.generated.LoadableStudy.AlgoErrors request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveAlgoErrorsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.AlgoErrors>
        fetchAllAlgoErrors(com.cpdss.common.generated.LoadableStudy.AlgoErrors request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFetchAllAlgoErrorsMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LatestCargoReply>
        getCargoHistoryByCargo(
            com.cpdss.common.generated.LoadableStudy.LatestCargoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCargoHistoryByCargoMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.ActiveVoyage>
        getActiveVoyagesByVessel(com.cpdss.common.generated.LoadableStudy.VoyageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetActiveVoyagesByVesselMethod(), getCallOptions()), request);
    }

    /**
     *
     *
     * <pre>
     * For both Loading and Discharge
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
        getSynopticDataForLoadingPlan(
            com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSynopticDataForLoadingPlanMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        saveLoadingInfoToSynopticData(
            com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadingInfoToSynopticDataMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>
        getOrSaveRulesForLoadableStudy(
            com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOrSaveRulesForLoadableStudyMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>
        getLoadablePatternDetailsJson(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternDetailsJsonMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
        getLoadablePatternByVoyageAndStatus(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternByVoyageAndStatusMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
        getCargoNominationByCargoNominationId(
            com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCargoNominationByCargoNominationIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
        getLoadableCommingleByPatternId(
            com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableCommingleByPatternIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>
        getLoadableStudyShore(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyShoreMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>
        getLoadableStudyPortRotationByPortRotationId(
            com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(getGetLoadableStudyPortRotationByPortRotationIdMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>
        getLoadableStudySimulatorJsonData(
            com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadableStudySimulatorJsonDataMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.Common.ResponseStatus>
        updateDischargeQuantityCargoDetails(
            com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateDischargeQuantityCargoDetailsMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>
        getLoadingSimulatorJsonData(
            com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadingSimulatorJsonDataMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
        getVoyage(com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVoyageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>
        saveActivatedVoyage(
            com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveActivatedVoyageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>
        getVoyageByVoyageId(com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetVoyageByVoyageIdMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
        getLoadablePatternForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest
                request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLoadablePatternForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationReply>
        saveLoadablePatternForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest
                request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveLoadablePatternForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getLoadicatorDataSynopticalForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(getGetLoadicatorDataSynopticalForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveLoadicatorDataSynopticalForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(getSaveLoadicatorDataSynopticalForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getJsonDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetJsonDataForCommunicationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveJsonDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveJsonDataForCommunicationMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getSynopticalDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSynopticalDataForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveSynopticalDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveSynopticalDataForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getLoadableStudyPortRotationDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getGetLoadableStudyPortRotationDataForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveLoadableStudyPortRotationDataForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadableStudyPortRotationDataForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getJsonDataForDischargePlanCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(getGetJsonDataForDischargePlanCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>
        checkCargoUsage(
            com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckCargoUsageMethod(), getCallOptions()), request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getLoadablePlanStowageBallastDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getGetLoadablePlanStowageBallastDetailsForCommunicationMethod(),
                  getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveLoadablePlanStowageBallastDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod(),
                  getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getLoadablePatternCargoDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(getGetLoadablePatternCargoDetailsForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveLoadablePatternCargoDetailsForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadablePatternCargoDetailsForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getLoadablePlanCommingleDetailsPortwiseForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
                  getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveLoadablePlanCommingleDetailsPortwiseForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel()
              .newCall(
                  getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod(),
                  getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getOnBoardQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOnBoardQuantityForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveOnBoardQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveOnBoardQuantityForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        getOnHandQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetOnHandQuantityForCommunicationMethod(), getCallOptions()),
          request);
    }

    /** */
    public com.google.common.util.concurrent.ListenableFuture<
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>
        saveOnHandQuantityForCommunication(
            com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveOnHandQuantityForCommunicationMethod(), getCallOptions()),
          request);
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
  private static final int METHODID_SAVE_LOADABLE_PATTERNS = 20;
  private static final int METHODID_GET_PURPOSE_OF_COMMINGLE = 21;
  private static final int METHODID_GET_COMMINGLE_CARGO = 22;
  private static final int METHODID_SAVE_COMMINGLE_CARGO = 23;
  private static final int METHODID_GET_LOADABLE_PATTERN_COMMINGLE_DETAILS = 24;
  private static final int METHODID_GENERATE_LOADABLE_PATTERNS = 25;
  private static final int METHODID_VALIDATE_LOADABLE_PLAN = 26;
  private static final int METHODID_GET_LOADABLE_PATTERN_LIST = 27;
  private static final int METHODID_GET_ON_BOARD_QUANTITY = 28;
  private static final int METHODID_SAVE_ON_BOARD_QUANTITY = 29;
  private static final int METHODID_GET_LOADICATOR_DATA = 30;
  private static final int METHODID_SAVE_ALGO_LOADABLE_STUDY_STATUS = 31;
  private static final int METHODID_SAVE_LOADICATOR_RESULTS = 32;
  private static final int METHODID_SAVE_PATTERN_VALIDATE_RESULT = 33;
  private static final int METHODID_SAVE_SYNOPTICAL_TABLE = 34;
  private static final int METHODID_GET_SYNOPTICAL_TABLE = 35;
  private static final int METHODID_GET_SYNOPTICAL_DATA_BY_PORT_ID = 36;
  private static final int METHODID_GET_SYNOPTICAL_PORT_DATA_BY_PORT_ID = 37;
  private static final int METHODID_GET_SYNOPTIC_DATA_BY_LOADABLE_STUDY_ID = 38;
  private static final int METHODID_GET_LOADABLE_STUDY_STATUS = 39;
  private static final int METHODID_GET_LOADABLE_PLAN_DETAILS = 40;
  private static final int METHODID_UPDATE_ULLAGE = 41;
  private static final int METHODID_GET_ULLAGE = 42;
  private static final int METHODID_CONFIRM_PLAN_STATUS = 43;
  private static final int METHODID_CONFIRM_PLAN = 44;
  private static final int METHODID_DOWNLOAD_LOADABLE_STUDY_ATTACHMENT = 45;
  private static final int METHODID_SAVE_COMMENT = 46;
  private static final int METHODID_SAVE_LOAD_ON_TOP = 47;
  private static final int METHODID_GET_VOYAGES = 48;
  private static final int METHODID_GET_LOADABLE_PLAN_REPORT = 49;
  private static final int METHODID_GET_ALGO_ERRORS = 50;
  private static final int METHODID_SAVE_VOYAGE_STATUS = 51;
  private static final int METHODID_GET_CARGO_API_TEMP_HISTORY = 52;
  private static final int METHODID_GET_ALL_CARGO_HISTORY = 53;
  private static final int METHODID_SAVE_JSON = 54;
  private static final int METHODID_SAVE_ALGO_ERRORS = 55;
  private static final int METHODID_FETCH_ALL_ALGO_ERRORS = 56;
  private static final int METHODID_GET_CARGO_HISTORY_BY_CARGO = 57;
  private static final int METHODID_GET_ACTIVE_VOYAGES_BY_VESSEL = 58;
  private static final int METHODID_GET_SYNOPTIC_DATA_FOR_LOADING_PLAN = 59;
  private static final int METHODID_SAVE_LOADING_INFO_TO_SYNOPTIC_DATA = 60;
  private static final int METHODID_GET_OR_SAVE_RULES_FOR_LOADABLE_STUDY = 61;
  private static final int METHODID_GET_LOADABLE_PATTERN_DETAILS_JSON = 62;
  private static final int METHODID_GET_LOADABLE_PATTERN_BY_VOYAGE_AND_STATUS = 63;
  private static final int METHODID_GET_CARGO_NOMINATION_BY_CARGO_NOMINATION_ID = 64;
  private static final int METHODID_GET_LOADABLE_COMMINGLE_BY_PATTERN_ID = 65;
  private static final int METHODID_GET_LOADABLE_STUDY_SHORE = 66;
  private static final int METHODID_GET_LOADABLE_STUDY_PORT_ROTATION_BY_PORT_ROTATION_ID = 67;
  private static final int METHODID_GET_LOADABLE_STUDY_SIMULATOR_JSON_DATA = 68;
  private static final int METHODID_UPDATE_DISCHARGE_QUANTITY_CARGO_DETAILS = 69;
  private static final int METHODID_GET_LOADING_SIMULATOR_JSON_DATA = 70;
  private static final int METHODID_GET_VOYAGE = 71;
  private static final int METHODID_SAVE_ACTIVATED_VOYAGE = 72;
  private static final int METHODID_GET_VOYAGE_BY_VOYAGE_ID = 73;
  private static final int METHODID_GET_LOADABLE_PATTERN_FOR_COMMUNICATION = 74;
  private static final int METHODID_SAVE_LOADABLE_PATTERN_FOR_COMMUNICATION = 75;
  private static final int METHODID_GET_LOADICATOR_DATA_SYNOPTICAL_FOR_COMMUNICATION = 76;
  private static final int METHODID_SAVE_LOADICATOR_DATA_SYNOPTICAL_FOR_COMMUNICATION = 77;
  private static final int METHODID_GET_JSON_DATA_FOR_COMMUNICATION = 78;
  private static final int METHODID_SAVE_JSON_DATA_FOR_COMMUNICATION = 79;
  private static final int METHODID_GET_SYNOPTICAL_DATA_FOR_COMMUNICATION = 80;
  private static final int METHODID_SAVE_SYNOPTICAL_DATA_FOR_COMMUNICATION = 81;
  private static final int METHODID_GET_LOADABLE_STUDY_PORT_ROTATION_DATA_FOR_COMMUNICATION = 82;
  private static final int METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_DATA_FOR_COMMUNICATION = 83;
  private static final int METHODID_GET_JSON_DATA_FOR_DISCHARGE_PLAN_COMMUNICATION = 84;
  private static final int METHODID_CHECK_CARGO_USAGE = 85;
  private static final int METHODID_GET_LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS_FOR_COMMUNICATION =
      86;
  private static final int METHODID_SAVE_LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS_FOR_COMMUNICATION =
      87;
  private static final int METHODID_GET_LOADABLE_PATTERN_CARGO_DETAILS_FOR_COMMUNICATION = 88;
  private static final int METHODID_SAVE_LOADABLE_PATTERN_CARGO_DETAILS_FOR_COMMUNICATION = 89;
  private static final int METHODID_GET_LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE_FOR_COMMUNICATION =
      90;
  private static final int
      METHODID_SAVE_LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE_FOR_COMMUNICATION = 91;
  private static final int METHODID_GET_ON_BOARD_QUANTITY_FOR_COMMUNICATION = 92;
  private static final int METHODID_SAVE_ON_BOARD_QUANTITY_FOR_COMMUNICATION = 93;
  private static final int METHODID_GET_ON_HAND_QUANTITY_FOR_COMMUNICATION = 94;
  private static final int METHODID_SAVE_ON_HAND_QUANTITY_FOR_COMMUNICATION = 95;

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
        case METHODID_SAVE_LOADABLE_PATTERNS:
          serviceImpl.saveLoadablePatterns(
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
        case METHODID_VALIDATE_LOADABLE_PLAN:
          serviceImpl.validateLoadablePlan(
              (com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest) request,
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
        case METHODID_GET_LOADICATOR_DATA:
          serviceImpl.getLoadicatorData(
              (com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ALGO_LOADABLE_STUDY_STATUS:
          serviceImpl.saveAlgoLoadableStudyStatus(
              (com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.AlgoStatusReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADICATOR_RESULTS:
          serviceImpl.saveLoadicatorResults(
              (com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_PATTERN_VALIDATE_RESULT:
          serviceImpl.savePatternValidateResult(
              (com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoReply>)
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
        case METHODID_GET_SYNOPTIC_DATA_BY_LOADABLE_STUDY_ID:
          serviceImpl.getSynopticDataByLoadableStudyId(
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
        case METHODID_UPDATE_ULLAGE:
          serviceImpl.updateUllage(
              (com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>)
                  responseObserver);
          break;
        case METHODID_GET_ULLAGE:
          serviceImpl.getUllage(
              (com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.UpdateUllageReply>)
                  responseObserver);
          break;
        case METHODID_CONFIRM_PLAN_STATUS:
          serviceImpl.confirmPlanStatus(
              (com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply>)
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
        case METHODID_SAVE_LOAD_ON_TOP:
          serviceImpl.saveLoadOnTop(
              (com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SaveCommentReply>)
                  responseObserver);
          break;
        case METHODID_GET_VOYAGES:
          serviceImpl.getVoyages(
              (com.cpdss.common.generated.LoadableStudy.VoyageRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.VoyageListReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PLAN_REPORT:
          serviceImpl.getLoadablePlanReport(
              (com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>)
                  responseObserver);
          break;
        case METHODID_GET_ALGO_ERRORS:
          serviceImpl.getAlgoErrors(
              (com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrorReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_VOYAGE_STATUS:
          serviceImpl.saveVoyageStatus(
              (com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_API_TEMP_HISTORY:
          serviceImpl.getCargoApiTempHistory(
              (com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>)
                  responseObserver);
          break;
        case METHODID_GET_ALL_CARGO_HISTORY:
          serviceImpl.getAllCargoHistory(
              (com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_JSON:
          serviceImpl.saveJson(
              (com.cpdss.common.generated.LoadableStudy.JsonRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.StatusReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ALGO_ERRORS:
          serviceImpl.saveAlgoErrors(
              (com.cpdss.common.generated.LoadableStudy.AlgoErrors) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors>)
                  responseObserver);
          break;
        case METHODID_FETCH_ALL_ALGO_ERRORS:
          serviceImpl.fetchAllAlgoErrors(
              (com.cpdss.common.generated.LoadableStudy.AlgoErrors) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_HISTORY_BY_CARGO:
          serviceImpl.getCargoHistoryByCargo(
              (com.cpdss.common.generated.LoadableStudy.LatestCargoRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LatestCargoReply>)
                  responseObserver);
          break;
        case METHODID_GET_ACTIVE_VOYAGES_BY_VESSEL:
          serviceImpl.getActiveVoyagesByVessel(
              (com.cpdss.common.generated.LoadableStudy.VoyageRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.ActiveVoyage>)
                  responseObserver);
          break;
        case METHODID_GET_SYNOPTIC_DATA_FOR_LOADING_PLAN:
          serviceImpl.getSynopticDataForLoadingPlan(
              (com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADING_INFO_TO_SYNOPTIC_DATA:
          serviceImpl.saveLoadingInfoToSynopticData(
              (com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_GET_OR_SAVE_RULES_FOR_LOADABLE_STUDY:
          serviceImpl.getOrSaveRulesForLoadableStudy(
              (com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableRuleReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_DETAILS_JSON:
          serviceImpl.getLoadablePatternDetailsJson(
              (com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_BY_VOYAGE_AND_STATUS:
          serviceImpl.getLoadablePatternByVoyageAndStatus(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>)
                  responseObserver);
          break;
        case METHODID_GET_CARGO_NOMINATION_BY_CARGO_NOMINATION_ID:
          serviceImpl.getCargoNominationByCargoNominationId(
              (com.cpdss.common.generated.LoadableStudy.CargoNominationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_COMMINGLE_BY_PATTERN_ID:
          serviceImpl.getLoadableCommingleByPatternId(
              (com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_SHORE:
          serviceImpl.getLoadableStudyShore(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyShoreResponse>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_PORT_ROTATION_BY_PORT_ROTATION_ID:
          serviceImpl.getLoadableStudyPortRotationByPortRotationId(
              (com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.PortRotationDetailReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_SIMULATOR_JSON_DATA:
          serviceImpl.getLoadableStudySimulatorJsonData(
              (com.cpdss.common.generated.LoadableStudy.SimulatorJsonRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.SimulatorJsonReply>)
                  responseObserver);
          break;
        case METHODID_UPDATE_DISCHARGE_QUANTITY_CARGO_DETAILS:
          serviceImpl.updateDischargeQuantityCargoDetails(
              (com.cpdss.common.generated.LoadableStudy.DischargeQuantityCargoDetailsRequest)
                  request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.Common.ResponseStatus>)
                  responseObserver);
          break;
        case METHODID_GET_LOADING_SIMULATOR_JSON_DATA:
          serviceImpl.getLoadingSimulatorJsonData(
              (com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadingSimulatorJsonReply>)
                  responseObserver);
          break;
        case METHODID_GET_VOYAGE:
          serviceImpl.getVoyage(
              (com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ACTIVATED_VOYAGE:
          serviceImpl.saveActivatedVoyage(
              (com.cpdss.common.generated.LoadableStudy.VoyageActivateRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.VoyageActivateReply>)
                  responseObserver);
          break;
        case METHODID_GET_VOYAGE_BY_VOYAGE_ID:
          serviceImpl.getVoyageByVoyageId(
              (com.cpdss.common.generated.LoadableStudy.VoyageInfoRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.VoyageInfoReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_FOR_COMMUNICATION:
          serviceImpl.getLoadablePatternForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadableStudyPatternCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_PATTERN_FOR_COMMUNICATION:
          serviceImpl.saveLoadablePatternForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyPatternCommunicationRequest)
                  request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy
                          .LoadableStudyPatternCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADICATOR_DATA_SYNOPTICAL_FOR_COMMUNICATION:
          serviceImpl.getLoadicatorDataSynopticalForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADICATOR_DATA_SYNOPTICAL_FOR_COMMUNICATION:
          serviceImpl.saveLoadicatorDataSynopticalForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_JSON_DATA_FOR_COMMUNICATION:
          serviceImpl.getJsonDataForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_JSON_DATA_FOR_COMMUNICATION:
          serviceImpl.saveJsonDataForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_SYNOPTICAL_DATA_FOR_COMMUNICATION:
          serviceImpl.getSynopticalDataForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_SYNOPTICAL_DATA_FOR_COMMUNICATION:
          serviceImpl.saveSynopticalDataForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_PORT_ROTATION_DATA_FOR_COMMUNICATION:
          serviceImpl.getLoadableStudyPortRotationDataForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_STUDY_PORT_ROTATION_DATA_FOR_COMMUNICATION:
          serviceImpl.saveLoadableStudyPortRotationDataForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_JSON_DATA_FOR_DISCHARGE_PLAN_COMMUNICATION:
          serviceImpl.getJsonDataForDischargePlanCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_CHECK_CARGO_USAGE:
          serviceImpl.checkCargoUsage(
              (com.cpdss.common.generated.LoadableStudy.CargoNominationCheckRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.CargoNominationCheckReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS_FOR_COMMUNICATION:
          serviceImpl.getLoadablePlanStowageBallastDetailsForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_PLAN_STOWAGE_BALLAST_DETAILS_FOR_COMMUNICATION:
          serviceImpl.saveLoadablePlanStowageBallastDetailsForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PATTERN_CARGO_DETAILS_FOR_COMMUNICATION:
          serviceImpl.getLoadablePatternCargoDetailsForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_PATTERN_CARGO_DETAILS_FOR_COMMUNICATION:
          serviceImpl.saveLoadablePatternCargoDetailsForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE_FOR_COMMUNICATION:
          serviceImpl.getLoadablePlanCommingleDetailsPortwiseForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_PLAN_COMMINGLE_DETAILS_PORTWISE_FOR_COMMUNICATION:
          serviceImpl.saveLoadablePlanCommingleDetailsPortwiseForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_ON_BOARD_QUANTITY_FOR_COMMUNICATION:
          serviceImpl.getOnBoardQuantityForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ON_BOARD_QUANTITY_FOR_COMMUNICATION:
          serviceImpl.saveOnBoardQuantityForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_GET_ON_HAND_QUANTITY_FOR_COMMUNICATION:
          serviceImpl.getOnHandQuantityForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
                  responseObserver);
          break;
        case METHODID_SAVE_ON_HAND_QUANTITY_FOR_COMMUNICATION:
          serviceImpl.saveOnHandQuantityForCommunication(
              (com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationRequest) request,
              (io.grpc.stub.StreamObserver<
                      com.cpdss.common.generated.LoadableStudy.LoadableStudyCommunicationReply>)
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
                      .addMethod(getSaveLoadablePatternsMethod())
                      .addMethod(getGetPurposeOfCommingleMethod())
                      .addMethod(getGetCommingleCargoMethod())
                      .addMethod(getSaveCommingleCargoMethod())
                      .addMethod(getGetLoadablePatternCommingleDetailsMethod())
                      .addMethod(getGenerateLoadablePatternsMethod())
                      .addMethod(getValidateLoadablePlanMethod())
                      .addMethod(getGetLoadablePatternListMethod())
                      .addMethod(getGetOnBoardQuantityMethod())
                      .addMethod(getSaveOnBoardQuantityMethod())
                      .addMethod(getGetLoadicatorDataMethod())
                      .addMethod(getSaveAlgoLoadableStudyStatusMethod())
                      .addMethod(getSaveLoadicatorResultsMethod())
                      .addMethod(getSavePatternValidateResultMethod())
                      .addMethod(getSaveSynopticalTableMethod())
                      .addMethod(getGetSynopticalTableMethod())
                      .addMethod(getGetSynopticalDataByPortIdMethod())
                      .addMethod(getGetSynopticalPortDataByPortIdMethod())
                      .addMethod(getGetSynopticDataByLoadableStudyIdMethod())
                      .addMethod(getGetLoadableStudyStatusMethod())
                      .addMethod(getGetLoadablePlanDetailsMethod())
                      .addMethod(getUpdateUllageMethod())
                      .addMethod(getGetUllageMethod())
                      .addMethod(getConfirmPlanStatusMethod())
                      .addMethod(getConfirmPlanMethod())
                      .addMethod(getDownloadLoadableStudyAttachmentMethod())
                      .addMethod(getSaveCommentMethod())
                      .addMethod(getSaveLoadOnTopMethod())
                      .addMethod(getGetVoyagesMethod())
                      .addMethod(getGetLoadablePlanReportMethod())
                      .addMethod(getGetAlgoErrorsMethod())
                      .addMethod(getSaveVoyageStatusMethod())
                      .addMethod(getGetCargoApiTempHistoryMethod())
                      .addMethod(getGetAllCargoHistoryMethod())
                      .addMethod(getSaveJsonMethod())
                      .addMethod(getSaveAlgoErrorsMethod())
                      .addMethod(getFetchAllAlgoErrorsMethod())
                      .addMethod(getGetCargoHistoryByCargoMethod())
                      .addMethod(getGetActiveVoyagesByVesselMethod())
                      .addMethod(getGetSynopticDataForLoadingPlanMethod())
                      .addMethod(getSaveLoadingInfoToSynopticDataMethod())
                      .addMethod(getGetOrSaveRulesForLoadableStudyMethod())
                      .addMethod(getGetLoadablePatternDetailsJsonMethod())
                      .addMethod(getGetLoadablePatternByVoyageAndStatusMethod())
                      .addMethod(getGetCargoNominationByCargoNominationIdMethod())
                      .addMethod(getGetLoadableCommingleByPatternIdMethod())
                      .addMethod(getGetLoadableStudyShoreMethod())
                      .addMethod(getGetLoadableStudyPortRotationByPortRotationIdMethod())
                      .addMethod(getGetLoadableStudySimulatorJsonDataMethod())
                      .addMethod(getUpdateDischargeQuantityCargoDetailsMethod())
                      .addMethod(getGetLoadingSimulatorJsonDataMethod())
                      .addMethod(getGetVoyageMethod())
                      .addMethod(getSaveActivatedVoyageMethod())
                      .addMethod(getGetVoyageByVoyageIdMethod())
                      .addMethod(getGetLoadablePatternForCommunicationMethod())
                      .addMethod(getSaveLoadablePatternForCommunicationMethod())
                      .addMethod(getGetLoadicatorDataSynopticalForCommunicationMethod())
                      .addMethod(getSaveLoadicatorDataSynopticalForCommunicationMethod())
                      .addMethod(getGetJsonDataForCommunicationMethod())
                      .addMethod(getSaveJsonDataForCommunicationMethod())
                      .addMethod(getGetSynopticalDataForCommunicationMethod())
                      .addMethod(getSaveSynopticalDataForCommunicationMethod())
                      .addMethod(getGetLoadableStudyPortRotationDataForCommunicationMethod())
                      .addMethod(getSaveLoadableStudyPortRotationDataForCommunicationMethod())
                      .addMethod(getGetJsonDataForDischargePlanCommunicationMethod())
                      .addMethod(getCheckCargoUsageMethod())
                      .addMethod(getGetLoadablePlanStowageBallastDetailsForCommunicationMethod())
                      .addMethod(getSaveLoadablePlanStowageBallastDetailsForCommunicationMethod())
                      .addMethod(getGetLoadablePatternCargoDetailsForCommunicationMethod())
                      .addMethod(getSaveLoadablePatternCargoDetailsForCommunicationMethod())
                      .addMethod(getGetLoadablePlanCommingleDetailsPortwiseForCommunicationMethod())
                      .addMethod(
                          getSaveLoadablePlanCommingleDetailsPortwiseForCommunicationMethod())
                      .addMethod(getGetOnBoardQuantityForCommunicationMethod())
                      .addMethod(getSaveOnBoardQuantityForCommunicationMethod())
                      .addMethod(getGetOnHandQuantityForCommunicationMethod())
                      .addMethod(getSaveOnHandQuantityForCommunicationMethod())
                      .build();
        }
      }
    }
    return result;
  }
}
