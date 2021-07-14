import { TestBed } from '@angular/core/testing';

import { LoadingApiService } from './loading-api.service';

describe('LoadingApiService', () => {
  let service: LoadingApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
