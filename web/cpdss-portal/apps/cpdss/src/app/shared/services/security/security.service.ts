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

  private static token: string = '';
  private static userProfile: UserProfile;
  private static loggedIn: boolean = false;

  constructor() { }

  // setting token
  public static setAuthToken(jwtToken: string) {
    this.token = jwtToken;
  }

  // getting token
  public static getAuthToken(): string {
    return this.token;
  }

  // setting logged-in user details
  public static setUserProfile(userDetails: UserProfile) {
    this.userProfile = {
      email: userDetails.email,
      userName: userDetails.userName
    }
  }

  // getting logged-in user details
  public static getUserProfile(): UserProfile {
    return this.userProfile;
  }

  // setting current login status
  public static setLoginStatus(status: boolean) {
    this.loggedIn = status;
  }

  // getting current login status
  public static getLoginStatus(): boolean {
    return this.loggedIn;
  }

}
