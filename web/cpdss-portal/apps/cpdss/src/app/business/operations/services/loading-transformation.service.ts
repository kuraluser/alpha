import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

/**
 * Transformation Service for Loading  module
 *
 * @export
 * @class LoadingTransformationService
 */
@Injectable()
export class LoadingTransformationService {
  private _loadingInformationSource: Subject<boolean> = new Subject();
  private _unitChangeSource: Subject<boolean> = new Subject();

  loadingInformationValidity$ = this._loadingInformationSource.asObservable();
  unitChange$ = this._unitChangeSource.asObservable();
  constructor() { }

  /** Set loading information complete status */
  setLoadingInformationValidity(isValid: boolean) {
    this._loadingInformationSource.next(isValid);
  }

    /** Set unit changed */
    setUnitChanged(unitChanged: boolean) {
      this._unitChangeSource.next(unitChanged);
    }
  
}
