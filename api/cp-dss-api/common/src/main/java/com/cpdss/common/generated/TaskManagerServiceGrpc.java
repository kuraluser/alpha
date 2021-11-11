package com.cpdss.common.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.40.1)",
    comments = "Source: task_manager.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TaskManagerServiceGrpc {

  private TaskManagerServiceGrpc() {}

  public static final String SERVICE_NAME = "TaskManagerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ScheduleTaskRequest,
      com.cpdss.common.generated.TaskManager.TaskManagerReply> getScheduleTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ScheduleTask",
      requestType = com.cpdss.common.generated.TaskManager.ScheduleTaskRequest.class,
      responseType = com.cpdss.common.generated.TaskManager.TaskManagerReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ScheduleTaskRequest,
      com.cpdss.common.generated.TaskManager.TaskManagerReply> getScheduleTaskMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ScheduleTaskRequest, com.cpdss.common.generated.TaskManager.TaskManagerReply> getScheduleTaskMethod;
    if ((getScheduleTaskMethod = TaskManagerServiceGrpc.getScheduleTaskMethod) == null) {
      synchronized (TaskManagerServiceGrpc.class) {
        if ((getScheduleTaskMethod = TaskManagerServiceGrpc.getScheduleTaskMethod) == null) {
          TaskManagerServiceGrpc.getScheduleTaskMethod = getScheduleTaskMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.TaskManager.ScheduleTaskRequest, com.cpdss.common.generated.TaskManager.TaskManagerReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ScheduleTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.ScheduleTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.TaskManagerReply.getDefaultInstance()))
              .setSchemaDescriptor(new TaskManagerServiceMethodDescriptorSupplier("ScheduleTask"))
              .build();
        }
      }
    }
    return getScheduleTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest,
      com.cpdss.common.generated.TaskManager.TaskManagerReply> getDeleteScheduleTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteScheduleTask",
      requestType = com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest.class,
      responseType = com.cpdss.common.generated.TaskManager.TaskManagerReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest,
      com.cpdss.common.generated.TaskManager.TaskManagerReply> getDeleteScheduleTaskMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest, com.cpdss.common.generated.TaskManager.TaskManagerReply> getDeleteScheduleTaskMethod;
    if ((getDeleteScheduleTaskMethod = TaskManagerServiceGrpc.getDeleteScheduleTaskMethod) == null) {
      synchronized (TaskManagerServiceGrpc.class) {
        if ((getDeleteScheduleTaskMethod = TaskManagerServiceGrpc.getDeleteScheduleTaskMethod) == null) {
          TaskManagerServiceGrpc.getDeleteScheduleTaskMethod = getDeleteScheduleTaskMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest, com.cpdss.common.generated.TaskManager.TaskManagerReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteScheduleTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.TaskManagerReply.getDefaultInstance()))
              .setSchemaDescriptor(new TaskManagerServiceMethodDescriptorSupplier("DeleteScheduleTask"))
              .build();
        }
      }
    }
    return getDeleteScheduleTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ExecuteTaskRequest,
      com.cpdss.common.generated.TaskManager.ExecuteTaskReply> getExecuteTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExecuteTask",
      requestType = com.cpdss.common.generated.TaskManager.ExecuteTaskRequest.class,
      responseType = com.cpdss.common.generated.TaskManager.ExecuteTaskReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ExecuteTaskRequest,
      com.cpdss.common.generated.TaskManager.ExecuteTaskReply> getExecuteTaskMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.ExecuteTaskRequest, com.cpdss.common.generated.TaskManager.ExecuteTaskReply> getExecuteTaskMethod;
    if ((getExecuteTaskMethod = TaskManagerServiceGrpc.getExecuteTaskMethod) == null) {
      synchronized (TaskManagerServiceGrpc.class) {
        if ((getExecuteTaskMethod = TaskManagerServiceGrpc.getExecuteTaskMethod) == null) {
          TaskManagerServiceGrpc.getExecuteTaskMethod = getExecuteTaskMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.TaskManager.ExecuteTaskRequest, com.cpdss.common.generated.TaskManager.ExecuteTaskReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExecuteTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.ExecuteTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.ExecuteTaskReply.getDefaultInstance()))
              .setSchemaDescriptor(new TaskManagerServiceMethodDescriptorSupplier("ExecuteTask"))
              .build();
        }
      }
    }
    return getExecuteTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.SchedulededTaskRequest,
      com.cpdss.common.generated.TaskManager.SchedulededTaskReply> getGetScheduledTasksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getScheduledTasks",
      requestType = com.cpdss.common.generated.TaskManager.SchedulededTaskRequest.class,
      responseType = com.cpdss.common.generated.TaskManager.SchedulededTaskReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.SchedulededTaskRequest,
      com.cpdss.common.generated.TaskManager.SchedulededTaskReply> getGetScheduledTasksMethod() {
    io.grpc.MethodDescriptor<com.cpdss.common.generated.TaskManager.SchedulededTaskRequest, com.cpdss.common.generated.TaskManager.SchedulededTaskReply> getGetScheduledTasksMethod;
    if ((getGetScheduledTasksMethod = TaskManagerServiceGrpc.getGetScheduledTasksMethod) == null) {
      synchronized (TaskManagerServiceGrpc.class) {
        if ((getGetScheduledTasksMethod = TaskManagerServiceGrpc.getGetScheduledTasksMethod) == null) {
          TaskManagerServiceGrpc.getGetScheduledTasksMethod = getGetScheduledTasksMethod =
              io.grpc.MethodDescriptor.<com.cpdss.common.generated.TaskManager.SchedulededTaskRequest, com.cpdss.common.generated.TaskManager.SchedulededTaskReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getScheduledTasks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.SchedulededTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.cpdss.common.generated.TaskManager.SchedulededTaskReply.getDefaultInstance()))
              .setSchemaDescriptor(new TaskManagerServiceMethodDescriptorSupplier("getScheduledTasks"))
              .build();
        }
      }
    }
    return getGetScheduledTasksMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TaskManagerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskManagerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskManagerServiceStub>() {
        @java.lang.Override
        public TaskManagerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskManagerServiceStub(channel, callOptions);
        }
      };
    return TaskManagerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TaskManagerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskManagerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskManagerServiceBlockingStub>() {
        @java.lang.Override
        public TaskManagerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskManagerServiceBlockingStub(channel, callOptions);
        }
      };
    return TaskManagerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TaskManagerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TaskManagerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TaskManagerServiceFutureStub>() {
        @java.lang.Override
        public TaskManagerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TaskManagerServiceFutureStub(channel, callOptions);
        }
      };
    return TaskManagerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TaskManagerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void scheduleTask(com.cpdss.common.generated.TaskManager.ScheduleTaskRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.TaskManagerReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getScheduleTaskMethod(), responseObserver);
    }

    /**
     */
    public void deleteScheduleTask(com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.TaskManagerReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteScheduleTaskMethod(), responseObserver);
    }

    /**
     */
    public void executeTask(com.cpdss.common.generated.TaskManager.ExecuteTaskRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.ExecuteTaskReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExecuteTaskMethod(), responseObserver);
    }

    /**
     */
    public void getScheduledTasks(com.cpdss.common.generated.TaskManager.SchedulededTaskRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.SchedulededTaskReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetScheduledTasksMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getScheduleTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.TaskManager.ScheduleTaskRequest,
                com.cpdss.common.generated.TaskManager.TaskManagerReply>(
                  this, METHODID_SCHEDULE_TASK)))
          .addMethod(
            getDeleteScheduleTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest,
                com.cpdss.common.generated.TaskManager.TaskManagerReply>(
                  this, METHODID_DELETE_SCHEDULE_TASK)))
          .addMethod(
            getExecuteTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.TaskManager.ExecuteTaskRequest,
                com.cpdss.common.generated.TaskManager.ExecuteTaskReply>(
                  this, METHODID_EXECUTE_TASK)))
          .addMethod(
            getGetScheduledTasksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.cpdss.common.generated.TaskManager.SchedulededTaskRequest,
                com.cpdss.common.generated.TaskManager.SchedulededTaskReply>(
                  this, METHODID_GET_SCHEDULED_TASKS)))
          .build();
    }
  }

  /**
   */
  public static final class TaskManagerServiceStub extends io.grpc.stub.AbstractAsyncStub<TaskManagerServiceStub> {
    private TaskManagerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskManagerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskManagerServiceStub(channel, callOptions);
    }

    /**
     */
    public void scheduleTask(com.cpdss.common.generated.TaskManager.ScheduleTaskRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.TaskManagerReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getScheduleTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteScheduleTask(com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.TaskManagerReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteScheduleTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void executeTask(com.cpdss.common.generated.TaskManager.ExecuteTaskRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.ExecuteTaskReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExecuteTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getScheduledTasks(com.cpdss.common.generated.TaskManager.SchedulededTaskRequest request,
        io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.SchedulededTaskReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetScheduledTasksMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TaskManagerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<TaskManagerServiceBlockingStub> {
    private TaskManagerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskManagerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskManagerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.cpdss.common.generated.TaskManager.TaskManagerReply scheduleTask(com.cpdss.common.generated.TaskManager.ScheduleTaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getScheduleTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.TaskManager.TaskManagerReply deleteScheduleTask(com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteScheduleTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.TaskManager.ExecuteTaskReply executeTask(com.cpdss.common.generated.TaskManager.ExecuteTaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExecuteTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.cpdss.common.generated.TaskManager.SchedulededTaskReply getScheduledTasks(com.cpdss.common.generated.TaskManager.SchedulededTaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetScheduledTasksMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TaskManagerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<TaskManagerServiceFutureStub> {
    private TaskManagerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TaskManagerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TaskManagerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.TaskManager.TaskManagerReply> scheduleTask(
        com.cpdss.common.generated.TaskManager.ScheduleTaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getScheduleTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.TaskManager.TaskManagerReply> deleteScheduleTask(
        com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteScheduleTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.TaskManager.ExecuteTaskReply> executeTask(
        com.cpdss.common.generated.TaskManager.ExecuteTaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExecuteTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.cpdss.common.generated.TaskManager.SchedulededTaskReply> getScheduledTasks(
        com.cpdss.common.generated.TaskManager.SchedulededTaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetScheduledTasksMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SCHEDULE_TASK = 0;
  private static final int METHODID_DELETE_SCHEDULE_TASK = 1;
  private static final int METHODID_EXECUTE_TASK = 2;
  private static final int METHODID_GET_SCHEDULED_TASKS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TaskManagerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TaskManagerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SCHEDULE_TASK:
          serviceImpl.scheduleTask((com.cpdss.common.generated.TaskManager.ScheduleTaskRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.TaskManagerReply>) responseObserver);
          break;
        case METHODID_DELETE_SCHEDULE_TASK:
          serviceImpl.deleteScheduleTask((com.cpdss.common.generated.TaskManager.ScheduleTaskDeleteRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.TaskManagerReply>) responseObserver);
          break;
        case METHODID_EXECUTE_TASK:
          serviceImpl.executeTask((com.cpdss.common.generated.TaskManager.ExecuteTaskRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.ExecuteTaskReply>) responseObserver);
          break;
        case METHODID_GET_SCHEDULED_TASKS:
          serviceImpl.getScheduledTasks((com.cpdss.common.generated.TaskManager.SchedulededTaskRequest) request,
              (io.grpc.stub.StreamObserver<com.cpdss.common.generated.TaskManager.SchedulededTaskReply>) responseObserver);
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

  private static abstract class TaskManagerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TaskManagerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.cpdss.common.generated.TaskManager.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TaskManagerService");
    }
  }

  private static final class TaskManagerServiceFileDescriptorSupplier
      extends TaskManagerServiceBaseDescriptorSupplier {
    TaskManagerServiceFileDescriptorSupplier() {}
  }

  private static final class TaskManagerServiceMethodDescriptorSupplier
      extends TaskManagerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TaskManagerServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (TaskManagerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TaskManagerServiceFileDescriptorSupplier())
              .addMethod(getScheduleTaskMethod())
              .addMethod(getDeleteScheduleTaskMethod())
              .addMethod(getExecuteTaskMethod())
              .addMethod(getGetScheduledTasksMethod())
              .build();
        }
      }
    }
    return result;
  }
}
