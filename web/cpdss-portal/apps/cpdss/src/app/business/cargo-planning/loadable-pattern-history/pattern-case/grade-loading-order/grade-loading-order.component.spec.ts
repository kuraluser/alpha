import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeLoadingOrderComponent } from './grade-loading-order.component';

describe('GradeLoadingOrderComponent', () => {
  let component: GradeLoadingOrderComponent;
  let fixture: ComponentFixture<GradeLoadingOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GradeLoadingOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GradeLoadingOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
