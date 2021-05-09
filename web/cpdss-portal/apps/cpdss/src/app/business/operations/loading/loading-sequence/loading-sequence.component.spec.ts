import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingSequenceComponent } from './loading-sequence.component';

describe('LoadingSequenceComponent', () => {
  let component: LoadingSequenceComponent;
  let fixture: ComponentFixture<LoadingSequenceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingSequenceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingSequenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
