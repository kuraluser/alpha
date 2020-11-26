import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationAlertComponent } from './confirmation-alert.component';

describe('ConfirmationAlertComponent', () => {
  let component: ConfirmationAlertComponent;
  let fixture: ComponentFixture<ConfirmationAlertComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmationAlertComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmationAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
