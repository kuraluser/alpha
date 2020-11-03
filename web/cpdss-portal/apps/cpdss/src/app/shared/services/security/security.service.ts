import { Injectable } from '@angular/core';
import { UserProfile } from "../../models/user-profile.model";

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

  private static token: string;
  private static userProfile: UserProfile;
  private static loggedIn = false;

  constructor() { }

  // setting token
  static setAuthToken(jwtToken: string) {
    SecurityService.token = jwtToken;
  }

  // getting token
  static getAuthToken(): string {
    return SecurityService.token;
  }

  // setting logged-in user details
  static setUserProfile(userDetails: UserProfile) {
    SecurityService.userProfile = {
      email: userDetails.email,
      userName: userDetails.userName
    }
  }

  // getting logged-in user details
  static getUserProfile(): UserProfile {
    return SecurityService.userProfile;
  }

  // setting current login status
  static setLoginStatus(status: boolean) {
    SecurityService.loggedIn = status;
  }

  // getting current login status
  static getLoginStatus(): boolean {
    return SecurityService.loggedIn;
  }

  /**
   * After logout rest all User details
   *
   * @static
   * @memberof SecurityService
   */
  static userLogoutAction() {
    SecurityService.token = null;
    SecurityService.userProfile = null;
    SecurityService.loggedIn = false;
    window.localStorage.clear();
  }

}
