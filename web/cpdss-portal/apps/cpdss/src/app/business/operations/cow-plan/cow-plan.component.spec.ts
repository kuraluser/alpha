import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CowPlanComponent } from './cow-plan.component';

describe('CowPlanComponent', () => {
  let component: CowPlanComponent;
  let fixture: ComponentFixture<CowPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CowPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CowPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
