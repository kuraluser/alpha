import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { VesselInfoComponent } from './vessel-info.component';

describe('VesselInfoComponent', () => {
  let component: VesselInfoComponent;
  let fixture: ComponentFixture<VesselInfoComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ VesselInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VesselInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
