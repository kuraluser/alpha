import { TestBed } from '@angular/core/testing';

import { DischargeStudyDetailsTransformationService } from './discharge-study-details-transformation.service';

describe('DischargeStudyDetailsTransformationService', () => {
  let service: DischargeStudyDetailsTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargeStudyDetailsTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
