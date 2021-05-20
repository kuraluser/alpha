import { TestBed } from '@angular/core/testing';

import { QuantityDecimalService } from './quantity-decimal.service';

describe('QuantityDecimalService', () => {
  let service: QuantityDecimalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuantityDecimalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
