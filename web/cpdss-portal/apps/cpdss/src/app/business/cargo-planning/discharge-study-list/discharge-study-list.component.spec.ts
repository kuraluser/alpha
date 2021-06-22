import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DischargeStudyListComponent } from './discharge-study-list.component';

describe('DischargeStudyListComponent', () => {
  let component: DischargeStudyListComponent;
  let fixture: ComponentFixture<DischargeStudyListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DischargeStudyListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DischargeStudyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
