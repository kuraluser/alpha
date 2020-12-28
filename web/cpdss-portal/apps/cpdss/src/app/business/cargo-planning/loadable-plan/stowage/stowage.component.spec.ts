import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StowageComponent } from './stowage.component';

describe('StowageComponent', () => {
  let component: StowageComponent;
  let fixture: ComponentFixture<StowageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StowageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StowageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
