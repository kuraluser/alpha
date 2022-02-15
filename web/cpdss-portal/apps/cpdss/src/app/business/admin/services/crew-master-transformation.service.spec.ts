import { TestBed } from '@angular/core/testing';

import { CrewMasterTransformationService } from './crew-master-transformation.service';

describe('CrewMasterTransformationService', () => {
  let service: CrewMasterTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrewMasterTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
