import { TestBed } from '@angular/core/testing';

import { UserRolePermissionTransformationService } from './user-role-permission-transformation.service';

describe('UserRolePermissionTransformationService', () => {
  let service: UserRolePermissionTransformationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserRolePermissionTransformationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
