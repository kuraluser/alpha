import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructionSidePanelComponent } from './instruction-side-panel.component';

describe('InstructionSidePanelComponent', () => {
  let component: InstructionSidePanelComponent;
  let fixture: ComponentFixture<InstructionSidePanelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstructionSidePanelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructionSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
