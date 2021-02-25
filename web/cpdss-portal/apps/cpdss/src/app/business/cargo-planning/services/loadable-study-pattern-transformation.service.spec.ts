import { TestBed } from '@angular/core/testing';

import { LoadableStudyPatternTransformationService } from './loadable-study-pattern-transformation.service';

describe('LoadableStudyPatternTransformationService', () => {
  let service: LoadableStudyPatternTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadableStudyPatternTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
