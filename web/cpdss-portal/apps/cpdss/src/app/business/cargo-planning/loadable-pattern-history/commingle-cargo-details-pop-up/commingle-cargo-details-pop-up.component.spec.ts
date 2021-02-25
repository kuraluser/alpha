import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommingleCargoDetailsPopUpComponent } from './commingle-cargo-details-pop-up.component';

describe('CommingleCargoDetailsPopUpComponent', () => {
  let component: CommingleCargoDetailsPopUpComponent;
  let fixture: ComponentFixture<CommingleCargoDetailsPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommingleCargoDetailsPopUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommingleCargoDetailsPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
