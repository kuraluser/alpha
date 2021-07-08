import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargeStudyComponent } from './discharge-study.component';

describe('DischargeStudyComponent', () => {
  let component: DischargeStudyComponent;
  let fixture: ComponentFixture<DischargeStudyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargeStudyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargeStudyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
