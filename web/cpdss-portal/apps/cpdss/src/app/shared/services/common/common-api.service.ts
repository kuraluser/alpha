import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'apps/cpdss/src/environments/environment';
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
   *  apiGetter function will concatenate api host url, host port (if needed), host sub-domain and service url
   *  eg: {http://localhost} + {:} + {8080} + {/} + {api/cloud/} + {companies/mol.cpdss.com/idp-info}
   */
  public apiGetter(apiUri: string) {
    return AppConfigurationService.settings.apiUrl + environment.uriPath + apiUri;
  }

  /**
   *  returns http GET
   */
  public get(apiUri: string, params?: string) {
    let reqParam = params ? '/' + params : '';
    return this.http.get(this.apiGetter(apiUri) + reqParam);
  }

  /**
   *  returns http POST 
   */
  public post(apiUri: string, postData: any) {
    return this.http.post(this.apiGetter(apiUri), postData);
  }

  /**
   *  returns http PUT
   */
  public put(apiUri: string, params: string) {
    return this.http.put(this.apiGetter(apiUri), { params: params });
  }

  /**
   *  returns http DELETE
   */
  public delete(apiUri: string, params: any) {
    return this.http.delete(this.apiGetter(apiUri), { params: params });
  }

}
