import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoyageStatusComponent } from './voyage-status.component';

describe('VoyageStatusComponent', () => {
  let component: VoyageStatusComponent;
  let fixture: ComponentFixture<VoyageStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoyageStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoyageStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
