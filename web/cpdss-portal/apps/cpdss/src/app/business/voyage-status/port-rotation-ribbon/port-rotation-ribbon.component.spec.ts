import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PortRotationRibbonComponent } from './port-rotation-ribbon.component';

describe('PortRotationRibbonComponent', () => {
  let component: PortRotationRibbonComponent;
  let fixture: ComponentFixture<PortRotationRibbonComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PortRotationRibbonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortRotationRibbonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
