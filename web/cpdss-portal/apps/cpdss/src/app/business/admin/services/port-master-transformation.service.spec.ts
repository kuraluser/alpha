import { TestBed } from '@angular/core/testing';

import { PortMasterTransformationService } from './port-master-transformation.service';

describe('PortMasterTransformationService', () => {
  let service: PortMasterTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PortMasterTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
