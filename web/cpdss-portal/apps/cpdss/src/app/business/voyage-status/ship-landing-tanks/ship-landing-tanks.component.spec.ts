import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShipLandingTanksComponent } from './ship-landing-tanks.component';

describe('ShipLandingTanksComponent', () => {
  let component: ShipLandingTanksComponent;
  let fixture: ComponentFixture<ShipLandingTanksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShipLandingTanksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShipLandingTanksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
