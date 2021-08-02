import { TestBed } from '@angular/core/testing';

import { LoadingDischargingTransformationService } from './loading-discharging-transformation.service';

describe('LoadingDischargingTransformationService', () => {
  let service: LoadingDischargingTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
