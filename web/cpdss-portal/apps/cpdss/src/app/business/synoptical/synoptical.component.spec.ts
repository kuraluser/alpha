import { async, ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SynopticalComponent } from './synoptical.component';

describe('SynopticalComponent', () => {
  let component: SynopticalComponent;
  let fixture: ComponentFixture<SynopticalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SynopticalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SynopticalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
