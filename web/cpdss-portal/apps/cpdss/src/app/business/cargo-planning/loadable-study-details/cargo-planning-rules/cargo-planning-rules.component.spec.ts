import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoPlanningRulesComponent } from './cargo-planning-rules.component';

describe('CargoPlanningRulesComponent', () => {
  let component: CargoPlanningRulesComponent;
  let fixture: ComponentFixture<CargoPlanningRulesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CargoPlanningRulesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoPlanningRulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
