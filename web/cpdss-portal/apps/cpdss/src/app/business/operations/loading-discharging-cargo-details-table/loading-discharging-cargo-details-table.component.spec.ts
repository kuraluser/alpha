import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingCargoDetailsTableComponent } from './loading-discharging-cargo-details-table.component';

describe('LoadingDischargingCargoDetailsTableComponent', () => {
  let component: LoadingDischargingCargoDetailsTableComponent;
  let fixture: ComponentFixture<LoadingDischargingCargoDetailsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingCargoDetailsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingCargoDetailsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
