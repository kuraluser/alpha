import { TestBed } from '@angular/core/testing';

import { RulesTransformationService } from './rules-transformation.service';

describe('RulesTransformationService', () => {
  let service: RulesTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RulesTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
