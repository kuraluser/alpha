import { TestBed } from '@angular/core/testing';

import { LoadingDischargingManageSequenceTransformationService } from './loading-discharging-manage-sequence-transformation.service';

describe('LoadingDischargingManageSequenceTransformationService', () => {
  let service: LoadingDischargingManageSequenceTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingDischargingManageSequenceTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
