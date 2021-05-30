import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArrivalConditionPanelComponent } from './arrival-condition-panel.component';

describe('ArrivalConditionPanelComponent', () => {
  let component: ArrivalConditionPanelComponent;
  let fixture: ComponentFixture<ArrivalConditionPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArrivalConditionPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArrivalConditionPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
