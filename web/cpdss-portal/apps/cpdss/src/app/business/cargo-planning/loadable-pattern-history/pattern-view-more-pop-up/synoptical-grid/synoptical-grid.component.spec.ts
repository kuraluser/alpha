import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SynopticalGridComponent } from './synoptical-grid.component';

describe('SynopticalGridComponent', () => {
  let component: SynopticalGridComponent;
  let fixture: ComponentFixture<SynopticalGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SynopticalGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SynopticalGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
