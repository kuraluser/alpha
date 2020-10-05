import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadablePlanComponent } from './loadable-plan.component';

describe('LoadablePlanComponent', () => {
  let component: LoadablePlanComponent;
  let fixture: ComponentFixture<LoadablePlanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadablePlanComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadablePlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
