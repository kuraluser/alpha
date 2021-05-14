import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FleetVesselCardComponent } from './fleet-vessel-card.component';

describe('FleetVesselCardComponent', () => {
  let component: FleetVesselCardComponent;
  let fixture: ComponentFixture<FleetVesselCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FleetVesselCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FleetVesselCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
