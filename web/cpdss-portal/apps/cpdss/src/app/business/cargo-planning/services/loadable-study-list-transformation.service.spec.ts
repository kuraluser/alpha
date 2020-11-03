import { TestBed } from '@angular/core/testing';

import { LoadableStudyListTransformationService } from './loadable-study-list-transformation.service';

describe('LoadableStudyListTransformationService', () => {
  let service: LoadableStudyListTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadableStudyListTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
