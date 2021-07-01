import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoNominationComponent } from './cargo-nomination.component';

describe('CargoNominationComponent', () => {
  let component: CargoNominationComponent;
  let fixture: ComponentFixture<CargoNominationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoNominationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoNominationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
