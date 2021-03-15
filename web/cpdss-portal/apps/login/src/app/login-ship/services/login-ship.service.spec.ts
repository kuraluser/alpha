import { TestBed } from '@angular/core/testing';

import { LoginShipService } from './login-ship.service';

describe('LoginShipService', () => {
  let service: LoginShipService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginShipService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
