import { TestBed } from '@angular/core/testing';

import { DischargeStudyDetailsApiService } from './discharge-study-details-api.service';

describe('DischargeStudyDetailsApiService', () => {
  let service: DischargeStudyDetailsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargeStudyDetailsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
