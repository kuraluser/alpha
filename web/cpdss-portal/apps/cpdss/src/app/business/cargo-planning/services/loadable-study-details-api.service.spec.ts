import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { LoadableStudyDetailsApiService } from './loadable-study-details-api.service';

describe('LoadableStudyDetailsApiService', () => {
  let service: LoadableStudyDetailsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoadableStudyDetailsApiService]
    });
    service = TestBed.inject(LoadableStudyDetailsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
