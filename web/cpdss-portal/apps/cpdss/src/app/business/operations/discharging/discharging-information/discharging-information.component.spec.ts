import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargingInformationComponent } from './discharging-information.component';

describe('DischargingInformationComponent', () => {
  let component: DischargingInformationComponent;
  let fixture: ComponentFixture<DischargingInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargingInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargingInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
