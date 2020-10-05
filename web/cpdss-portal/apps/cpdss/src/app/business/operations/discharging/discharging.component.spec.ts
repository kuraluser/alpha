import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargingComponent } from './discharging.component';

describe('DischargingComponent', () => {
  let component: DischargingComponent;
  let fixture: ComponentFixture<DischargingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DischargingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
