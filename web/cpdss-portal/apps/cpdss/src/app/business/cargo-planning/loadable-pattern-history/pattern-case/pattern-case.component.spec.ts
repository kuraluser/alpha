import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatternCaseComponent } from './pattern-case.component';

describe('PatternCaseComponent', () => {
  let component: PatternCaseComponent;
  let fixture: ComponentFixture<PatternCaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatternCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatternCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
