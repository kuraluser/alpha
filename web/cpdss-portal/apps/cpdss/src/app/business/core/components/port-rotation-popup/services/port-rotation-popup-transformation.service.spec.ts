import { TestBed } from '@angular/core/testing';

import { PortRotationPopupTransformationService } from './port-rotation-popup-transformation.service';

describe('PortRotationPopupTransformationService', () => {
  let service: PortRotationPopupTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortRotationPopupTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
