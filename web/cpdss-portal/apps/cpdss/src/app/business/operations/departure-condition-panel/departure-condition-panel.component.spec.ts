import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartureConditionPanelComponent } from './departure-condition-panel.component';

describe('DepartureConditionPanelComponent', () => {
  let component: DepartureConditionPanelComponent;
  let fixture: ComponentFixture<DepartureConditionPanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartureConditionPanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartureConditionPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
