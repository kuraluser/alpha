import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OnBoardQuantityComponent } from './on-board-quantity.component';

describe('OnBoardQuantityComponent', () => {
  let component: OnBoardQuantityComponent;
  let fixture: ComponentFixture<OnBoardQuantityComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OnBoardQuantityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OnBoardQuantityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
