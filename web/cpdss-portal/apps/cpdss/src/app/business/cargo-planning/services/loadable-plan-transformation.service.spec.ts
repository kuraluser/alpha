import { TestBed } from '@angular/core/testing';

import { LoadablePlanTransformationService } from './loadable-plan-transformation.service';

describe('LoadablePlanTransformationService', () => {
  let service: LoadablePlanTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadablePlanTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
