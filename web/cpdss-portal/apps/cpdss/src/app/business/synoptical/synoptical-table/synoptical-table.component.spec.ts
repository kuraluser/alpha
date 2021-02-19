import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SynopticalTableComponent } from './synoptical-table.component';

describe('SynopticalTableComponent', () => {
  let component: SynopticalTableComponent;
  let fixture: ComponentFixture<SynopticalTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SynopticalTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SynopticalTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
