import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingRateComponent } from './loading-rate.component';

describe('LoadingRateComponent', () => {
  let component: LoadingRateComponent;
  let fixture: ComponentFixture<LoadingRateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingRateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingRateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
