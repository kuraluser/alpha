import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingSequenceChartComponent } from './loading-discharging-sequence-chart.component';

describe('LoadingDischargingSequenceChartComponent', () => {
  let component: LoadingDischargingSequenceChartComponent;
  let fixture: ComponentFixture<LoadingDischargingSequenceChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingSequenceChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingSequenceChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
