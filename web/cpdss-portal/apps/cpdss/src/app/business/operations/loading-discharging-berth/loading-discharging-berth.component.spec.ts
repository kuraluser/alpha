import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingBerthComponent } from './loading-discharging-berth.component';

describe('LoadingDischargingBerthComponent', () => {
  let component: LoadingDischargingBerthComponent;
  let fixture: ComponentFixture<LoadingDischargingBerthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingBerthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingBerthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
