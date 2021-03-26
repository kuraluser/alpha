import { TestBed } from '@angular/core/testing';

import { TimeZoneTransformationService } from './time-zone-transformation.service';

describe('TimeZoneTransformationService', () => {
  let service: TimeZoneTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimeZoneTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
