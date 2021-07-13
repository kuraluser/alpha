import { TestBed } from '@angular/core/testing';

import { DischargeStudyListTransformationApiService } from './discharge-study-list-transformation-api.service';

describe('DischargeStudyListTransformationApiService', () => {
  let service: DischargeStudyListTransformationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargeStudyListTransformationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
