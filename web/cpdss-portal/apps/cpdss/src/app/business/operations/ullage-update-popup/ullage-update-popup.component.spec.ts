import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UllageUpdatePopupComponent } from './ullage-update-popup.component';

describe('UllageUpdatePopupComponent', () => {
  let component: UllageUpdatePopupComponent;
  let fixture: ComponentFixture<UllageUpdatePopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UllageUpdatePopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UllageUpdatePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
