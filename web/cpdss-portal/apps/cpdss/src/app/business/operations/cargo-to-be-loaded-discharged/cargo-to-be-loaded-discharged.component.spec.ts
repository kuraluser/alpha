import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoToBeLoadedDischargedComponent } from './cargo-to-be-loaded-discharged.component';

describe('CargoToBeLoadedDischargedComponent', () => {
  let component: CargoToBeLoadedDischargedComponent;
  let fixture: ComponentFixture<CargoToBeLoadedDischargedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoToBeLoadedDischargedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoToBeLoadedDischargedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
