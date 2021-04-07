import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { CPDSSDB } from '../../models/common.model';
import { IUserProfile } from "../../models/user-profile.model";
import { AppConfigurationService } from '../app-configuration/app-configuration.service';

/**
 *  service class for setting auth-token and user details
 */

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  /**
   *  static functions can be used in other classes without creating instance of the service
   */

  private static _TOKEN: string;
  private static _USER: IUserProfile;
  private static _LOGGED_IN = false;
  private static cpdssDB = new CPDSSDB();

  constructor() { }

  // setting token
  static setAuthToken(jwtToken: string) {
    SecurityService._TOKEN = jwtToken;
    localStorage.setItem('token', jwtToken);
  }

  // getting token
  static getAuthToken(): string {
    return localStorage.getItem('token');
  }

  // getting properties in indexed db
  static async getPropertiesDB(key: string) {
    return await SecurityService.cpdssDB?.properties?.get(key)
  }

  // setting properties in indexed db
  static setPropertiesDB(value: string, key: string) {
    SecurityService.cpdssDB?.properties.add(value, key)
  }

  // setting logged-in user details
  static setUserProfile(userDetails: IUserProfile) {
    localStorage.setItem('userDetails', JSON.stringify(userDetails))
    SecurityService._USER = userDetails;
  }

  // getting logged-in user details
  static getUserProfile(): IUserProfile {
    SecurityService._USER = JSON.parse(localStorage.getItem('userDetails'));
    return SecurityService._USER;
  }

  // setting current login status
  static setLoginStatus(status: boolean) {
    SecurityService._LOGGED_IN = status;
  }

  // getting current login status
  static getLoginStatus(): boolean {
    return SecurityService._LOGGED_IN;
  }

  /**
   * After logout rest all User details
   *
   * @static
   * @memberof SecurityService
   */
  static userLogoutAction() {
    SecurityService._TOKEN = null;
    SecurityService._USER = null;
    SecurityService._LOGGED_IN = false;
    window.localStorage.clear();
    SecurityService.cpdssDB?.properties?.clear();
  }

  /**
   * Initialise properties db in indexed db while login
   *
   * @static
   * @param {*} token
   * @memberof SecurityService
   */
  static async initPropertiesDB(token) {
    const serviceWorkerReady = await navigator.serviceWorker.ready;
    if (serviceWorkerReady) {
      SecurityService.setPropertiesDB(token, 'token');
      SecurityService.setPropertiesDB(environment.name, 'environment');
      SecurityService.setPropertiesDB(JSON.parse(JSON.stringify(AppConfigurationService?.settings)), 'appConfig');
    }
  }

}
