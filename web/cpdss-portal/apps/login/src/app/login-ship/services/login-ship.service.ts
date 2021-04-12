import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
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
    return this.http.post<ILoginShipResponse>(`${AppConfigurationService?.settings?.apiUrl}api/ship/authenticate`, loginData); 
  }
}
