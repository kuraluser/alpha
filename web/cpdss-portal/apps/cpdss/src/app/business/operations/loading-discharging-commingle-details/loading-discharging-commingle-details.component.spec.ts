import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingCommingleDetailsComponent } from './loading-discharging-commingle-details.component';

describe('LoadingDischargingCommingleDetailsComponent', () => {
  let component: LoadingDischargingCommingleDetailsComponent;
  let fixture: ComponentFixture<LoadingDischargingCommingleDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingCommingleDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingCommingleDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
