import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ICrewMasterListResponse } from '../../admin/models/crew.model';
import { IVoyageResponse, NewVoyageModel, NewVoyageResponseModel, Voyage } from '../models/common.model';

@Injectable()
export class VoyageService {
  private _voyages: Voyage[];

  constructor(private commonApiService: CommonApiService) { }

  /**
   * Vessel details api result mock
   */
  getVoyagesByVesselId(vesselId: number): Observable<Voyage[]> {
    return this.commonApiService.get<IVoyageResponse>(`vessels/${vesselId}/voyages`).pipe(map((response) => {
      this._voyages = response.voyages;
      return this._voyages;
    }));

  }

  /**
   * Save new-voyage api
   */
  saveNewVoyageData(newVoyage: NewVoyageModel, vesselId: number): Observable<NewVoyageResponseModel> {
    return this.commonApiService.post<NewVoyageModel, NewVoyageResponseModel>(`vessels/${vesselId}/voyages`, newVoyage);
  }

  /**
   * Set validation Error to form control
   */
  setValidationErrorMessage() {
    return {
      voyageNo: {
        'required': 'VOYAGE_POPUP_VOYAGE_NO_REQUIRED_ERROR',
        'maxlength': 'NEW_VOYAGE_POPUP_CHARACTER_LIMIT',
        'specialCharacter': 'NEW_VOYAGE_POPUP_SPECIAL_CHARACTER_NOT_ALLOWED',
        'duplicate': 'NEW_VOYAGE_ALREADY_EXIST'
      },
      start_date: {
        'required': 'VOYAGE_POPUP_START_DATE_REQUIRED_ERROR',
        'failedCompare': 'VOYAGE_POPUP_START_DATE_COMPARE_ERROR'
      },
      end_date: {
        'required': 'VOYAGE_POPUP_END_DATE_REQUIRED_ERROR',
        'failedCompare': 'VOYAGE_POPUP_END_DATE_COMPARE_ERROR'
      },
      selectStartDateTimeZone: {
        'required': 'VOYAGE_POPUP_START_DATE_TIME_ZONE_REQUIRED_ERROR',
      },
      selectEndDateTimeZone: {
        'required': 'VOYAGE_POPUP_END_DATE_TIME_ZONE_REQUIRED_ERROR'
      },
      captain: {
        'required': 'VOYAGE_POPUP_CAPTIAN_REQUIRED_ERROR'
      },
      chiefOfficer: {
        'required': 'VOYAGE_POPUP_CHIEF_OFFICER_REQUIRED_ERROR'
      }
    }
  }

  /**
   *
   * @param vesselId to get crewdetails
   * @returns
   */
  getCrewDetails(vesselId:number){
    return  this.commonApiService.get<ICrewMasterListResponse>(`/master/crewdetails?vesselId=${vesselId}`);
  }

}
