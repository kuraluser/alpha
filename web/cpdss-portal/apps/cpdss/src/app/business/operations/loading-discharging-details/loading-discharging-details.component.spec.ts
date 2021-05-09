import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingDetailsComponent } from './loading-discharging-details.component';

describe('LoadingDischargingDetailsComponent', () => {
  let component: LoadingDischargingDetailsComponent;
  let fixture: ComponentFixture<LoadingDischargingDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
