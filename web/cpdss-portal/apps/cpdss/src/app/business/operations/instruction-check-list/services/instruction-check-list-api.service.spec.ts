import { TestBed } from '@angular/core/testing';

import { InstructionCheckListApiService } from './instruction-check-list-api.service';

describe('InstructionCheckListApiService', () => {
  let service: InstructionCheckListApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InstructionCheckListApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
