import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBerthComponent } from './add-berth.component';

describe('AddBerthComponent', () => {
  let component: AddBerthComponent;
  let fixture: ComponentFixture<AddBerthComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBerthComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBerthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
