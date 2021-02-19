import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DraftConditionComponent } from './draft-condition.component';

describe('DraftConditionComponent', () => {
  let component: DraftConditionComponent;
  let fixture: ComponentFixture<DraftConditionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DraftConditionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DraftConditionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
