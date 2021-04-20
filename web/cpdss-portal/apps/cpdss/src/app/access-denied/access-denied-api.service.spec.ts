import { TestBed } from '@angular/core/testing';

import { AccessDeniedApiService } from './access-denied-api.service';

describe('AccessDeniedApiService', () => {
  let service: AccessDeniedApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccessDeniedApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
