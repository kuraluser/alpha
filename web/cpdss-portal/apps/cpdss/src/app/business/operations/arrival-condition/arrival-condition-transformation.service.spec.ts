import { TestBed } from '@angular/core/testing';

import { ArrivalConditionTransformationService } from './arrival-condition-transformation.service';

describe('ArrivalConditionTransformationService', () => {
  let service: ArrivalConditionTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArrivalConditionTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
