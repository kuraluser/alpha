import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OnHandQuantityComponent } from './on-hand-quantity.component';

describe('OnHandQuantityComponent', () => {
  let component: OnHandQuantityComponent;
  let fixture: ComponentFixture<OnHandQuantityComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OnHandQuantityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnHandQuantityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
