import { TestBed } from '@angular/core/testing';

import { FleetApiService } from './fleet-api.service';

describe('FleetApiService', () => {
  let service: FleetApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FleetApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
