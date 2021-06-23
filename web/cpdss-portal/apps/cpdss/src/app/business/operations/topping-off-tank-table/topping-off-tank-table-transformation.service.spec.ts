import { TestBed } from '@angular/core/testing';

import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';

describe('ToppingOffTankTableTransformationService', () => {
  let service: ToppingOffTankTableTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToppingOffTankTableTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
