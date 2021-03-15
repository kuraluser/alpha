import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StabilityPopUpComponent } from './stability-pop-up.component';

describe('StabilityPopUpComponent', () => {
  let component: StabilityPopUpComponent;
  let fixture: ComponentFixture<StabilityPopUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StabilityPopUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StabilityPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
