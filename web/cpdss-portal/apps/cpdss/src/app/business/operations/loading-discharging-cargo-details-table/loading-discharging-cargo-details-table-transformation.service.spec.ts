import { TestBed } from '@angular/core/testing';

import { LoadingDischargingCargoDetailsTableTransformationService } from './loading-discharging-cargo-details-table-transformation.service';

describe('LoadingDischargingCargoDetailsTableTransformationService', () => {
  let service: LoadingDischargingCargoDetailsTableTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingCargoDetailsTableTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
