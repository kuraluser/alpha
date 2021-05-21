import { TestBed } from '@angular/core/testing';

import { DepartureConditionTransformationService } from './departure-condition-transformation.service';

describe('DepartureConditionTransformationService', () => {
  let service: DepartureConditionTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepartureConditionTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
