import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ILoginShip, ILoginShipResponse } from '../model/login-ship.model';

/**
 * Api Service for ship login
 *
 * @export
 * @class LoginShipService
 */
@Injectable({
  providedIn: 'root'
})
export class LoginShipService {

  constructor(private http: HttpClient) { }

  /**
   * validate login
   * @param loginData 
   */
  validateShipLogin(loginData: ILoginShip): Observable<ILoginShipResponse>{
    return this.http.post<ILoginShipResponse>(`api/ship/authenticate`, loginData); 
  }
}
