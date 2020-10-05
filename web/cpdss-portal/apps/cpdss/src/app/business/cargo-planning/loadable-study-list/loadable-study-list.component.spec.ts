import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadableStudyListComponent } from './loadable-study-list.component';

describe('LoadableStudyListComponent', () => {
  let component: LoadableStudyListComponent;
  let fixture: ComponentFixture<LoadableStudyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoadableStudyListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadableStudyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
