import { TestBed } from '@angular/core/testing';

import { VesselInformationTransformationService } from './vessel-information-transformation.service';

describe('VesselInformationTransformationService', () => {
  let service: VesselInformationTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VesselInformationTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
