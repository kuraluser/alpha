import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BunkeringComponent } from './bunkering.component';

describe('BunkeringComponent', () => {
  let component: BunkeringComponent;
  let fixture: ComponentFixture<BunkeringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BunkeringComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BunkeringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
