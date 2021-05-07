import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingCargoDetailsComponent } from './loading-discharging-cargo-details.component';

describe('LoadingDischargingCargoDetailsComponent', () => {
  let component: LoadingDischargingCargoDetailsComponent;
  let fixture: ComponentFixture<LoadingDischargingCargoDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingCargoDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingCargoDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
