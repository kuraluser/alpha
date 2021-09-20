import { TestBed } from '@angular/core/testing';

import { DischargingPlanApiService } from './discharging-plan-api.service';

describe('DischargingPlanApiService', () => {
  let service: DischargingPlanApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargingPlanApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
