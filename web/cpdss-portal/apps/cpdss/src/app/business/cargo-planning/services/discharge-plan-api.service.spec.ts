import { TestBed } from '@angular/core/testing';

import { DischargePlanApiService } from './discharge-plan-api.service';

describe('DischargePlanApiService', () => {
  let service: DischargePlanApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargePlanApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
