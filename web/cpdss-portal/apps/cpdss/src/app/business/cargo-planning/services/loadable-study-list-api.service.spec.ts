import { TestBed } from '@angular/core/testing';

import { LoadableStudyListApiService } from './loadable-study-list-api.service';

describe('LoadableStudyListApiService', () => {
  let service: LoadableStudyListApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadableStudyListApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
