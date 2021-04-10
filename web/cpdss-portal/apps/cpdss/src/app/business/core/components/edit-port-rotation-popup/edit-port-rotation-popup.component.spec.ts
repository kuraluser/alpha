import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EditPortRotationPopupComponent } from './edit-port-rotation-popup.component';

describe('EditPortRotationPopupComponent', () => {
  let component: EditPortRotationPopupComponent;
  let fixture: ComponentFixture<EditPortRotationPopupComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ EditPortRotationPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPortRotationPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
