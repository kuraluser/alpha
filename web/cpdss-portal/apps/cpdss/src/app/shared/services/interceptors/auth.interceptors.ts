import { Injectable, Optional } from '@angular/core';
import {
  HttpInterceptor, HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpErrorResponse
} from '@angular/common/http';

import { from, Observable } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { SecurityService } from '../security/security.service';
import { GlobalErrorHandler } from '../error-handlers/global-error-handler';
import { environment } from '../../../../environments/environment';
import { KeycloakService } from 'keycloak-angular';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

/**
 *  interceptor for API calls
 *  calls securityservice for getting authorization token
 */

@Injectable()
export class HttpAuthInterceptor implements HttpInterceptor {

  constructor(private globalErrorHandler: GlobalErrorHandler, @Optional() private keycloakService: KeycloakService) { }

  /**
   *  initiates interceptor with http module
   */
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return from(this.processRequest(request, next));

  }

  /**
   * Updating request headers
   *
   * @private
   * @param {HttpRequest<any>} request
   * @param {HttpHandler} next
   * @return {*}  {Promise<HttpEvent<any>>}
   * @memberof HttpAuthInterceptor
   */
  private async processRequest(request: HttpRequest<any>, next: HttpHandler): Promise<HttpEvent<any>> {
    if (localStorage.getItem('realm')) {
      request = request.clone({ headers: request.headers.set('X-TenantID', localStorage.getItem('realm')) });
    }
    // Only work if keycloak instance is set. In case of ship refresh token will not be attempted as keycloak service is null
    const keycloakInstance = this.keycloakService?.getKeycloakInstance();
    // Attempt to refersh the token ifthe token is going to be expired in 180seconds or lesser.
    const refreshed = await keycloakInstance?.updateToken(AppConfigurationService.settings?.tokenMinValidity ?? 180);
    if (refreshed) {
      SecurityService.setAuthToken(keycloakInstance.token);
      navigator.serviceWorker.ready.then(() => {
        SecurityService.setPropertiesDB(keycloakInstance.token, 'token');
      });
    }
    const token: string = SecurityService.getAuthToken();
    if(token) {
      // Add token to request header
      request = request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + token) });
    }
    return next.handle(request).pipe(
      retry(1),
      map((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          const refreshedToken = event.headers.get('token');
          if (environment.name !== 'shore' && refreshedToken) {
            SecurityService.setAuthToken(refreshedToken)
            SecurityService.initPropertiesDB(refreshedToken);
          }
        }
        return event;
      }), catchError((error) => {
        if (error instanceof HttpErrorResponse) {
          this.globalErrorHandler.handleError(error);
        }
        throw error;
      })).toPromise();
  }
}
