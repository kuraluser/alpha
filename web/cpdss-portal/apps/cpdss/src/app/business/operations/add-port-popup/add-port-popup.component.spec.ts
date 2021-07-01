import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPortPopupComponent } from './add-port-popup.component';

describe('AddPortPopupComponent', () => {
  let component: AddPortPopupComponent;
  let fixture: ComponentFixture<AddPortPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPortPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPortPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
