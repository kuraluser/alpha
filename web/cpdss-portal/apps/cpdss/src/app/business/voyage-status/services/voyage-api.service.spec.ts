import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, waitForAsync } from '@angular/core/testing';

import { VoyageApiService } from './voyage-api.service';

describe('VoyageApiService', () => {
  let service: VoyageApiService;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [VoyageApiService]
    });
    service = TestBed.inject(VoyageApiService);
  }));

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
