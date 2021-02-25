import { TestBed } from '@angular/core/testing';

import { VoyageStatusTransformationService } from './voyage-status-transformation.service';

describe('VoyageStatusTransformationService', () => {
  let service: VoyageStatusTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoyageStatusTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
