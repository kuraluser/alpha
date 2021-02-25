import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortEtaEtdConditionComponent } from './port-eta-etd-condition.component';

describe('PortEtaEtdConditionComponent', () => {
  let component: PortEtaEtdConditionComponent;
  let fixture: ComponentFixture<PortEtaEtdConditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PortEtaEtdConditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortEtaEtdConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
