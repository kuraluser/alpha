import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartureConditionComponent } from './departure-condition.component';

describe('DepartureConditionComponent', () => {
  let component: DepartureConditionComponent;
  let fixture: ComponentFixture<DepartureConditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartureConditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartureConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
