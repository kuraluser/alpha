import { TestBed } from '@angular/core/testing';

import { FileRepositoryTransformationService } from './file-repository-transformation.service';

describe('FileRepositoryTransformationService', () => {
  let service: FileRepositoryTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileRepositoryTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
