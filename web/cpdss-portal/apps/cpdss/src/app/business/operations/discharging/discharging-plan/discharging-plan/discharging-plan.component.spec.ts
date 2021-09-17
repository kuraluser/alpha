import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargingPlanComponent } from './discharging-plan.component';

describe('DischargingPlanComponent', () => {
  let component: DischargingPlanComponent;
  let fixture: ComponentFixture<DischargingPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargingPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargingPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
