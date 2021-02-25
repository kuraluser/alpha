import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BallastLayoutComponent } from './ballast-layout.component';

describe('BallastLayoutComponent', () => {
  let component: BallastLayoutComponent;
  let fixture: ComponentFixture<BallastLayoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BallastLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BallastLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
