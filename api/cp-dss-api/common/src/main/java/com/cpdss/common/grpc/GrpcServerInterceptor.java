/* Licensed under Apache-2.0 */
package com.cpdss.common.grpc;

import com.cpdss.common.utils.TenantContext;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import java.util.HashSet;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;

/**
 * Interceptor for grpc server method calls
 *
 * @author krishna
 */
@Log4j2
public class GrpcServerInterceptor implements ServerInterceptor {

  private boolean isMultitenant;

  public GrpcServerInterceptor(boolean isMultitenant) {
    this.isMultitenant = isMultitenant;
  }

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {
    if (this.isMultitenant) {
      String tenantID =
          metadata.get(Metadata.Key.of("X-TenantID", Metadata.ASCII_STRING_MARSHALLER));
      if (tenantID == null) {
        log.error("X-TenantID not present in the Request Header");
        throw new ConstraintViolationException(
            "X-TenantID not present in the Request Header", new HashSet<>());
      }
      TenantContext.setCurrentTenant(tenantID);
    }
    return serverCallHandler.startCall(serverCall, metadata);
  }
}
