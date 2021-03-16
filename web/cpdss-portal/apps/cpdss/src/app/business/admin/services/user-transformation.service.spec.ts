import { TestBed } from '@angular/core/testing';

import { UserTransformationService } from './user-transformation.service';

describe('UserTransformationService', () => {
  let service: UserTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
