import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { RouterTestingModule } from '@angular/router/testing';
import { KeycloakService } from 'keycloak-angular';
import { SecurityService } from '../shared/services/security/security.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [RouterTestingModule],
      providers: [
        KeycloakService,
        SecurityService
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /* it('should load keycloak properties', fakeAsync(() => {
    const profile = { email: 'email', userName: 'username' };
    const fixture = TestBed.createComponent(LoginComponent);
    const component = fixture.debugElement.componentInstance;
    // const service = fixture.debugElement.injector.get(SecurityService.setUserProfile(profile));
    const service = TestBed.inject(SecurityService);
    const ss = new SecurityService();
    const spycanCreateData = jest.spyOn(ss, 'getData').mockReturnValue(true);
    // expect(service.getData(profile)).toEqual(true);

    component.loadKeycloakProperties();
    tick(100);

    expect(component.loadKeycloakProperties()).toHaveBeenCalled();
  })) */

});
