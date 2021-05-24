import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FleetVesselNotificationsComponent } from './fleet-vessel-notifications.component';

describe('FleetVesselNotificationsComponent', () => {
  let component: FleetVesselNotificationsComponent;
  let fixture: ComponentFixture<FleetVesselNotificationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FleetVesselNotificationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FleetVesselNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
