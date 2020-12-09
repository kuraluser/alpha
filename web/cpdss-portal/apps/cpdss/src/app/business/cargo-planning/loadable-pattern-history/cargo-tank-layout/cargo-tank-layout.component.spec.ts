import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoTankLayoutComponent } from './cargo-tank-layout.component';

describe('CargoTankLayoutComponent', () => {
  let component: CargoTankLayoutComponent;
  let fixture: ComponentFixture<CargoTankLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoTankLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoTankLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
