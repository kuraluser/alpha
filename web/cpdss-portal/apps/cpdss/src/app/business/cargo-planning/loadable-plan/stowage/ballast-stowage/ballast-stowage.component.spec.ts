import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BallastStowageComponent } from './ballast-stowage.component';

describe('BallastStowageComponent', () => {
  let component: BallastStowageComponent;
  let fixture: ComponentFixture<BallastStowageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BallastStowageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BallastStowageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
