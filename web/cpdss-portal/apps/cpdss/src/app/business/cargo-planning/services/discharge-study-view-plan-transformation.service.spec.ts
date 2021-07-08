import { TestBed } from '@angular/core/testing';

import { DischargeStudyViewPlanTransformationService } from './discharge-study-view-plan-transformation.service';

describe('DischargeStudyViewPlanTransformationService', () => {
  let service: DischargeStudyViewPlanTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargeStudyViewPlanTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
