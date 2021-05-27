import { TestBed } from '@angular/core/testing';

import { LoadingDischargingCargoDetailsTransformationService } from './loading-discharging-cargo-details-transformation.service';

describe('LoadingDischargingCargoDetailsTransformationService', () => {
  let service: LoadingDischargingCargoDetailsTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingCargoDetailsTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
