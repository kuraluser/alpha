import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoMasterComponent } from './cargo-master.component';

describe('CargoMasterComponent', () => {
  let component: CargoMasterComponent;
  let fixture: ComponentFixture<CargoMasterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoMasterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
