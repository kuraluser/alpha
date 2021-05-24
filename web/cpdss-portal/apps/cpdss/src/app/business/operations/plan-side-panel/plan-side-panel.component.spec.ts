import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanSidePanelComponent } from './plan-side-panel.component';

describe('PlanSidePanelComponent', () => {
  let component: PlanSidePanelComponent;
  let fixture: ComponentFixture<PlanSidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanSidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
