import { TestBed } from '@angular/core/testing';

import { EditPortRotationApiService } from './edit-port-rotation-api.service';

describe('EditPortRotationApiService', () => {
  let service: EditPortRotationApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EditPortRotationApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
