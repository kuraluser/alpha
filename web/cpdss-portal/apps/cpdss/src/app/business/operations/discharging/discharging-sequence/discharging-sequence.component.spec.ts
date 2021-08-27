import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargingSequenceComponent } from './discharging-sequence.component';

describe('DischargingSequenceComponent', () => {
  let component: DischargingSequenceComponent;
  let fixture: ComponentFixture<DischargingSequenceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargingSequenceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargingSequenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
