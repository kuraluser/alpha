import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

/**
 *  common service which will be shared to all components in this module
 */
@Injectable({
  providedIn: 'root'
})
export class CommonApiService {

  constructor(private http: HttpClient) { }

  /**
   *  apiGetter function will concatenate api host url, uri of ship or shore
   *  eg: {http://localhost} + {:} + {8080} + {/} + {api/cloud/} + {companies/mol.cpdss.com/idp-info}
   */
  apiGetter(apiUri: string): string {
    return AppConfigurationService.settings.apiUrl + environment.uriPath + apiUri;
  }

  /**
   *  Common get request handler
   */
  get<T>(apiUri: string, options?: any): Observable<T> {
    const httpHeaders = new HttpHeaders().set('Content-Type', 'application/json')
    return this.http.get<T>(this.apiGetter(apiUri), {headers:httpHeaders , responseType: options?.responseType});
  }

  /**
   *  Common post request handler
   */
  post<N, T>(apiUri: string, body: N): Observable<T> {
    const httpHeaders = new HttpHeaders().set('Content-Type', 'application/json')
    return this.http.post<T>(this.apiGetter(apiUri), body,{headers:httpHeaders});
  }

  /**
   *  Common post request handler
   */
  postFormData<T>(apiUri: string, body: FormData): Observable<T> {
    return this.http.post<T>(this.apiGetter(apiUri), body);
  }

  /**
   *  Common put request handler
   */
  put<N, T>(apiUri: string, body: N): Observable<T> {
    const httpHeaders = new HttpHeaders().set('Content-Type', 'application/json')
    return this.http.put<T>(this.apiGetter(apiUri), body, {headers:httpHeaders});
  }

  /**
   * Common delete request handler
   */
  delete<T>(apiUri: string): Observable<T> {
    const httpHeaders = new HttpHeaders().set('Content-Type', 'application/json')
    return this.http.delete<T>(this.apiGetter(apiUri),{headers:httpHeaders});
  }


/**
 * Common put request handler as formdata.
 *
 * @template T
 * @param {string} apiUri
 * @param {FormData} body
 * @return {*}  {Observable<T>}
 * @memberof CommonApiService
 */
putFormData<T>(apiUri: string, body: FormData): Observable<T> {
    return this.http.put<T>(this.apiGetter(apiUri), body);
  }
}
