import { TestBed } from '@angular/core/testing';

import { VesselsApiService } from './vessels-api.service';

describe('VesselsApiService', () => {
  let service: VesselsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VesselsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
