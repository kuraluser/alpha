import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VesselInformationComponent } from './vessel-information.component';

describe('VesselInformationComponent', () => {
  let component: VesselInformationComponent;
  let fixture: ComponentFixture<VesselInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VesselInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VesselInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
