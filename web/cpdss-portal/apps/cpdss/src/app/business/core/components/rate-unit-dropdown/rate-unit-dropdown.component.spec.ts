import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateUnitDropdownComponent } from './rate-unit-dropdown.component';

describe('RateUnitDropdownComponent', () => {
  let component: RateUnitDropdownComponent;
  let fixture: ComponentFixture<RateUnitDropdownComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateUnitDropdownComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateUnitDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
