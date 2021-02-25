import { TestBed } from '@angular/core/testing';

import { UserRolePermissionApiService } from './user-role-permission-api.service';

describe('UserRolePermissionApiService', () => {
  let service: UserRolePermissionApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserRolePermissionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
