import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingCargoMachineryComponent } from './loading-discharging-cargo-machinery.component';

describe('LoadingDischargingCargoMachineryComponent', () => {
  let component: LoadingDischargingCargoMachineryComponent;
  let fixture: ComponentFixture<LoadingDischargingCargoMachineryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingCargoMachineryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingCargoMachineryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
