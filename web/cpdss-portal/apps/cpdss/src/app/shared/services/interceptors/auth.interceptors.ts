import { Injectable } from '@angular/core';
import {
    HttpInterceptor,
    HttpRequest,
    HttpResponse,
    HttpHandler,
    HttpEvent,
    HttpErrorResponse
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { SecurityService } from '../security/security.service';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 *  interceptor for API calls
 *  calls securityservice for getting authorization token
 */

@Injectable()
export class HttpAuthInterceptor implements HttpInterceptor {

    constructor(private ngxSpinnerService: NgxSpinnerService) { }

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

        this.ngxSpinnerService.show();
        return next.handle(request).pipe(
            map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    // console.log('event--->>>', event);
                    this.ngxSpinnerService.hide();
                }
                return event;
            }), catchError((error) => {
                this.ngxSpinnerService.hide();
                throw error;
            }));
    }
}