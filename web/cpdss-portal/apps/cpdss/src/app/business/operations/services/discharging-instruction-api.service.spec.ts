import { TestBed } from '@angular/core/testing';

import { DischargingInstructionApiService } from './discharging-instruction-api.service';

describe('DischargingInstructionApiService', () => {
  let service: DischargingInstructionApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DischargingInstructionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
