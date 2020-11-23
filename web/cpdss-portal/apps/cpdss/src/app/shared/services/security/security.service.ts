import { Injectable } from '@angular/core';
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

  constructor() { }

  // setting token
  static setAuthToken(jwtToken: string) {
    SecurityService._TOKEN = jwtToken;
  }

  // getting token
  static getAuthToken(): string {
    return SecurityService._TOKEN;
  }

  // setting logged-in user details
  static setUserProfile(userDetails: IUserProfile) {
    SecurityService._USER = userDetails;
  }

  // getting logged-in user details
  static getUserProfile(): IUserProfile {
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
  }

}
