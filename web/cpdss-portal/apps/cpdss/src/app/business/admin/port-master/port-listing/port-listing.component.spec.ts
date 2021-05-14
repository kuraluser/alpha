import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortListingComponent } from './port-listing.component';

describe('PortListingComponent', () => {
  let component: PortListingComponent;
  let fixture: ComponentFixture<PortListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PortListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
