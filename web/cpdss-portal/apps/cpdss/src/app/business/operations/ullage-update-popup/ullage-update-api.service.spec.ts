import { TestBed } from '@angular/core/testing';

import { UllageUpdateApiService } from './ullage-update-api.service';

describe('UllageUpdateApiService', () => {
  let service: UllageUpdateApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UllageUpdateApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
