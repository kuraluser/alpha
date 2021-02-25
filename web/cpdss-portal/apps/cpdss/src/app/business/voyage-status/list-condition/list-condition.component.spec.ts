import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListConditionComponent } from './list-condition.component';

describe('ListConditionComponent', () => {
  let component: ListConditionComponent;
  let fixture: ComponentFixture<ListConditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListConditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
