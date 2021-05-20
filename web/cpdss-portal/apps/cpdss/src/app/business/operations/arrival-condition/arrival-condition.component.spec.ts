import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArrivalConditionComponent } from './arrival-condition.component';

describe('ArrivalConditionComponent', () => {
  let component: ArrivalConditionComponent;
  let fixture: ComponentFixture<ArrivalConditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArrivalConditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArrivalConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
