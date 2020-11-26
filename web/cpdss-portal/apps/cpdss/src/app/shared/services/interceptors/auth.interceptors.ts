import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpResponse, HttpHandler, HttpEvent, HttpErrorResponse
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { catchError, map, retry } from 'rxjs/operators';
import { SecurityService } from '../security/security.service';
import { GlobalErrorHandler } from '../error-handlers/global-error-handler';

/**
 *  interceptor for API calls
 *  calls securityservice for getting authorization token
 */

@Injectable()
export class HttpAuthInterceptor implements HttpInterceptor {

    constructor(private globalErrorHandler: GlobalErrorHandler) { }

    /**
     *  initiates interceptor with http module
     */
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token: string = SecurityService.getAuthToken();

        if (token) {
            request = request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + token) });
        }
        if (localStorage.getItem('realm')) {
            request = request.clone({ headers: request.headers.set('X-TenantID', localStorage.getItem('realm')) });
        }

        return next.handle(request).pipe(
            retry(1),
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                }
                return event;
            }), catchError((error) => {
                if (error instanceof HttpErrorResponse) {
                    this.globalErrorHandler.handleError(error);
                }
                throw error;
            }));
    }
}