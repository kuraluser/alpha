import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRoleSelectionPopupComponent } from './user-role-selection-popup.component';

describe('UserRoleSelectionPopupComponent', () => {
  let component: UserRoleSelectionPopupComponent;
  let fixture: ComponentFixture<UserRoleSelectionPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserRoleSelectionPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserRoleSelectionPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
