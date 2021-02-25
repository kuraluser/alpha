import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { BunkeringLayoutComponent } from './bunkering-layout.component';

describe('BunkeringLayoutComponent', () => {
  let component: BunkeringLayoutComponent;
  let fixture: ComponentFixture<BunkeringLayoutComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BunkeringLayoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BunkeringLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
