import { TestBed } from '@angular/core/testing';

import { LoadingInstructionApiService } from './loading-instruction-api.service';

describe('LoadingInstructionApiService', () => {
  let service: LoadingInstructionApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadingInstructionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
