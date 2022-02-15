import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CrewListingComponent } from './crew-listing.component';

describe('CrewListingComponent', () => {
  let component: CrewListingComponent;
  let fixture: ComponentFixture<CrewListingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CrewListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CrewListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
