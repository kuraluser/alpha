import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatePopUpComponent } from './date-pop-up.component';

describe('DatePopUpComponent', () => {
  let component: DatePopUpComponent;
  let fixture: ComponentFixture<DatePopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatePopUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatePopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
