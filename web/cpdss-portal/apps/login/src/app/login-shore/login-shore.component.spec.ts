import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginShoreComponent } from './login-shore.component';

describe('LoginShoreComponent', () => {
  let component: LoginShoreComponent;
  let fixture: ComponentFixture<LoginShoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginShoreComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginShoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
