import { TestBed } from '@angular/core/testing';

import { LoadingDischargingDetailsTransformationService } from './loading-discharging-details-transformation.service';

describe('LoadingDischargingDetailsTransformationService', () => {
  let service: LoadingDischargingDetailsTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingDetailsTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
