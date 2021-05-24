import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingPlanComponent } from './loading-plan.component';

describe('LoadingPlanComponent', () => {
  let component: LoadingPlanComponent;
  let fixture: ComponentFixture<LoadingPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
