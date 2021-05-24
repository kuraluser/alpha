import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadingInstructionComponent } from './loading-instruction.component';

describe('LoadingInstructionComponent', () => {
  let component: LoadingInstructionComponent;
  let fixture: ComponentFixture<LoadingInstructionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadingInstructionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadingInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
