import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortRotationPopupComponent } from './port-rotation-popup.component';

describe('PortRotationPopupComponent', () => {
  let component: PortRotationPopupComponent;
  let fixture: ComponentFixture<PortRotationPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PortRotationPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortRotationPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
