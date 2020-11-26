import { TestBed } from '@angular/core/testing';

import { LoadableQuantityApiService } from './loadable-quantity-api.service';

describe('LoadableQuantityService', () => {
  let service: LoadableQuantityApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoadableQuantityApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
