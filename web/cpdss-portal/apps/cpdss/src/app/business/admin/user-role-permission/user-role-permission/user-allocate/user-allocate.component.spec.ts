import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAllocateComponent } from './user-allocate.component';

describe('UserAllocateComponent', () => {
  let component: UserAllocateComponent;
  let fixture: ComponentFixture<UserAllocateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserAllocateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAllocateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
