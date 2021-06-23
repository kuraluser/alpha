import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewDischargeStudyPopUpComponent } from './new-discharge-study-pop-up.component';

describe('NewDischargeStudyPopUpComponent', () => {
  let component: NewDischargeStudyPopUpComponent;
  let fixture: ComponentFixture<NewDischargeStudyPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewDischargeStudyPopUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewDischargeStudyPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
