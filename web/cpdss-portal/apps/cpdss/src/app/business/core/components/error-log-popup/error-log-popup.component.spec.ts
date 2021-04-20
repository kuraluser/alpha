import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorLogPopupComponent } from './error-log-popup.component';

describe('ErrorLogPopupComponent', () => {
  let component: ErrorLogPopupComponent;
  let fixture: ComponentFixture<ErrorLogPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ErrorLogPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorLogPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
