import { TestBed } from '@angular/core/testing';

import { LoadingDischargingInformationApiService } from './loading-discharging-information-api.service';

describe('LoadingDischargingInformationApiService', () => {
  let service: LoadingDischargingInformationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingInformationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
