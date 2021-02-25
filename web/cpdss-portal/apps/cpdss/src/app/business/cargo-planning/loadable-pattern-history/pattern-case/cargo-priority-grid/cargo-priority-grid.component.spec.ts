import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoPriorityGridComponent } from './cargo-priority-grid.component';

describe('CargoPriorityGridComponent', () => {
  let component: CargoPriorityGridComponent;
  let fixture: ComponentFixture<CargoPriorityGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoPriorityGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoPriorityGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
