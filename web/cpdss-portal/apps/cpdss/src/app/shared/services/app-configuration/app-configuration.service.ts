import { Injectable } from '@angular/core';
import { IAppConfiguration } from './app-configuration.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { IDictionary } from '../../models/common.model';

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
    const jsonFile = `assets/config/config.${environment.name}.${environment.config}.json`;
    try {
      const result: IAppConfiguration = (await this.http.get(jsonFile).toPromise()) as IAppConfiguration;
      AppConfigurationService.settings = result;
      result.permissionMapping = <IDictionary<string>>await this.http.get(`assets/config/permission-mapping.config.json`).toPromise();
      result.documentationSiteMapping = <IDictionary<string>>await this.http.get(`assets/config/documentation-url-mapping.json`).toPromise();
      return AppConfigurationService.settings;
    }
    catch (e) {
      console.error(e);
      throw e;
    }
  }
}
