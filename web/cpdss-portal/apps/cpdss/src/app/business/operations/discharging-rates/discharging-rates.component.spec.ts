import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargingRatesComponent } from './discharging-rates.component';

describe('DischargingRatesComponent', () => {
  let component: DischargingRatesComponent;
  let fixture: ComponentFixture<DischargingRatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargingRatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargingRatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
