import { TestBed } from '@angular/core/testing';

import { CargoHistoryTransformationService } from './cargo-history-transformation.service';

describe('CargoHistoryTransformationService', () => {
  let service: CargoHistoryTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargoHistoryTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
