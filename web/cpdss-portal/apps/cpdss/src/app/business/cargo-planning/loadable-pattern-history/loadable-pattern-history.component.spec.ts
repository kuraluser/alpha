import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadablePatternHistoryComponent } from './loadable-pattern-history.component';

describe('LoadablePatternHistoryComponent', () => {
  let component: LoadablePatternHistoryComponent;
  let fixture: ComponentFixture<LoadablePatternHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadablePatternHistoryComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadablePatternHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
