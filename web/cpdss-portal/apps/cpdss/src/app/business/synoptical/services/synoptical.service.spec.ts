import { TestBed } from '@angular/core/testing';

import { SynopticalService } from './synoptical.service';

describe('SynopticalService', () => {
  let service: SynopticalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SynopticalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
