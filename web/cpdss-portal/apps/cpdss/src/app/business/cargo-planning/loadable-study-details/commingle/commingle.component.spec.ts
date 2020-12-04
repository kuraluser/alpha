import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CommingleComponent } from './commingle.component';

describe('CommingleComponent', () => {
  let component: CommingleComponent;
  let fixture: ComponentFixture<CommingleComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CommingleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommingleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
