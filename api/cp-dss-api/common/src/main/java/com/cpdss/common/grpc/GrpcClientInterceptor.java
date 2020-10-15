package com.cpdss.common.grpc;

import com.cpdss.common.utils.TenantContext;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * Interceptor class for Grpc client
 *
 * @author krishna
 */
public class GrpcClientInterceptor implements ClientInterceptor {

  /** Client side method intercept call */
  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    return new SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        if (TenantContext.getCurrentTenant() != null) {
          /* put custom header for multitenancy */
          headers.put(
              Metadata.Key.of("X-TenantID", Metadata.ASCII_STRING_MARSHALLER),
              TenantContext.getCurrentTenant());
        }
        super.start(
            new SimpleForwardingClientCallListener<RespT>(responseListener) {
              @Override
              public void onHeaders(Metadata headers) {
                // Custom headers from the server
                super.onHeaders(headers);
              }
            },
            headers);
      }
    };
  }
}
