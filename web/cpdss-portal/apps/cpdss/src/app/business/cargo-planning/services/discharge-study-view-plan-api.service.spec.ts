import { TestBed } from '@angular/core/testing';

import { DischargeStudyViewPlanApiService } from './discharge-study-view-plan-api.service';

describe('DischargeStudyViewPlanApiService', () => {
  let service: DischargeStudyViewPlanApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargeStudyViewPlanApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
