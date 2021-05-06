import { TestBed } from '@angular/core/testing';

import { PortMasterApiService } from './port-master-api.service';

describe('PortMasterApiService', () => {
  let service: PortMasterApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortMasterApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
