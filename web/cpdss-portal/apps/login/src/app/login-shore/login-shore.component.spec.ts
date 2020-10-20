import { ComponentFixture, TestBed, tick } from '@angular/core/testing';
import { TranslateModule } from '@ngx-translate/core';

import { LoginShoreComponent } from './login-shore.component';

describe('LoginShoreComponent', () => {
  let component: LoginShoreComponent;
  let fixture: ComponentFixture<LoginShoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginShoreComponent ],
      imports:[TranslateModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginShoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should call oninit function', () => {
    const fixture = TestBed.createComponent(LoginShoreComponent);
    const component = fixture.debugElement.componentInstance;
    const spycanCreateData = jest.spyOn(component, 'ngOnInit');
    component.ngOnInit();
    tick(100);
    expect(component.createIdpInput()).toHaveBeenCalled();
  });

  it('should call createIdpInput function', () => {
    const fixture = TestBed.createComponent(LoginShoreComponent);
    const component = fixture.debugElement.componentInstance;
    const spycanCreateData = jest.spyOn(component, 'createIdpInput');
    component.createIdpInput();
    tick(100);
    expect(component.createIdpInput()).toHaveBeenCalled();
  });

  it('should call login function', () => {
    const fixture = TestBed.createComponent(LoginShoreComponent);
    const component = fixture.debugElement.componentInstance;
    const spycanCreateData = jest.spyOn(component, 'login');
    component.login('google');
    tick(100);
    expect(component.login('google')).toHaveBeenCalled();
  });
  
});
