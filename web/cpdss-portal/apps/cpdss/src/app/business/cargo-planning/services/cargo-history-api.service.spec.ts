import { TestBed } from '@angular/core/testing';

import { CargoHistoryApiService } from './cargo-history-api.service';

describe('CargoHistoryApiService', () => {
  let service: CargoHistoryApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargoHistoryApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
