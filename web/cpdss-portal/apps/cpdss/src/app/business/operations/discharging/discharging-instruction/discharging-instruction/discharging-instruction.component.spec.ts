import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargingInstructionComponent } from './discharging-instruction.component';

describe('DischargingInstructionComponent', () => {
  let component: DischargingInstructionComponent;
  let fixture: ComponentFixture<DischargingInstructionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargingInstructionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargingInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
