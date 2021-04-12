import { TestBed } from '@angular/core/testing';

import { NavbarApiService } from './navbar-api.service';

describe('NavbarApiService', () => {
  let service: NavbarApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NavbarApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
