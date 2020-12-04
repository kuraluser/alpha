import { TestBed } from '@angular/core/testing';

import { CommingleApiService } from './commingle-api.service';

describe('CommingleApiService', () => {
  let service: CommingleApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommingleApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
