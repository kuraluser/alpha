import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveStowagePopupComponent } from './save-stowage-popup.component';

describe('SaveStowagePopupComponent', () => {
  let component: SaveStowagePopupComponent;
  let fixture: ComponentFixture<SaveStowagePopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveStowagePopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveStowagePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
