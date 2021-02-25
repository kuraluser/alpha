import { TestBed } from '@angular/core/testing';

import { SynopticalApiService } from './synoptical-api.service';

describe('SynopticalApiService', () => {
  let service: SynopticalApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SynopticalApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
