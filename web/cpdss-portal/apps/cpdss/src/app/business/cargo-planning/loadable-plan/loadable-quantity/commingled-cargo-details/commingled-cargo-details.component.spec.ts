import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommingledCargoDetailsComponent } from './commingled-cargo-details.component';

describe('CommingledCargoDetailsComponent', () => {
  let component: CommingledCargoDetailsComponent;
  let fixture: ComponentFixture<CommingledCargoDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommingledCargoDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommingledCargoDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
