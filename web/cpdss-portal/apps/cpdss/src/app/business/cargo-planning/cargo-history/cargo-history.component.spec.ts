import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoHistoryComponent } from './cargo-history.component';

describe('CargoHistoryComponent', () => {
  let component: CargoHistoryComponent;
  let fixture: ComponentFixture<CargoHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
