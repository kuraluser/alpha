import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { DialogModule } from 'primeng/dialog';

import { ApiTemperatureHistoryPopupComponent } from './api-temperature-history-popup.component';

describe('ApiTemperatureHistoryPopupComponent', () => {
  let component: ApiTemperatureHistoryPopupComponent;
  let fixture: ComponentFixture<ApiTemperatureHistoryPopupComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ApiTemperatureHistoryPopupComponent],
      imports: [DialogModule]
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
