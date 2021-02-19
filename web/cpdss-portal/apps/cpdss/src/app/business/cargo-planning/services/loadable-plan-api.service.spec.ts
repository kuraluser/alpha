import { TestBed } from '@angular/core/testing';

import { LoadablePlanApiService } from './loadable-plan-api.service';

describe('LoadablePlanApiService', () => {
  let service: LoadablePlanApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadablePlanApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
