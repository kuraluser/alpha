import { TestBed } from '@angular/core/testing';

import { CargoMasterTransformationService } from './cargo-master-transformation.service';

describe('CargoMasterTransformationService', () => {
  let service: CargoMasterTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargoMasterTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
