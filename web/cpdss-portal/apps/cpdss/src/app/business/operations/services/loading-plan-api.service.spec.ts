import { TestBed } from '@angular/core/testing';

import { LoadingPlanApiService } from './loading-plan-api.service';

describe('LoadingPlanApiService', () => {
  let service: LoadingPlanApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingPlanApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
