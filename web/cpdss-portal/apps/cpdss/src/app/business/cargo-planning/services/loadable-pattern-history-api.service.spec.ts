import { TestBed } from '@angular/core/testing';

import { LoadablePatternHistoryApiService } from './loadable-pattern-history-api.service';

describe('LoadablePatternHistoryApiService', () => {
  let service: LoadablePatternHistoryApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadablePatternHistoryApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
