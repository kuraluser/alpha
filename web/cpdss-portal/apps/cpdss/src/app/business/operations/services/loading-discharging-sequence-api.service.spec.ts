import { TestBed } from '@angular/core/testing';

import { LoadingDischargingSequenceApiService } from './loading-discharging-sequence-api.service';

describe('LoadingDischargingSequenceApiService', () => {
  let service: LoadingDischargingSequenceApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingSequenceApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
