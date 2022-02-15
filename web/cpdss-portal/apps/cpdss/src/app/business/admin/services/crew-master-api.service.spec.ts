import { TestBed } from '@angular/core/testing';

import { CrewMasterApiService } from './crew-master-api.service';

describe('CrewMasterApiService', () => {
  let service: CrewMasterApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrewMasterApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
