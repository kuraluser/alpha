import { TestBed } from '@angular/core/testing';

import { LoadingTransformationService } from './loading-transformation.service';

describe('LoadingTransformationService', () => {
  let service: LoadingTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
