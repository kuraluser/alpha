import { TestBed } from '@angular/core/testing';

import { VoyageListTransformationService } from './voyage-list-transformation.service';

describe('VoyageListTransformationService', () => {
  let service: VoyageListTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VoyageListTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
