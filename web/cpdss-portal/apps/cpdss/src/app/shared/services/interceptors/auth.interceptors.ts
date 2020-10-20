import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpResponse,
    HttpHandler,
    HttpEvent,
    HttpErrorResponse
} from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { SecurityService } from '../security/security.service';

/**
 *  interceptor for API calls
 *  calls securityservice for getting authorization token
 */

@Injectable()
export class HttpAuthInterceptor implements HttpInterceptor {

    constructor() { }

    /**
     *  initiates interceptor with http module
     */
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token: string = SecurityService.getAuthToken();

        if (token) {
            request = request.clone({ headers: request.headers.set('Authorization', 'Bearer ' + token) });
        }

        if (!request.headers.has('Content-Type')) {
            request = request.clone({ headers: request.headers.set('Content-Type', 'application/json') });
        }

        request = request.clone({ headers: request.headers.set('Accept', 'application/json') });

        if (localStorage.getItem('realm')) {
            request = request.clone({ headers: request.headers.set('X-TenantID', localStorage.getItem('realm')) });
        }

        return next.handle(request).pipe(
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    // console.log('event--->>>', event);
                }
                return event;
            }));
    }
}