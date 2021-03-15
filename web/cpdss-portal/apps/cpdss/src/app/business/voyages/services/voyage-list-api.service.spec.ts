import { TestBed } from '@angular/core/testing';

import { VoyageListApiService } from './voyage-list-api.service';

describe('VoyageListApiService', () => {
  let service: VoyageListApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoyageListApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
