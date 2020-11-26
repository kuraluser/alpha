import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SidePanelLoadableStudyListComponent } from './side-panel-loadable-study-list.component';

describe('SidePanelLoadableStudyListComponent', () => {
  let component: SidePanelLoadableStudyListComponent;
  let fixture: ComponentFixture<SidePanelLoadableStudyListComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SidePanelLoadableStudyListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SidePanelLoadableStudyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
