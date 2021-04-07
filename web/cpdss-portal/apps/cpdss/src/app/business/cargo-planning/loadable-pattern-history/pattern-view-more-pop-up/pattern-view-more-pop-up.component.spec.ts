import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatternViewMorePopUpComponent } from './pattern-view-more-pop-up.component';

describe('PatternViewMorePopUpComponent', () => {
  let component: PatternViewMorePopUpComponent;
  let fixture: ComponentFixture<PatternViewMorePopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatternViewMorePopUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatternViewMorePopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
