import { Injectable } from '@angular/core';
import { environment } from 'apps/cpdss/src/environments/environment';
import { CPDSSDB, PropertiesDB } from '../../models/common.model';
import { IUserProfile } from "../../models/user-profile.model";

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
  private static propertiesDB = new PropertiesDB()

  constructor() { }

  // setting token
  static setAuthToken(jwtToken: string) {
    SecurityService._TOKEN = jwtToken;
    localStorage.setItem('token', jwtToken);
    this.propertiesDB.properties.add(jwtToken, 'token')
  }

  // getting token
  static getAuthToken(): string {
    return localStorage.getItem('token');
  }

  // getting environment
  static async getEnvironment() {
    return await this.propertiesDB.properties.get('environment')
  }

  // setting environment
  static setEnvironment(environment: string) {
    localStorage.setItem('environment', environment);
    this.propertiesDB.properties.add(environment, 'environment')
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
    this.propertiesDB.properties.clear();
  }

}
