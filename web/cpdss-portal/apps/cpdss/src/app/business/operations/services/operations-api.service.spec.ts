import { TestBed } from '@angular/core/testing';

import { OperationsApiService } from './operations-api.service';

describe('OperationsApiService', () => {
  let service: OperationsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OperationsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
