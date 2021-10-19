import { TestBed } from '@angular/core/testing';

import { FileRepositoryApiService } from './file-repository-api.service';

describe('FileRepositoryApiService', () => {
  let service: FileRepositoryApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FileRepositoryApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
