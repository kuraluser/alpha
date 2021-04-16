import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

/**
 *  common service for notification updation.
 */
@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  getNotification  = new BehaviorSubject(false);
  constructor() { }
}
