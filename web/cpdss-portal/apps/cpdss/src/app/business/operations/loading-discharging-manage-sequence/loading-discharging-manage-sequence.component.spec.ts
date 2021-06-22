import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingDischargingManageSequenceComponent } from './loading-discharging-manage-sequence.component';

describe('LoadingDischargingManageSequenceComponent', () => {
  let component: LoadingDischargingManageSequenceComponent;
  let fixture: ComponentFixture<LoadingDischargingManageSequenceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingDischargingManageSequenceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingDischargingManageSequenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
