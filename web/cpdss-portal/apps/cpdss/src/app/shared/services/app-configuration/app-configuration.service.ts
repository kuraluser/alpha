import { Injectable } from '@angular/core';
import { IAppConfiguration } from './app-configuration.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

/**
 * SERVICE FOR APP CONFIG
 */
@Injectable({
  providedIn: 'root'
})
export class AppConfigurationService {

  static settings: IAppConfiguration;

  constructor(private http: HttpClient) {
  }

  /**
   * LOAD CONFIG JSON FILE
   */
  async load(): Promise<IAppConfiguration> {
    const jsonFile = `assets/config/config.${environment.name}.${environment.production ? 'prod' : 'dev'}.json`;
    let result: IAppConfiguration;
    try {
      result = (await this.http.get(jsonFile).toPromise()) as IAppConfiguration;
      AppConfigurationService.settings = result;
      return result;
    }
    catch (e) {
      console.error(e);
      throw e;
    }
  }
}
