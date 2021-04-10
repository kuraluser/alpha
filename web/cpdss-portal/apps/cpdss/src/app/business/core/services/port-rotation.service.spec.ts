import { TestBed } from '@angular/core/testing';

import { PortRotationService } from './port-rotation.service';

describe('PortRotationService', () => {
  let service: PortRotationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortRotationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
