import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargeStudyDetailsComponent } from './discharge-study-details.component';

describe('DischargeStudyDetailsComponent', () => {
  let component: DischargeStudyDetailsComponent;
  let fixture: ComponentFixture<DischargeStudyDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargeStudyDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargeStudyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
