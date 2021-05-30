import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ToppingOffTankTableComponent } from './topping-off-tank-table.component';

describe('ToppingOffTankTableComponent', () => {
  let component: ToppingOffTankTableComponent;
  let fixture: ComponentFixture<ToppingOffTankTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ToppingOffTankTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ToppingOffTankTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
