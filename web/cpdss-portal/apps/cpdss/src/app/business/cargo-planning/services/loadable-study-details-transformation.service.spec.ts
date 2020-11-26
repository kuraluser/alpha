import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { LoadableStudyDetailsTransformationService } from './loadable-study-details-transformation.service';

describe('LoadableStudyDetailsTransformationService', () => {
  let service: LoadableStudyDetailsTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoadableStudyDetailsTransformationService]
    });
    service = TestBed.inject(LoadableStudyDetailsTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
