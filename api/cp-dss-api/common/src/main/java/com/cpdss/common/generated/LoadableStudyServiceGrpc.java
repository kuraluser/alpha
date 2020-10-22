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

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply> getSaveLoadableQuantityMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadableQuantity",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest,
      com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply> getSaveLoadableQuantityMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest, com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply> getSaveLoadableQuantityMethod;
    if ((getSaveLoadableQuantityMethod = LoadableStudyServiceGrpc.getSaveLoadableQuantityMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableQuantityMethod = LoadableStudyServiceGrpc.getSaveLoadableQuantityMethod) == null) {
          LoadableStudyServiceGrpc.getSaveLoadableQuantityMethod = getSaveLoadableQuantityMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest, com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveLoadableQuantity"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadableQuantity"))
              .build();
        }
      }
    }
    return getSaveLoadableQuantityMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> getFindLoadableStudiesByVesselAndVoyageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindLoadableStudiesByVesselAndVoyage",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> getFindLoadableStudiesByVesselAndVoyageMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest, com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> getFindLoadableStudiesByVesselAndVoyageMethod;
    if ((getFindLoadableStudiesByVesselAndVoyageMethod = LoadableStudyServiceGrpc.getFindLoadableStudiesByVesselAndVoyageMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getFindLoadableStudiesByVesselAndVoyageMethod = LoadableStudyServiceGrpc.getFindLoadableStudiesByVesselAndVoyageMethod) == null) {
          LoadableStudyServiceGrpc.getFindLoadableStudiesByVesselAndVoyageMethod = getFindLoadableStudiesByVesselAndVoyageMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest, com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindLoadableStudiesByVesselAndVoyage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadableStudyServiceMethodDescriptorSupplier("FindLoadableStudiesByVesselAndVoyage"))
              .build();
        }
      }
    }
    return getFindLoadableStudiesByVesselAndVoyageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> getSaveLoadableStudyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveLoadableStudy",
      requestType = com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.class,
      responseType = com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail,
      com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> getSaveLoadableStudyMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail, com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> getSaveLoadableStudyMethod;
    if ((getSaveLoadableStudyMethod = LoadableStudyServiceGrpc.getSaveLoadableStudyMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveLoadableStudyMethod = LoadableStudyServiceGrpc.getSaveLoadableStudyMethod) == null) {
          LoadableStudyServiceGrpc.getSaveLoadableStudyMethod = getSaveLoadableStudyMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail, com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveLoadableStudy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadableStudyServiceMethodDescriptorSupplier("SaveLoadableStudy"))
              .build();
        }
      }
    }
    return getSaveLoadableStudyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
      com.cpdss.common.generated.LoadableStudy.CargoNominationReply> getSaveCargoNominationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveCargoNomination",
      requestType = com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.CargoNominationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest,
      com.cpdss.common.generated.LoadableStudy.CargoNominationReply> getSaveCargoNominationMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest, com.cpdss.common.generated.LoadableStudy.CargoNominationReply> getSaveCargoNominationMethod;
    if ((getSaveCargoNominationMethod = LoadableStudyServiceGrpc.getSaveCargoNominationMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getSaveCargoNominationMethod = LoadableStudyServiceGrpc.getSaveCargoNominationMethod) == null) {
          LoadableStudyServiceGrpc.getSaveCargoNominationMethod = getSaveCargoNominationMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.LoadableStudy.CargoNominationRequest, com.cpdss.common.generated.LoadableStudy.CargoNominationReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveCargoNomination"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.CargoNominationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.CargoNominationReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadableStudyServiceMethodDescriptorSupplier("SaveCargoNomination"))
              .build();
        }
      }
    }
    return getSaveCargoNominationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
      com.cpdss.common.generated.LoadableStudy.PortRotationReply> getGetLoadableStudyPortRotationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLoadableStudyPortRotation",
      requestType = com.cpdss.common.generated.LoadableStudy.PortRotationRequest.class,
      responseType = com.cpdss.common.generated.LoadableStudy.PortRotationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.PortRotationRequest,
      com.cpdss.common.generated.LoadableStudy.PortRotationReply> getGetLoadableStudyPortRotationMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.LoadableStudy.PortRotationRequest, com.cpdss.common.generated.LoadableStudy.PortRotationReply> getGetLoadableStudyPortRotationMethod;
    if ((getGetLoadableStudyPortRotationMethod = LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationMethod) == null) {
      synchronized (LoadableStudyServiceGrpc.class) {
        if ((getGetLoadableStudyPortRotationMethod = LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationMethod) == null) {
          LoadableStudyServiceGrpc.getGetLoadableStudyPortRotationMethod = getGetLoadableStudyPortRotationMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.LoadableStudy.PortRotationRequest, com.cpdss.common.generated.LoadableStudy.PortRotationReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLoadableStudyPortRotation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.PortRotationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.LoadableStudy.PortRotationReply.getDefaultInstance()))
              .setSchemaDescriptor(new LoadableStudyServiceMethodDescriptorSupplier("GetLoadableStudyPortRotation"))
              .build();
        }
      }
    }
    return getGetLoadableStudyPortRotationMethod;
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

    /**
     */
    public void saveLoadableQuantity(com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadableQuantityMethod(), responseObserver);
    }

    /**
     */
    public void findLoadableStudiesByVesselAndVoyage(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> responseObserver) {
      asyncUnimplementedUnaryCall(getFindLoadableStudiesByVesselAndVoyageMethod(), responseObserver);
    }

    /**
     */
    public void saveLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSaveLoadableStudyMethod(), responseObserver);
    }

    /**
     */
    public void saveCargoNomination(com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSaveCargoNominationMethod(), responseObserver);
    }

    /**
     */
    public void getLoadableStudyPortRotation(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetLoadableStudyPortRotationMethod(), responseObserver);
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

    /**
     */
    public void saveLoadableQuantity(com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadableQuantityMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findLoadableStudiesByVesselAndVoyage(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveLoadableStudyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveCargoNomination(com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSaveCargoNominationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLoadableStudyPortRotation(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLoadableStudyPortRotationMethod(), getCallOptions()), request, responseObserver);
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

    /**
     */
    public com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply saveLoadableQuantity(com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadableQuantityMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply findLoadableStudiesByVesselAndVoyage(com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return blockingUnaryCall(
          getChannel(), getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.LoadableStudy.LoadableStudyReply saveLoadableStudy(com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
      return blockingUnaryCall(
          getChannel(), getSaveLoadableStudyMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.LoadableStudy.CargoNominationReply saveCargoNomination(com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return blockingUnaryCall(
          getChannel(), getSaveCargoNominationMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.LoadableStudy.PortRotationReply getLoadableStudyPortRotation(com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLoadableStudyPortRotationMethod(), getCallOptions(), request);
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

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply> saveLoadableQuantity(
        com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadableQuantityMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> findLoadableStudiesByVesselAndVoyage(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getFindLoadableStudiesByVesselAndVoyageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply> saveLoadableStudy(
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveLoadableStudyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.LoadableStudy.CargoNominationReply> saveCargoNomination(
        com.cpdss.common.generated.LoadableStudy.CargoNominationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSaveCargoNominationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.LoadableStudy.PortRotationReply> getLoadableStudyPortRotation(
        com.cpdss.common.generated.LoadableStudy.PortRotationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLoadableStudyPortRotationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAVE_VOYAGE = 0;
  private static final int METHODID_SAVE_LOADABLE_QUANTITY = 1;
  private static final int METHODID_FIND_LOADABLE_STUDIES_BY_VESSEL_AND_VOYAGE = 2;
  private static final int METHODID_SAVE_LOADABLE_STUDY = 3;
  private static final int METHODID_SAVE_CARGO_NOMINATION = 4;
  private static final int METHODID_GET_LOADABLE_STUDY_PORT_ROTATION = 5;

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
        case METHODID_SAVE_LOADABLE_QUANTITY:
          serviceImpl.saveLoadableQuantity((com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply>) responseObserver);
          break;
        case METHODID_FIND_LOADABLE_STUDIES_BY_VESSEL_AND_VOYAGE:
          serviceImpl.findLoadableStudiesByVesselAndVoyage((com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>) responseObserver);
          break;
        case METHODID_SAVE_LOADABLE_STUDY:
          serviceImpl.saveLoadableStudy((com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableStudyReply>) responseObserver);
          break;
        case METHODID_SAVE_CARGO_NOMINATION:
          serviceImpl.saveCargoNomination((com.cpdss.common.generated.LoadableStudy.CargoNominationRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationReply>) responseObserver);
          break;
        case METHODID_GET_LOADABLE_STUDY_PORT_ROTATION:
          serviceImpl.getLoadableStudyPortRotation((com.cpdss.common.generated.LoadableStudy.PortRotationRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.LoadableStudy.PortRotationReply>) responseObserver);
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
              .addMethod(getSaveLoadableQuantityMethod())
              .addMethod(getFindLoadableStudiesByVesselAndVoyageMethod())
              .addMethod(getSaveLoadableStudyMethod())
              .addMethod(getSaveCargoNominationMethod())
              .addMethod(getGetLoadableStudyPortRotationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
