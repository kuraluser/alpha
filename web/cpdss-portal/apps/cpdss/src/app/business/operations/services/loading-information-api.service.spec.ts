import { TestBed } from '@angular/core/testing';

import { LoadingInformationApiService } from './loading-information-api.service';

describe('LoadingInformationApiService', () => {
  let service: LoadingInformationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingInformationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
