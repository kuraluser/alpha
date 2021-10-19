import { TestBed } from '@angular/core/testing';

import { VesselInformationApiService } from './vessel-information-api.service';

describe('VesselInformationApiService', () => {
  let service: VesselInformationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VesselInformationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
