import { TestBed } from '@angular/core/testing';

import { LoadingDischargingBerthTransformationService } from './loading-discharging-berth-transformation.service';

describe('LoadingDischargingBerthTransformationService', () => {
  let service: LoadingDischargingBerthTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingBerthTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
