import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructionCheckListComponent } from './instruction-check-list.component';

describe('InstructionCheckListComponent', () => {
  let component: InstructionCheckListComponent;
  let fixture: ComponentFixture<InstructionCheckListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstructionCheckListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructionCheckListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
