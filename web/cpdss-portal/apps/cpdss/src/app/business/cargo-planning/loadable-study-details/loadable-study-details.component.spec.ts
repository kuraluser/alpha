import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadableStudyDetailsComponent } from './loadable-study-details.component';

describe('LoadableStudyDetailsComponent', () => {
  let component: LoadableStudyDetailsComponent;
  let fixture: ComponentFixture<LoadableStudyDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoadableStudyDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadableStudyDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
