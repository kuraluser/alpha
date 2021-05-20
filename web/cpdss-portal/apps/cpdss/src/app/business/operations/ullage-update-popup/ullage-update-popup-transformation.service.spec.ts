import { TestBed } from '@angular/core/testing';

import { UllageUpdatePopupTransformationService } from './ullage-update-popup-transformation.service';

describe('UllageUpdatePopupTransformationService', () => {
  let service: UllageUpdatePopupTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UllageUpdatePopupTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
