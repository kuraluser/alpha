import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoPlanningComponent } from './cargo-planning.component';
import { RouterTestingModule } from '@angular/router/testing';

import { VesselInfoModule } from '../core/components/vessel-info/vessel-info.module';

describe('CargoPlanningComponent', () => {
  let component: CargoPlanningComponent;
  let fixture: ComponentFixture<CargoPlanningComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CargoPlanningComponent],
      imports: [
        RouterTestingModule,
        VesselInfoModule
      ],
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoPlanningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
