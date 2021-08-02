import { TestBed } from '@angular/core/testing';

import { FleetTransformationService } from './fleet-transformation.service';

describe('FleetTransformationService', () => {
  let service: FleetTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FleetTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
