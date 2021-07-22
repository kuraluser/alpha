import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiTemperatureHistoryPopupComponent } from './api-temperature-history-popup.component';

describe('ApiTemperatureHistoryPopupComponent', () => {
  let component: ApiTemperatureHistoryPopupComponent;
  let fixture: ComponentFixture<ApiTemperatureHistoryPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApiTemperatureHistoryPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApiTemperatureHistoryPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
