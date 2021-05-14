import { TestBed } from '@angular/core/testing';

import { PortRotationPopupApiService } from './port-rotation-popup-api.service';

describe('PortRotationPopupApiService', () => {
  let service: PortRotationPopupApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortRotationPopupApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
