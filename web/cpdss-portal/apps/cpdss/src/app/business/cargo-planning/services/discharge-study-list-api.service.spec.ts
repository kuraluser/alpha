import { TestBed } from '@angular/core/testing';

import { DischargeStudyListApiService } from './discharge-study-list-api.service';

describe('DischargeStudyListApiService', () => {
  let service: DischargeStudyListApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargeStudyListApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
