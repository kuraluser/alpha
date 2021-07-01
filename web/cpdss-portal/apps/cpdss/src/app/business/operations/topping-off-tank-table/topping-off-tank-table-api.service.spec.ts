import { TestBed } from '@angular/core/testing';

import { ToppingOffTankTableApiService } from './topping-off-tank-table-api.service';

describe('ToppingOffTankTableApiService', () => {
  let service: ToppingOffTankTableApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToppingOffTankTableApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
