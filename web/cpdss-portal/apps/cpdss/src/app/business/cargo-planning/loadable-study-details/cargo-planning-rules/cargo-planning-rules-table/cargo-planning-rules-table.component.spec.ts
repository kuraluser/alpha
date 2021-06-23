import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoPlanningRulesTableComponent } from './cargo-planning-rules-table.component';

describe('CargoPlanningRulesTableComponent', () => {
  let component: CargoPlanningRulesTableComponent;
  let fixture: ComponentFixture<CargoPlanningRulesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoPlanningRulesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoPlanningRulesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
