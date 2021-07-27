import { TestBed } from '@angular/core/testing';

import { CargoMasterApiService } from './cargo-master-api.service';

describe('CargoMasterApiService', () => {
  let service: CargoMasterApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargoMasterApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
